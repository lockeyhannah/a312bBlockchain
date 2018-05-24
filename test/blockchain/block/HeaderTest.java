package blockchain.block;

import blockchain.block.mining.Hasher;
import blockchain.utility.ByteUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {

    Header header;

    @BeforeEach
    public void setup(){
        header = new Header(4, new byte[]{3}, new byte[]{1}, new byte[]{2}, new byte[]{3}, System.currentTimeMillis());
    }

    @Test
    public void sameHeaderTest(){
        assertEquals(0, Arrays.compare(new byte[]{3}, header.getDifficultyTarget()));
        assertEquals(0, Arrays.compare(new byte[]{2}, header.getNonce()));
        assertEquals(0, Arrays.compare(new byte[]{1}, header.getDataHash()));
        assertEquals(0, Arrays.compare(new byte[]{3}, header.getPrevHash()));
        assertEquals(4, header.getBlockId());
    }

    @Test
    public void sameTimeStampTest(){
        header.setNonce(new byte[]{4});
        assertEquals(0, Arrays.compare(new byte[]{4}, header.getNonce()));

        header.setTimeStamp(System.currentTimeMillis());
        assertEquals(System.currentTimeMillis(), header.getTimeStamp());
    }
}