package blockchain.ledger_file.convertion;

import blockchain.block.Block;
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
    StorageContract contract;
    ArrayList<DataPoint> list;
    byte[] bytes1, bytes2;
    DataConverter converter;

    @BeforeEach
    public void dataConverterTest00(){
        converter = new DataConverter((short)1);
        contract = new StorageContract("chunkID", "OwnerID","StorageIP",
                System.currentTimeMillis(), 3);

        data1 = ConverterTest.generateBlock().getData();
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
        Data data1copy = converter.instanceFromBytes(bytes1);
        assertEquals(data1.getString(), data1copy.getString());
    }

    @Test
    public void dataConverterTest03(){
        assertEquals(3, converter.getOBJECT_TYPE_UID());
        assertEquals(-1, converter.getByteSize());
    }
}
