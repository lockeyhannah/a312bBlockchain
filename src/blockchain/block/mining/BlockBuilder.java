package blockchain.block.mining;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.block.data_points.*;
import blockchain.ledger_file.ChainAnalyzer;
import blockchain.ledger_file.LedgerReader;

import java.math.BigInteger;
import java.util.Date;
/*
 * Builder for the block class
 * When built a header is generated and the block is mined according to its difficulty
 */

public class BlockBuilder {

    // Reward given to the miner of the block
    public static final double miningReward = 5.0;

    private Data data;
    private Header header;
    private Header previousHeader;
    private long blockNumber;
    private LedgerReader ledgerReader;

    // Initiates the builder, potentially based on a previous header
    public BlockBuilder(LedgerReader ledgerReader) {
        this.ledgerReader = ledgerReader;
        this.previousHeader = findPreviousHeader();

        if (previousHeader != null)
            blockNumber = previousHeader.getBlockId() + 1;
        else
            blockNumber = 0;

        data = new Data();
    }

    private Header findPreviousHeader() {
        if (ledgerReader.getBlockCount() == 0) return null;
        else return ledgerReader.getNewestBlock().getHeader();
    }


    /* Generates a new header with values based on the previous header
     * Generates standard values if the previous header is null */
    private void generateHeader() {
        Long timeStamp = System.currentTimeMillis();

        byte[] dataHash = Hasher.applySHA(data.getDataBytes()); // Hash data
        byte[] nonce = new byte[]{0}; // Starting value for the nonce

        byte[] difficulty;
        byte[] prevHeaderHash;

        if (previousHeader != null) {
            // Otherwise set values based on previous block
            prevHeaderHash = Hasher.applySHA(previousHeader.getBytes());
            difficulty = previousHeader.getDifficultyTarget(); // todo Add difficulty calculation
        } else {
            // Otherwise set standard values
            difficulty = BigInteger.TWO.pow(238).toByteArray();
            prevHeaderHash = new byte[32];
        }

        header = new Header(blockNumber, prevHeaderHash, dataHash, nonce, difficulty, timeStamp);
    }

    // Adds a data point to the block and returns a unique identifier for this data point
    public DataPointUID addData(DataPoint dp) throws InsufficientFundsException {
        double requiredFunds = 0;
        double availableFunds = 0;

        // Check that the user doesn't spend more coins than he has
        if (dp instanceof CoinTransaction) {
            // If the DataPoint is a transaction then check that the giver has enough tokens to pay
            CoinTransaction transaction = (CoinTransaction) dp;
            requiredFunds = ChainAnalyzer.getBalanceChange(transaction, transaction.getGiverID());

            // Find the users total funds
            availableFunds = ChainAnalyzer.getUserBalance(ledgerReader, transaction.getGiverID());
            // Add the balance changes from the data in the current block
            availableFunds += ChainAnalyzer.getBalanceChange(data, transaction.getGiverID());
        } else if (dp instanceof StorageContract) {
            // If the DataPoint is a StorageContract then check that the file owner has enough tokens to pay
            StorageContract storageContract = (StorageContract) dp;
            requiredFunds = ChainAnalyzer.getBalanceChange(storageContract, storageContract.getFileOwnerID());

            // Find the users total funds
            availableFunds = ChainAnalyzer.getUserBalance(ledgerReader, storageContract.getFileOwnerID());
            // Add the balance changes from the data in the current block
            availableFunds += ChainAnalyzer.getBalanceChange(data, storageContract.getFileOwnerID());
        }

        // Throw exception if the user does not have the required funds
        if (requiredFunds > availableFunds)
            throw new InsufficientFundsException("User does not have sufficient funds", dp);

        // Add data point to the block and return a unique identifier for this data point
        int dataPointNumber = data.addData(dp);
        return new DataPointUID(blockNumber, dataPointNumber);
    }


    // Builds the block, generating the header and mining the block
    public Block build(String ownerID) {
        // Add reward for mining the block
        data.addData(new CoinTransaction("0", ownerID, miningReward));

        generateHeader();
        return BlockMiner.mineBlock(header, data);
    }
}
