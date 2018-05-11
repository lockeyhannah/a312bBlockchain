package blockchain.ledger_file.convertion;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.ledger_file.ConverterTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BlockConverterTest {

    BlockConverter converter;
    Block block, block2;
    byte[] bytes1, bytes2;

    @BeforeEach
    public void blockConverterTest00() {
        converter = new BlockConverter((short) 2);
        block = ConverterTest.generateBlock(new byte[32]);
        block2 = block;
        bytes1 = converter.bytesFromInstance(block);
        bytes2 = converter.bytesFromInstance(block2);

    }

    //TODO : make like header converter
    @Test
    public void blockConverterTest01() {
        assertTrue(Arrays.compare(bytes1, bytes2) == 0);
    }

    @Test
    public void blockConverterTest02(){
        block = converter.instanceFromBytes(bytes1);

        //assertEquals(block.getHeader().getNonce(), block2.getHeader().getNonce());
    }


}