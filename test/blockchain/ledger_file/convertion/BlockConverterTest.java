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
    public void blockConverterTest00() {
        converter = new BlockConverter((short) 2);
        block = ConverterTest.generateBlock(null);
        block2 = block;
        bytes1 = converter.bytesFromInstance(block);
        bytes2 = converter.bytesFromInstance(block2);

    }

    @Test
    public void blockConverterTest01() {
        assertTrue(Arrays.compare(bytes1, bytes2) == 0);
    }

    @Test
    public void blockConverterTest02(){
        block = converter.instanceFromBytes(bytes1);

        assertEquals(block.toString(), block2.toString());
    }

    @Test
    public void blockConverterTest03(){
        assertEquals((short) 1, converter.getOBJECT_TYPE_UID());
    }

    @Test
    public void blockConverterTest04(){
        assertEquals(-1, converter.getByteSize());
    }


}