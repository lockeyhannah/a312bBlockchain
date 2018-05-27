package blockchain.block.mining;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

class HasherTest {

    @Test // Test that two hashes of the same input gives the same output
    public void sameHashTest() {
        byte[] output1 = Hasher.applySHA("message".getBytes());
        byte[] output2 = Hasher.applySHA("message".getBytes());

        assertArrayEquals(output1, output2);
    }

    @Test // Test that a slightly altered input gives a different output
    public void differentHashTest() {
        byte[] output1 = Hasher.applySHA("message".getBytes());
        byte[] output2 = Hasher.applySHA("messag".getBytes());

        assertFalse(Arrays.compare(output1, output2) == 0);
    }

    @Test // Test the byte to hex converter
    public void toHexTest() {
        // Write 0x6C21 in byte format
        byte[] b = {108, 33};

        // Convert to string and test that it matches its original hex value
        String hexString = Hasher.bytesToHexString(b);
        assertEquals(hexString, "6c21");
    }
}