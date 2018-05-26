package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.data_points.CoinTransaction;
import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.StorageContract;
import blockchain.block.mining.Hasher;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class ChainAnalyzer {

    /* Looks through the entire chain and sums up all
       transactions made to and from the given user */
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

        else if (dataPoint instanceof StorageContract)
            balanceChange += getBalanceChange((StorageContract) dataPoint, userID);

        return balanceChange;
    }

    // Returns the balance change for a user if he is involved in the given transaction
    private static double getBalanceChange(CoinTransaction coinTransaction, String userID) {
        // Return positive transfer amount if the user is the recipient of coins
        if (coinTransaction.getRecipientID().equals(userID))
            return coinTransaction.getTokens();

        // Subtract transfer amount if the user is the giver
        if (coinTransaction.getGiverID().equals(userID))
            return -(coinTransaction.getTokens());

        // Return 0 if the user was not part of this transaction
        return 0;
    }

    private static double getBalanceChange(StorageContract storageContract, String userID) {
        double balanceChange = 0;

        // Subtract payments if the user paid for storage
        if(storageContract.getFileOwnerID().equals(userID))
            balanceChange = -storageContract.getReward();

        // Add payments if the user is the storage unit and the contract has been fulfilled
        // In this implementation the contract fulfillment means that the storage period has expired
        else if(storageContract.getStorageUnitID().equals(userID)){
            if(storageContract.getContractTerminationTime() < System.currentTimeMillis())
                balanceChange = storageContract.getReward();
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

    // Checks if a given block is valid
    public static boolean isBlockValid(Block block, LedgerReader reader) {
        BigInteger target = new BigInteger(block.getHeader().getDifficultyTarget());
        BigInteger hash = new BigInteger(Hasher.applySHA(block.getHeader().getBytes()));

        // Check that hash is below target value
        if (hash.compareTo(target) > 0)
            return false;

        // Check that hash of data matches the dataHash value in the header
        byte[] dataHash = Hasher.applySHA(block.getData().getDataBytes());
        if (Arrays.compare(dataHash, block.getHeader().getDataHash()) != 0)
            return false;

        // If this is the first block don't check validity relative to previous block
        if (block.getHeader().getBlockId() == 0)
            return true;

        // Get the previous block
        Block previousBlock = reader.readBlock(block.getHeader().getBlockId() - 1);

        // Check that the hash of previous header matches the prevHeaderHash value in the current block's header
        byte[] previousHeaderHash = (Hasher.applySHA(previousBlock.getHeader().getBytes()));
        return Arrays.compare(previousHeaderHash, block.getHeader().getPrevHash()) == 0;
    }
}
