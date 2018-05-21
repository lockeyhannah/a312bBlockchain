package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.data_points.CoinTransaction;
import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.FileOverview;
import blockchain.block.data_points.StorageContract;
import blockchain.block.mining.BlockMiner;
import blockchain.block.mining.Hasher;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ChainAnalyzer {

    public static double getUserBalance(LedgerReader reader, String userID) {
        double userBalance = 0;
        long totalBlocks = reader.getBlockCount();

        // Check all blocks starting from block 0
        for (int i = 0; i < totalBlocks; i++) {
            Data data = reader.readBlock(i).getData();
            userBalance += getBalanceChange(data, userID);
        }

        return userBalance;
    }

    // Returns the balance change for a user within the given data
    public static double getBalanceChange(Data data, String userID) {
        double balanceChange = 0;
        ArrayList<DataPoint> dataPoints = data.getDataPoints();

        for (DataPoint dp : dataPoints) {
            balanceChange += getBalanceChange(dp, userID);
        }

        return balanceChange;
    }

    // Returns the balance change for a user in the given data point
    public static double getBalanceChange(DataPoint dataPoint, String userID){
        double balanceChange = 0;

        if (dataPoint instanceof CoinTransaction)
            balanceChange += getBalanceChange((CoinTransaction) dataPoint, userID);

        else if (dataPoint instanceof FileOverview)
            balanceChange += getBalanceChange((FileOverview) dataPoint, userID);

        return balanceChange;
    }

    private static double getBalanceChange(CoinTransaction coinTransaction, String userID) {
        // Return positive transfer amount if the user is the recipient of coins
        if (coinTransaction.getRecipientID().equals(userID))
            return coinTransaction.getCoins();

        // Subtract transfer amount if the user is the giver
        if (coinTransaction.getGiverID().equals(userID))
            return -(coinTransaction.getCoins());

        // Return 0 if the user was not part of this transaction
        return 0;
    }

    private static double getBalanceChange(FileOverview fileOverview, String userID) {
        double balanceChange = 0;

        // Subtract all payments from balance if the user has payed for storage
        if(fileOverview.getOwnerID().equals(userID)){
            for(StorageContract contract : fileOverview.getStorageContracts())
                balanceChange -= contract.getReward();

        }else{ // Otherwise check if the user is storing/has stored a file for somebody
            for(StorageContract contract : fileOverview.getStorageContracts()){
                long currentTime = System.currentTimeMillis();

                if(contract.getStorageID().equals(userID)){
                    // If the contract is terminated the payment is granted
                    if(contract.getContractTerminationTime() < currentTime)
                        balanceChange = contract.getReward();

                    // Break the loop as a unit is only be able store one of the chunks of a file
                    break;
                }
            }
        }

        return balanceChange;
    }


    // Checks if all blocks in the chain are valid
    public static boolean isChainValid(LedgerReader reader) {
        long totalBlocks = reader.getBlockCount();

        // Check all blocks starting from block 0
        for (int i = 0; i < totalBlocks; i++) {
            // Returns false if an invalid block is encountered
            if (!(isBlockValid(reader.readBlock(i), reader)))
                return false;
        }
        return true;
    }

    // Checks if a given block is valid in the chain
    public static boolean isBlockValid(Block block, LedgerReader reader) {
        BigInteger target = new BigInteger(block.getHeader().getDifficultyTarget());
        BigInteger hash = new BigInteger(Hasher.applySHA(block.getHeader().getBytes()));

        // Check that hash is below target value
        if (hash.compareTo(target) > 0)
            return false;

        // Check that hash of data matches the dataHash in the header
        byte[] dataHash = Hasher.applySHA(block.getData().getDataBytes());
        if (Arrays.compare(dataHash, block.getHeader().getDataHash()) != 0)
            return false;

        // If this is the first block don't check validity relative to previous block
        if (block.getHeader().getBlockId() == 0)
            return true;

        // Get the previous block
        Block previousBlock = reader.readBlock(block.getHeader().getBlockId() - 1);

        // Check that the hash of previous header matches the prevHeaderHash in the current block's header
        byte[] previousHeaderHash = (Hasher.applySHA(previousBlock.getHeader().getBytes()));
        if (!(Arrays.compare(previousHeaderHash, block.getHeader().getPrevHash()) == 0)) {
            return false;
        }

        return true;
    }
}
