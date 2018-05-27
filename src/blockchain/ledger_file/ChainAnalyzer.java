package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.data_points.DataPoint;
import blockchain.block.mining.Hasher;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class ChainAnalyzer {

    private final LedgerReader reader;

    public ChainAnalyzer(LedgerReader reader) {
        this.reader = reader;
    }

    /* Looks through the entire chain and sums up all
       transactions made to and from the given user */
    public double getUserBalance(String userID) {
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
            balanceChange += dp.getBalanceChange(userID);
        }

        return balanceChange;
    }

    // Returns a list of all data points containing the given id
    public ArrayList<DataPoint> getDataPoints(String id) {
        ArrayList<DataPoint> matchingDataPoints = new ArrayList<>();

        long totalBlocks = reader.getBlockCount();

        // Check all blocks starting from block 0
        for (int i = 0; i < totalBlocks; i++) {
            ArrayList<DataPoint> dataPoints = reader.readBlock(i).getData().getDataPoints();

            // Check each DataPoint in the block and add it if it contain the id
            for (DataPoint dp : dataPoints) {
                if (dp.containsIdentifier(id))
                    matchingDataPoints.add(dp);
            }

        }
        return matchingDataPoints;
    }


    // Checks if all blocks in the chain are valid
    public boolean isChainValid() {
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
