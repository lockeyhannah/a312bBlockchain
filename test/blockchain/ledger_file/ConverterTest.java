package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.FileOverview;
import blockchain.block.data_points.StorageContract;
import blockchain.block.mining.BlockGenerator;
import blockchain.block.mining.Hasher;
import blockchain.ledger_file.convertion.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ConverterTest {


    public static Block generateBlock(Header previousHeader){
        Header header = new Header(3516, Hasher.applySHA("test".getBytes()), Hasher.applySHA("woops".getBytes()), BigInteger.valueOf(12).toByteArray(), BigInteger.valueOf(98765).toByteArray(), "today");

        int amountOfFiles = 15;
        ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();
        for(int j = 0; j < amountOfFiles; j++){

            int amountOfContracts = 7;
            StorageContract[] contracts = new StorageContract[amountOfContracts];
            for(int i = 0; i < amountOfContracts; i++){
                contracts[i] = new StorageContract("5"+i, "192.168.1.5" + i, "OwO what's this", 10000000, 0.35);
            }

            dataPoints.add(new FileOverview("Me.me.more.me", "file_" + j, contracts));
        }

        Data data = new Data(dataPoints);
        BigInteger difficulty = BigInteger.TWO.pow(235);
        return BlockGenerator.generateBlock(data, previousHeader);
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
        StorageContract[] contracts = new StorageContract[amountOfContracts];
        String expetedString, resultingString;
        for(int i = 0; i < amountOfContracts; i++){
            contracts[i] = new StorageContract("5"+i, "192.168.1.5" + i, "OwO what's this", 10000000, 0.35);
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
        testBlock.printBlock();

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
