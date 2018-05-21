package blockchain;

import blockchain.block.Block;
import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.DataPointUID;
import blockchain.block.data_points.InsufficientFundsException;
import blockchain.block.mining.BlockBuilder;
import blockchain.ledger_file.ChainAnalyzer;
import blockchain.ledger_file.LedgerReader;
import blockchain.ledger_file.LedgerWriter;

import java.nio.file.Path;

public class BlockchainClient {

    private Path ledgerFilePath;
    private LedgerReader ledgerReader;
    private LedgerWriter ledgerWriter;

    private String clientID;
    private BlockBuilder blockBuilder;

    public BlockchainClient(Path ledgerFilePath, String clientID){
        this.clientID = clientID;
        this.ledgerFilePath = ledgerFilePath;

        ledgerWriter = new LedgerWriter(ledgerFilePath);
        ledgerReader = new LedgerReader(ledgerFilePath);
        blockBuilder = new BlockBuilder(ledgerReader);
    }

    // Adds a given data point to the current block if it is valid
    public DataPointUID addDataPoint(DataPoint dataPoint) throws InsufficientFundsException{
        return blockBuilder.addData(dataPoint);
    }

    public DataPoint getDataPoint(DataPointUID dataPointUID){
        return ledgerReader.getDataPoint(dataPointUID);
    }

    public double getUserBalance(String userID){
        return ChainAnalyzer.getUserBalance(ledgerReader, userID);
    }

    // Mines the current block and appends it to the ledger
    public void mineCurrentBlock(){
        Block block = blockBuilder.build(clientID);
        ledgerWriter.writeBlock(block);

        // Reset block builder, initiating the creation of a new block
        blockBuilder = new BlockBuilder(ledgerReader);
    }

    public boolean isChainValid(){
        return ChainAnalyzer.isChainValid(ledgerReader);
    }




}
