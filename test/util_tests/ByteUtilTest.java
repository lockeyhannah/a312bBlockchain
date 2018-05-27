package util_tests;

import blockchain.utility.ByteUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ByteUtilTest {

    @Test
    public void trimZeroesTest(){
        byte[] bytes = {0, 0, 0, 0, 16, 65, 12, 25};
        byte[] bytes2 = {16, 65, 12, 25};
        byte[] expectedBytes = {16, 65, 12, 25};

        bytes = ByteUtils.trimLeadingZeroes(bytes);
        bytes2 = ByteUtils.trimLeadingZeroes(bytes2);

        assertArrayEquals(bytes, expectedBytes);
        assertArrayEquals(bytes2, expectedBytes);
    }

    @Test // Tests that different data type can be converted to bytes and back correctly
    public void toByteTest(){
        int originalInt = 30510;
        long originalLong = 30510;
        short originalShort = 30510;
        double originalDouble = 30.510;

        int convertedInt = ByteUtils.toInt(ByteUtils.toByteArray(originalInt));
        long convertedLong = ByteUtils.toLong(ByteUtils.toByteArray(originalLong));
        short convertedShort = ByteUtils.toShort(ByteUtils.toByteArray(originalShort));
        double convertedDouble = ByteUtils.toDouble(ByteUtils.toByteArray(originalDouble));

        assertEquals(convertedLong, originalLong);
        assertEquals(convertedInt, originalInt);
        assertEquals(convertedShort, originalShort);
        assertEquals(convertedDouble, originalDouble, 0.001);
    }

    @Test // Tests that different data types with negative values can be converted to bytes and back correctly
    public void negativeToByteTest(){
        int originalInt = -30510;
        long originalLong = -30510;
        short originalShort = -30510;
        double originalDouble = -30.510;

        int convertedInt = ByteUtils.toInt(ByteUtils.toByteArray(originalInt));
        long convertedLong = ByteUtils.toLong(ByteUtils.toByteArray(originalLong));
        short convertedShort = ByteUtils.toShort(ByteUtils.toByteArray(originalShort));
        double convertedDouble = ByteUtils.toDouble(ByteUtils.toByteArray(originalDouble));

        assertEquals(convertedLong, originalLong);
        assertEquals(convertedInt, originalInt);
        assertEquals(convertedShort, originalShort);
        assertEquals(convertedDouble, originalDouble, 0.001);
    }

    @Test // Tests that two byte arrays are correctly combined
    public void combineByteArrayTest(){
        byte[] array1 = {0, 15, 65, 15, 24, 35};
        byte[] array2 = {64, 65, 15, 24, 35};
        byte[] expectedArray = {0, 15, 65, 15, 24, 35, 64, 65, 15, 24, 35};

        byte[] combinedArray = ByteUtils.combineByteArrays(array1, array2);

        assertArrayEquals(combinedArray, expectedArray);
    }

    @Test // Tests that a list of byte arrays are combined correctly
    public void combineByteArrayListTest(){
        ArrayList<byte[]> byteList = new ArrayList<>();
        byte[] expectedArray = {0, 15, 65, 15, 24, 35, 64, 65, 15, 24, 35, 15};
        byteList.add(new byte[] {0, 15, 65, 15, 24, 35});
        byteList.add(new byte[] {64, 65, 15, 24, 35});
        byteList.add(new byte[] {});
        byteList.add(new byte[] {15});

        byte[] combinedArray = ByteUtils.combineByteArrays(byteList);

        assertArrayEquals(combinedArray, expectedArray);
    }

    @Test // Tests that leading zeroes are added to a byte array
    public void extendByteArrayTest(){
        byte[] bytes = {15, 65};
        byte[] expectedBytes = {0, 0, 0, 15, 65};

        // Test appending no zeroes
        byte[] noExtensionBytes = ByteUtils.extendByteArray(bytes, 0);
        assertArrayEquals(noExtensionBytes, bytes);

        byte[] extendedBytes = ByteUtils.extendByteArray(bytes, 5);
        assertArrayEquals(extendedBytes, expectedBytes);
    }


}
