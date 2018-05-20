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

    // Generates a new header with values based on the previous header
    // Generates standard values if the previous header is null
    private void generateHeader() {
        Long timeStamp = System.currentTimeMillis();

        byte[] dataHash = Hasher.applySHA(data.getDataBytes()); // Hash data
        byte[] nonce = new byte[]{0};
        byte[] difficulty;
        byte[] prevHeaderHash;

        if (previousHeader == null) { // Set standard values if no previous header is given
            difficulty = BigInteger.TWO.pow(238).toByteArray();
            prevHeaderHash = new byte[32];
        } else { // Otherwise set values based on previous block
            prevHeaderHash = Hasher.applySHA(previousHeader.getBytes());
            difficulty = previousHeader.getDifficultyTarget(); // todo Add difficulty calculation
        }

        header = new Header(blockNumber, prevHeaderHash, dataHash, nonce, difficulty, timeStamp);
    }

    // Adds a data point to the block and return a unique identifier for this data point
    public DataPointUID addData(DataPoint dp) throws InsufficientFundsException {
        double requiredFunds = 0;
        double availableFunds = 0;

        // Check that the user doesn't spend more coins than he has
        if (dp instanceof CoinTransaction) {
            CoinTransaction transaction = (CoinTransaction) dp;
            requiredFunds = ChainAnalyzer.getBalanceChange(transaction, transaction.getGiverID());

            // Find the users total funds
            availableFunds = ChainAnalyzer.getUserBalance(ledgerReader, transaction.getGiverID());
            // Add the funds that are in the current block and therefore not added to the ledger file yet
            availableFunds += ChainAnalyzer.getBalanceChange(data, transaction.getGiverID());

        } else if (dp instanceof FileOverview) {
            FileOverview fileOverview = (FileOverview) dp;
            requiredFunds = ChainAnalyzer.getBalanceChange(fileOverview, fileOverview.getOwnerID());

            // Find the users total funds
            availableFunds = ChainAnalyzer.getUserBalance(ledgerReader, fileOverview.getOwnerID());
            // Add the funds that are in the current block and therefore not added to the ledger file yet
            availableFunds += ChainAnalyzer.getBalanceChange(data, fileOverview.getOwnerID());
        }

        // Throw exception if the user does not have the required funds
        if (requiredFunds > availableFunds)
            throw new InsufficientFundsException("User does not have sufficient funds", dp);

        // Add data point to the block and return a unique identifier for this data point
        int dataPointNumber = data.addData(dp);
        return new DataPointUID(blockNumber, dataPointNumber);
    }


    // Builds the block, generating the header and mining the block
    public Block build() {
        generateHeader();
        return BlockMiner.mineBlock(header, data);
    }
}
