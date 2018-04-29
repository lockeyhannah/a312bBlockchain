package blockchain.utility;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ByteUtils {
    // TODO: 24-04-2018 : Også stjålet fra det store interweb - forstå hvad der sker

    public static byte[] toByteArray(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static byte[] toByteArray(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(x);
        return buffer.array();
    }

    public static byte[] toByteArray(double value) {
        byte[] bytes = new byte[Double.BYTES];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    public static byte[] toByteArray(short value) {
        byte[] bytes = new byte[Short.BYTES];
        ByteBuffer.wrap(bytes).putShort(value);
        return bytes;
    }

    public static long bytesToLong(byte[] bytes) { // TODO: 29-04-2018 Fix consistency
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public static int toInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static short toShort(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getShort();
    }

    public static byte[] combineByteArrays(byte[] array1, byte[] array2){
        ByteBuffer buffer = ByteBuffer.allocate(array1.length + array2.length);
        buffer.put(array1);
        buffer.put(array2);
        return buffer.array();
    }

    public static byte[] combineByteArrays(ArrayList<byte[]> byteList){
        if(byteList == null) return null;
        int totalBytes = 0;

        // Find total byte size
        for(int i = 0; i < byteList.size(); i++){
            totalBytes += byteList.get(i).length;
        }

        // Combine all arrays
        ByteBuffer buffer = ByteBuffer.allocate(totalBytes);
        for(int i = 0; i < byteList.size(); i++){
            buffer.put(byteList.get(i));
        }

        return buffer.array();
    }


    public static byte[] extendByteArray(byte[] array, int targetLength){
        int extraBytes = targetLength - (array.length);
        byte[] precedingBytes = new byte[extraBytes];
        return combineByteArrays(precedingBytes, array);
    }





}
