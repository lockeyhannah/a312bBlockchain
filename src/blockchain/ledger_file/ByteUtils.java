package blockchain.ledger_file;

import java.nio.ByteBuffer;

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
        buffer.flip();
        return buffer.getLong();
    }


}
