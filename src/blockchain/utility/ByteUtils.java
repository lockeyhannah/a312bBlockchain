package blockchain.utility;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class ByteUtils {

    //Converts long to byte array
    public static byte[] toByteArray(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    //Converts int to byte array
    public static byte[] toByteArray(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(x);
        return buffer.array();
    }

    //Converts double to byte array
    public static byte[] toByteArray(double value) {
        byte[] bytes = new byte[Double.BYTES];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    //Converts short to byte array
    public static byte[] toByteArray(short value) {
        byte[] bytes = new byte[Short.BYTES];
        ByteBuffer.wrap(bytes).putShort(value);
        return bytes;
    }

    //Converts byte array to long
    public static long toLong(byte[] bytes) {
        if (bytes.length < Long.BYTES) bytes = extendByteArray(bytes, Long.BYTES);
        return ByteBuffer.wrap(bytes).getLong();
    }

    //Converts byte array to double
    public static double toDouble(byte[] bytes) {
        if (bytes.length < Double.BYTES) bytes = extendByteArray(bytes, Double.BYTES);
        return ByteBuffer.wrap(bytes).getDouble();
    }

    //Converts byte array to int
    public static int toInt(byte[] bytes) {
        if (bytes.length < Integer.BYTES) bytes = extendByteArray(bytes, Integer.BYTES);
        return ByteBuffer.wrap(bytes).getInt();
    }

    //Converts byte array to short
    public static short toShort(byte[] bytes) {
        if (bytes.length < Short.BYTES) bytes = extendByteArray(bytes, Short.BYTES);
        return ByteBuffer.wrap(bytes).getShort();
    }

    // Combines two byte arrays
    public static byte[] combineByteArrays(byte[] array1, byte[] array2) {
        ByteBuffer buffer = ByteBuffer.allocate(array1.length + array2.length);
        buffer.put(array1);
        buffer.put(array2);
        return buffer.array();
    }

    // Combines a list of byte arrays
    public static byte[] combineByteArrays(ArrayList<byte[]> byteList) {
        if (byteList == null) return null;
        int totalBytes = 0;

        // Find total byte size
        for (int i = 0; i < byteList.size(); i++)
            totalBytes += byteList.get(i).length;

        // Combine all arrays
        ByteBuffer buffer = ByteBuffer.allocate(totalBytes);
        for (int i = 0; i < byteList.size(); i++)
            buffer.put(byteList.get(i));

        return buffer.array();
    }

    // Adds leading zeroes to a byte array until it has the target length
    public static byte[] extendByteArray(byte[] array, int targetLength) {
        int extraBytes = targetLength - (array.length);
        if (extraBytes <= 0) return array;
        byte[] precedingBytes = new byte[extraBytes];
        return combineByteArrays(precedingBytes, array);
    }

    // Removes the leading zeroes from the given byte array
    public static byte[] trimLeadingZeroes(byte[] inputBytes) {
        int len = inputBytes.length;
        int leadingZeroes = 0;

        // Count amount of leading zeroes
        for (int i = 0; i < len; i++) {
            if (inputBytes[i] == 0) leadingZeroes++;
            else break;
        }

        return Arrays.copyOfRange(inputBytes, leadingZeroes, len);
    }


}
