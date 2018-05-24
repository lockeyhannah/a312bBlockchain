package blockchain.ledger_file.convertion;

import blockchain.block.Block;
import blockchain.ledger_file.ConverterTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BlockConverterTest {

    private BlockConverter converter;
    private Block block;
    private Block block2;
    private byte[] bytes1;
    private byte[] bytes2;

    @BeforeEach
    public void setup() {
        converter = new BlockConverter((short) 2);
        block = ConverterTest.generateBlock();
        block2 = block;
        bytes1 = converter.bytesFromInstance(block);
        bytes2 = converter.bytesFromInstance(block2);

    }

    @Test
    public void sameBytesTest() {
        assertTrue(Arrays.compare(bytes1, bytes2) == 0);
    }

    @Test
    public void sameStringTest(){
        block = converter.instanceFromBytes(bytes1);

        assertEquals(block.toString(), block2.toString());
    }

    @Test
    public void sameUIDTest(){
        assertEquals((short) 1, converter.getOBJECT_TYPE_UID());
    }

    @Test
    public void sameByteSizeTest(){
        assertEquals(-1, converter.getByteSize());
    }


}