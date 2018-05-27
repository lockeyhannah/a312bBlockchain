package blockchain;

import blockchain.block.Block;
import blockchain.block.data_points.*;
import blockchain.block.mining.BlockBuilder;
import blockchain.ledger_file.ChainAnalyzer;
import blockchain.ledger_file.LedgerReader;
import blockchain.ledger_file.LedgerWriter;

import java.nio.file.Path;
import java.util.ArrayList;

public class BlockchainClient {

    private Path ledgerFilePath;
    private LedgerReader ledgerReader;
    private LedgerWriter ledgerWriter;

    private String clientID;
    private BlockBuilder blockBuilder;

    private ChainAnalyzer chainAnalyzer;

    public BlockchainClient(Path ledgerFilePath, String clientID){
        this.clientID = clientID;
        this.ledgerFilePath = ledgerFilePath;

        ledgerWriter = new LedgerWriter(ledgerFilePath);
        ledgerReader = new LedgerReader(ledgerFilePath);
        blockBuilder = new BlockBuilder(ledgerReader);
        chainAnalyzer = new ChainAnalyzer(ledgerReader);
    }

    // Adds a given data point to the current block if it is valid
    public DataPointUID addDataPoint(DataPoint dataPoint) throws InsufficientFundsException{
        return blockBuilder.addData(dataPoint);
    }

    // Returns data points that contain the given id (userId, fileId etc.)
    public ArrayList<DataPoint> getDataPoints(String id){
        return chainAnalyzer.getDataPoints(id);
    }

    public DataPoint getDataPoint(DataPointUID dataPointUID){
        return ledgerReader.getDataPoint(dataPointUID);
    }

    public double getUserBalance(String userID){
        return chainAnalyzer.getUserBalance(userID);
    }

    // Mines the current block and appends it to the ledger
    public void mineCurrentBlock(){
        Block block = blockBuilder.build(clientID);
        ledgerWriter.writeBlock(block);

        // Reset block builder, initiating the creation of a new block
        blockBuilder = new BlockBuilder(ledgerReader);
    }

    public boolean isChainValid(){
        return chainAnalyzer.isChainValid();
    }




}
