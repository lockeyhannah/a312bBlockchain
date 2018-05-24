package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.block.data_points.InsufficientFundsException;
import blockchain.block.data_points.StorageContract;
import blockchain.block.mining.BlockBuilder;
import blockchain.ledger_file.convertion.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.fail;


public class ConverterTest {

    // Generates a new block with random contents
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

    public static Block generateBlock() {
        return generateBlock(new LedgerReader(Paths.get(LedgerWriterReaderTest.emptyFilePath)), "1");
    }

    @Test
    public void StorageContractConverterTest() {
        String ownerId = "192.168.1.50", storageID = "192.168.1.51";
        StorageContract originalContract = new StorageContract("56", storageID, ownerId, System.currentTimeMillis(), 0.35);

        StorageContractConverter converter = new StorageContractConverter((short) 1);
        byte[] contractBytes = converter.bytesFromInstance(originalContract);
        StorageContract newContract = converter.instanceFromBytes(contractBytes);

        Assert.assertEquals(originalContract.getFileId(), newContract.getFileId());
        Assert.assertEquals(originalContract.getFileOwnerID(), newContract.getFileOwnerID());
        Assert.assertEquals(originalContract.getStorageUnitID(), newContract.getStorageUnitID());
        Assert.assertEquals(originalContract.getContractTerminationTime(), newContract.getContractTerminationTime());
        Assert.assertEquals(originalContract.getReward(), newContract.getReward(), 0);
    }


    // TODO: 24-05-2018 Add storage converter test


    @Test
    public void DataConverterTest() {
        Block testBlock = generateBlock();

        Data data = testBlock.getData();

        DataConverter dataConverter = new DataConverter((short) 1);
        byte[] dataBytes = dataConverter.bytesFromInstance(data);

        data = dataConverter.instanceFromBytes(dataBytes);


        // Assert.assertEquals(resultingString, expetedString); // TODO: add asserts
    }

    @Test
    public void HeaderConverterTest() {
        Header header = generateBlock().getHeader();

        HeaderConverter headerConverter = new HeaderConverter((short) 1);
        byte[] headerBytes = headerConverter.bytesFromInstance(header);

        header = headerConverter.instanceFromBytes(headerBytes);
    }


   /* @Test
    public void blockConverterTest(){
        Block block = generateBlock();

        LedgerWriter ledgerWriter = new LedgerWriter(Paths.get("ledger.dat"));
        ledgerWriter.writeBlock(block);

        LedgerReader ledgerReader = null;
        try {
            ledgerReader = new LedgerReader(Paths.get("ledger.dat"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Block newBlock = ledgerReader.getFirstBlock();

        block.printBlock();
        newBlock.printBlock();
    }
*/

}
