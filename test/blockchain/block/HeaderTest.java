package blockchain.block;

import blockchain.block.mining.Hasher;
import blockchain.utility.ByteUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class HeaderTest {

    @Test
    public void setterTest(){
        Header header = new Header(4, new byte[]{3}, new byte[]{1},
                new byte[]{2}, new byte[]{3}, System.currentTimeMillis());

        header.setNonce(new byte[]{4});
        assertEquals(0, Arrays.compare(new byte[]{4}, header.getNonce()));

        header.setTimeStamp(System.currentTimeMillis());
        assertEquals(System.currentTimeMillis(), header.getTimeStamp());
    }
}