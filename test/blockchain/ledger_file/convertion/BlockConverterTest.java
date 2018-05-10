package blockchain.ledger_file.convertion;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlockConverterTest {

    BlockConverter converter;
    Block block, block2;
    Header header;
    Data data;
    byte[] bytes;

    @BeforeEach
    public void blockConverterTest00() {
        converter = new BlockConverter((short) 2);
        header = new Header(3, new byte[]{0}, new byte[]{1},
                new byte[]{2}, new byte[]{3}, "mandag");
        data = new Data(new ArrayList<>());
        block = new Block(header, data);
    }

    //TODO : make like header converter
    @Test
    public void blockConverterTest01() {
        bytes = converter.bytesFromInstance(block);
        block2 = converter.instanceFromBytes(bytes);

        assertEquals(block, block2);
    }

}