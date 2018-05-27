package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.block.data_points.InsufficientFundsException;
import blockchain.block.data_points.StorageContract;
import blockchain.block.data_points.TokenTransaction;
import blockchain.block.mining.BlockBuilder;
import blockchain.ledger_file.convertion.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class ConverterTest {

    // Generates a new block based on the given chain
    public static Block generateBlock(LedgerReader ledgerReader, String minerID) {
        BlockBuilder blockBuilder = new BlockBuilder(ledgerReader);

        int amountOfContracts = 7;
        for (int i = 0; i < amountOfContracts; i++) {
            try {
                blockBuilder.addData(new StorageContract("5" + i, "192.168.1.5" + i, "196.177.1.5" + i, System.currentTimeMillis(), 0));
            } catch (InsufficientFundsException e) {
                fail("Could not construct block because of insufficient funds");
                e.printStackTrace();
            }
        }

        return blockBuilder.build(minerID);
    }

    // Generates a block based on an empty chain
    public static Block generateBlock() {
        return generateBlock(new LedgerReader(Paths.get(LedgerWriterReaderTest.emptyFilePath)), "1");
    }

    @Test // Tests that a TokenTransaction object can be correctly converted to bytes and back
    public void tokenTransferConverterTest() {
        TokenTransaction transaction = new TokenTransaction("giver", "receiver", 0.568);
        TokenTransactionConverter converter = new TokenTransactionConverter((short) 1);

        byte[] transactionBytes = converter.bytesFromInstance(transaction);
        TokenTransaction convertedTransaction = converter.instanceFromBytes(transactionBytes);

        // Test that the contract still contains the same information
        assertEquals(transaction.getGiverID(), convertedTransaction.getGiverID());
        assertEquals(transaction.getRecipientID(), convertedTransaction.getRecipientID());
        assertEquals(transaction.getTokens(), convertedTransaction.getTokens(), 0);
    }

    @Test // Tests that a StorageContract object can be correctly converted to bytes and back
    public void storageContractConverterTest() {
        String ownerId = "192.168.1.50", storageID = "192.168.1.51";
        StorageContract originalContract = new StorageContract("56", storageID, ownerId, System.currentTimeMillis(), 0.35);

        StorageContractConverter converter = new StorageContractConverter((short) 1);
        byte[] contractBytes = converter.bytesFromInstance(originalContract);
        StorageContract newContract = converter.instanceFromBytes(contractBytes);

        // Test that the contract still contains the same information
        assertEquals(originalContract.getFileId(), newContract.getFileId());
        assertEquals(originalContract.getFileOwnerID(), newContract.getFileOwnerID());
        assertEquals(originalContract.getStorageUnitID(), newContract.getStorageUnitID());
        assertEquals(originalContract.getContractTerminationTime(), newContract.getContractTerminationTime());
        assertEquals(originalContract.getReward(), newContract.getReward(), 0);
    }


    @Test // Tests that a data object can be correctly converted to bytes and back
    public void DataConverterTest() {
        Data data = generateBlock().getData();

        DataConverter dataConverter = new DataConverter((short) 1);
        byte[] dataBytes = dataConverter.bytesFromInstance(data);
        Data convertedData = dataConverter.instanceFromBytes(dataBytes);

        assertEquals(data.toString(), convertedData.toString());
    }

    @Test // Tests that a header object can be correctly converted to bytes and back
    public void HeaderConverterTest() {
        Header header = generateBlock().getHeader();

        HeaderConverter headerConverter = new HeaderConverter((short) 1);
        byte[] headerBytes = headerConverter.bytesFromInstance(header);
        Header convertedHeader = headerConverter.instanceFromBytes(headerBytes);

        assertEquals(header.toString(), convertedHeader.toString());
    }



}
