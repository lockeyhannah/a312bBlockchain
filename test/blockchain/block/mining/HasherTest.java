package blockchain.block.mining;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

class HasherTest {

    byte[] msg1, msg2, output1, output2;
    String hex1, hex2;

    @BeforeEach
    public void setup(){
        msg1 = "message".getBytes();
        msg2 = "message".getBytes();
        output1 = Hasher.applySHA(msg1);
        output2 = Hasher.applySHA(msg2);
        hex1 = Hasher.bytesToHexString(output1);
        hex2 = Hasher.bytesToHexString(output2);
    }

    @Test // Test that hashes of the same input gives the same output
    public void sameHashTest(){
        byte[] output1 = Hasher.applySHA("message".getBytes());
        byte[] output2 = Hasher.applySHA("message".getBytes());

        assertArrayEquals(output1, output2);
    }

    @Test // Test that a slightly altered input gives a different output
    public void differentHashTest(){
        output1 = Hasher.applySHA("message".getBytes());
        output2 = Hasher.applySHA("messag".getBytes());

        assertFalse(Arrays.compare(output1, output2) == 0);
    }

    @Test // Test the byte to hex converter
    public void toHexTest(){
        // Write 0x6C21 in byte format
        byte[] b = {108, 33};

        String hexString = Hasher.bytesToHexString(b);
        assertEquals(hexString, "6c21");
    }
}