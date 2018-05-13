package blockchain.block;

import blockchain.block.mining.Hasher;
import blockchain.utility.ByteUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {

    Header header;

    @BeforeEach
    public void headerTest00(){
        header = new Header(4, new byte[]{3}, new byte[]{1}, new byte[]{2}, new byte[]{3}, "mandag");
    }

    @Test
    public void headerTest01(){
        assertTrue(Arrays.compare(new byte[]{3}, header.getDifficultyTarget()) == 0);
        assertTrue(Arrays.compare(new byte[]{2}, header.getNonce()) == 0);
        assertTrue(Arrays.compare(new byte[]{1}, header.getDataHash()) == 0);
        assertTrue(Arrays.compare(new byte[]{3}, header.getPrevHash()) == 0);
        assertEquals(4, header.getBlockId());
    }

    @Test
    public void headerTest02(){
        header.setNonce(new byte[]{4});
        assertTrue(Arrays.compare(new byte[]{4}, header.getNonce()) == 0);
        header.setTimeStamp("tuesday");
        assertEquals(header.getTimeStamp(), "tuesday");
    }

}