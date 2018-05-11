package blockchain.ledger_file.convertion;

import blockchain.block.Data;
import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.StorageContract;
import blockchain.ledger_file.ConverterTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DataConverterTest {

    Data data1, data2;
    DataPoint contract;
    ArrayList<DataPoint> list;
    byte[] bytes1, bytes2;
    DataConverter converter;

    @BeforeEach
    public void dataConverterTest00(){
        converter = new DataConverter((short)3);
        contract = new StorageContract("chunkID", "StorageIP",
                "TermTime", 4, 3);

        //
        list = new ArrayList<>();
        list.add(contract);
        data1 = ConverterTest.generateBlock(null).getData();
        data2 = data1;

        bytes1 = converter.bytesFromInstance(data1);
        bytes2 = converter.bytesFromInstance(data2);
    }

    @Test
    public void dataConverterTest01(){
        assertTrue(Arrays.compare(bytes1, bytes2) == 0);
    }

    @Test
    public void dataConverterTest02(){
        data1 = converter.instanceFromBytes(bytes1);
    }
}