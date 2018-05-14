package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.FileOverview;
import blockchain.block.data_points.StorageContract;
import blockchain.block.mining.BlockBuilder;
import blockchain.block.mining.BlockMiner;
import blockchain.block.mining.Hasher;
import blockchain.ledger_file.convertion.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;


public class ConverterTest {


    public static Block generateBlock(Header previousHeader){
        BlockBuilder blockBuilder = new BlockBuilder(previousHeader);

        int amountOfFiles = 15;
        for(int j = 0; j < amountOfFiles; j++){

            int amountOfContracts = 7;
            ArrayList<StorageContract> contracts = new ArrayList<>();
            for(int i = 0; i < amountOfContracts; i++){
                contracts.add(new StorageContract("5" + i, "192.168.1.5" + i, "OwO what's this", new Random().nextInt(1000000), 0.35));
            }

            blockBuilder.addData(new FileOverview("Me.me.more.me", "file_" + j, contracts));
        }

        return blockBuilder.build();
    }

    @Test
    public void StorageContractConverterTest(){
        StorageContract originalContract = new StorageContract("56", "192.168.1.50", "now!", 10000000, 0.35);
        System.out.println(originalContract.getFormattedDataString());

        StorageContractConverter converter = new StorageContractConverter((short) 1);
        byte[] contractBytes = converter.bytesFromInstance(originalContract);
        StorageContract newContract = converter.instanceFromBytes(contractBytes);

        Assert.assertEquals(originalContract.getChunkId(), newContract.getChunkId());
        Assert.assertEquals(originalContract.getChunkSize(), newContract.getChunkSize());
        Assert.assertEquals(originalContract.getStorageIp(), newContract.getStorageIp());
        Assert.assertEquals(originalContract.getContractTerminationTime(), newContract.getContractTerminationTime());
        Assert.assertEquals(originalContract.getReward(), newContract.getReward(), 0);
    }

    @Test
    public void FileOverViewConverterTest(){
        int amountOfContracts = 7;
        ArrayList<StorageContract> contracts = new ArrayList<>();
        String expetedString, resultingString;
        for(int i = 0; i < amountOfContracts; i++){
            contracts.add(new StorageContract("5"+i, "192.168.1.5" + i,
                    "OwO what's this", 10000000, 0.35));
        }

        FileOverview fileOw = new FileOverview("Me.me.more.me", "filename", contracts);
        System.out.println(expetedString = fileOw.getFormattedDataString());

        FileOverViewConverter fileOwConverter = new FileOverViewConverter((short) 1);
        byte[] fileOwBytes = fileOwConverter.bytesFromInstance(fileOw);

        fileOw = fileOwConverter.instanceFromBytes(fileOwBytes);
        System.out.println(resultingString = fileOw.getFormattedDataString());

        Assert.assertEquals(resultingString, expetedString);
    }


    @Test
    public void DataConverterTest(){
        Block testBlock = generateBlock(null);

        Data data = testBlock.getData();
        System.out.println(data.getString());
        DataConverter dataConverter = new DataConverter((short) 1);
        byte[] dataBytes = dataConverter.bytesFromInstance(data);

        data = dataConverter.instanceFromBytes(dataBytes);
        System.out.println(data.getString());


        // Assert.assertEquals(resultingString, expetedString); // TODO: add asserts
    }

    @Test
    public void HeaderConverterTest(){
        Header header = generateBlock(null).getHeader();
        System.out.println(header.getString());

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
