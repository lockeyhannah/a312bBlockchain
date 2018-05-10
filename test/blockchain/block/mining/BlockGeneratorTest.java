package blockchain.block.mining;

import blockchain.block.Block;
import blockchain.block.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class BlockGeneratorTest {

    Data data;
    Block block;

    @BeforeEach
    public void blockGeneratorTest00() {
        data = new Data(new ArrayList<>());
        block = BlockGenerator.generateBlock(data, new byte[]{3}, new byte[]{10});
    }

    @Test
    public void blockGeneratorTest01() {
        assertTrue(Arrays.compare(block.getHeader().getNonce(), new byte[]{0}) == 0);
    }

    @Test
    public void blockGeneratorTest02() {
        assertEquals(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()),
                BlockGenerator.generateTimeStamp());
    }
}