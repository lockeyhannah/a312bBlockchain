package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class LedgerReader {

    private Path ledgerFilePath;

    public LedgerReader(Path ledgerFilePath) {
        this.ledgerFilePath = ledgerFilePath;
    }

    // Reads and returns the block with the given id
    public Block readBlock(long blockID){
        return null;
    }

    // TODO: 24-04-2018 : make this shit
    //Returns the hash of the previous blocks header
    public byte[] getPreviousHeaderHash(){
        return null;
    }

    public byte[] readBytes(int byteCount){
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(ledgerFilePath))) {
            byte[] bytes = new byte[byteCount];
            bis.read(bytes, 0, 8);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] longToBytes(long num) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(num);
        return buffer.array();
    }

    private long bytesToLong(byte[] bit) {
        ByteBuffer buffer = ByteBuffer.allocate(bit.length);
        buffer.put(bit);
        return buffer.getLong();
    }
}


