package blockchain.ledger_file;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ByteUtils {
    // TODO: 24-04-2018 : Også stjålet fra det store interweb - forstå hvad der sker

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        //Det lyder til at flip flipper den fra at være en write til read,
        //så efter man flipper så kan den printes idk
        buffer.flip();
        return buffer.getLong();
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
        int extraBytes = targetLength - array.length;
        byte[] precedingBytes = new byte[extraBytes];
        return combineByteArrays(precedingBytes, array);
    }



}
