package blockchain.block.mining;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

class HasherTest {

    byte[] msg1, msg2, output1, output2;
    String hex1, hex2;

    @BeforeEach
    public void hasherTest00(){
        msg1 = "message".getBytes();
        msg2 = "message".getBytes();
        output1 = Hasher.applySHA(msg1);
        output2 = Hasher.applySHA(msg2);
        hex1 = Hasher.hashToHexString(output1);
        hex2 = Hasher.hashToHexString(output2);
    }

    @Test
    public void hasherTest01(){
        assertTrue(Arrays.compare(output1, output2) == 0);
    }

    @Test
    public void hasherTest02(){
        msg1 = "messag".getBytes();
        output1 = Hasher.applySHA(msg1);

        assertFalse(Arrays.compare(output1, output2) == 0);
    }

    @Test
    public void hasherTest03(){
        assertEquals(hex1, hex2);
    }

    @Test
    public void hasherTest04(){
        msg1 = "messag".getBytes();
        output1 = Hasher.applySHA(msg1);
        hex1 = Hasher.hashToHexString(output1);

        assertNotEquals(hex1, hex2);
    }
}