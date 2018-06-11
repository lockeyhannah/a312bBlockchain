package blockchain;

import blockchain.block.Block;
import blockchain.block.data_points.*;
import blockchain.block.mining.BlockBuilder;
import blockchain.ledger_file.ChainAnalyzer;
import blockchain.ledger_file.LedgerReader;
import blockchain.ledger_file.LedgerWriter;

import java.nio.file.Path;
import java.util.ArrayList;

/*
 * Provides an overview of the blockchain's basic functionality
 */

public class BlockchainClient {

    private final Path ledgerFilePath;
    private final LedgerReader ledgerReader;
    private final LedgerWriter ledgerWriter;

    private BlockBuilder blockBuilder;

    private final ChainAnalyzer chainAnalyzer;

    public BlockchainClient(Path ledgerFilePath) {
        this.ledgerFilePath = ledgerFilePath;

        ledgerWriter = new LedgerWriter(ledgerFilePath);
        ledgerReader = new LedgerReader(ledgerFilePath);
        blockBuilder = new BlockBuilder(ledgerReader);
        chainAnalyzer = new ChainAnalyzer(ledgerReader);
    }

    // Adds a given data point to the current block if it is valid
    public DataPointUID addDataPoint(DataPoint dataPoint) throws InsufficientFundsException {
        return blockBuilder.addData(dataPoint);
    }

    // Returns all data points that contain the given id (userId, fileId etc.)
    public ArrayList<DataPoint> getDataPoints(String id) {
        return chainAnalyzer.getDataPoints(id);
    }

    // Retrieves a data point given an identifier
    public DataPoint getDataPoint(DataPointUID dataPointUID) {
        return ledgerReader.getDataPoint(dataPointUID);
    }

    // Finds the users balance by summing all transactions and contracts involving this user
    public double getUserBalance(String userID) {
        return chainAnalyzer.getUserBalance(userID)
                + ChainAnalyzer.getBalanceChange(blockBuilder.getCurrentData(), userID);
    }

    // Mines the current block and appends it to the ledger
    public void mineCurrentBlock(String minerID) {
        Block block = blockBuilder.build(minerID);
        ledgerWriter.writeBlock(block);

        // Reset block builder, initiating the creation of a new block
        blockBuilder = new BlockBuilder(ledgerReader);
    }

    // Checks the validity of the entire chain
    public boolean isChainValid() {
        return chainAnalyzer.isChainValid();
    }

    public Path getLedgerFilePath() {
        return ledgerFilePath;
    }

    public void printBlockChain(){
        long blockCount = ledgerReader.getBlockCount();

        for(long l = 0; l < blockCount; l++){
            System.out.println(ledgerReader.readBlock(l).toString() + "\n");
        }

    }

}
