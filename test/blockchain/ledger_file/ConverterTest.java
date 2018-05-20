package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.block.data_points.FileOverview;
import blockchain.block.data_points.InsufficientFundsException;
import blockchain.block.data_points.StorageContract;
import blockchain.block.mining.BlockBuilder;
import blockchain.ledger_file.convertion.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class ConverterTest {

    // Generates a new block with random contents
    public static Block generateBlock(LedgerReader ledgerReader){
        BlockBuilder blockBuilder = new BlockBuilder(ledgerReader);

        int amountOfFiles = 15;
        for(int j = 0; j < amountOfFiles; j++){

            int amountOfContracts = 7;
            ArrayList<StorageContract> contracts = new ArrayList<>();
            for(int i = 0; i < amountOfContracts; i++){
                contracts.add(new StorageContract("5" + i, "192.168.1.5" + i, System.currentTimeMillis(),0));
            }

            try {
                blockBuilder.addData(new FileOverview("Me.me.more.me", "file_" + j, contracts));
            } catch (InsufficientFundsException e) {
                e.printStackTrace();
                System.out.println(e.getDataPoint().getFormattedDataString());
            }
        }

        return blockBuilder.build();
    }

    public static Block generateBlock(){
        return generateBlock(new LedgerReader(Paths.get(LedgerWriterReaderTest.emptyFilePath)));
    }

    @Test
    public void StorageContractConverterTest(){
        StorageContract originalContract = new StorageContract("56", "192.168.1.50", System.currentTimeMillis(), 0.35);

        StorageContractConverter converter = new StorageContractConverter((short) 1);
        byte[] contractBytes = converter.bytesFromInstance(originalContract);
        StorageContract newContract = converter.instanceFromBytes(contractBytes);

        Assert.assertEquals(originalContract.getChunkId(), newContract.getChunkId());
        Assert.assertEquals(originalContract.getStorageID(), newContract.getStorageID());
        Assert.assertEquals(originalContract.getContractTerminationTime(), newContract.getContractTerminationTime());
        Assert.assertEquals(originalContract.getReward(), newContract.getReward(), 0);
    }

    @Test
    public void FileOverViewConverterTest(){
        int amountOfContracts = 7;
        ArrayList<StorageContract> contracts = new ArrayList<>();
        String expectedString, resultingString;
        for(int i = 0; i < amountOfContracts; i++){
            contracts.add(new StorageContract("5"+i, "192.168.1.5" + i,
                    System.currentTimeMillis(), 0.35));
        }

        FileOverview fileOw = new FileOverview("Me.me.more.me", "filename", contracts);
        expectedString = fileOw.getFormattedDataString();

        FileOverViewConverter fileOwConverter = new FileOverViewConverter((short) 1);
        byte[] fileOwBytes = fileOwConverter.bytesFromInstance(fileOw);

        fileOw = fileOwConverter.instanceFromBytes(fileOwBytes);
        resultingString = fileOw.getFormattedDataString();

        Assert.assertEquals(resultingString, expectedString);
    }


    @Test
    public void DataConverterTest(){
        Block testBlock = generateBlock();

        Data data = testBlock.getData();

        DataConverter dataConverter = new DataConverter((short) 1);
        byte[] dataBytes = dataConverter.bytesFromInstance(data);

        data = dataConverter.instanceFromBytes(dataBytes);


        // Assert.assertEquals(resultingString, expetedString); // TODO: add asserts
    }

    @Test
    public void HeaderConverterTest(){
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
