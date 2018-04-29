package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.ledger_file.convertion.BlockConverter;
import blockchain.utility.ByteUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static blockchain.ledger_file.LedgerWriter.BLOCK_SIZE_BYTE_LEN;

public class LedgerReader {

    private Path ledgerFilePath;

    public LedgerReader(Path ledgerFilePath) {
        this.ledgerFilePath = ledgerFilePath;
    }

    // TODO: 29-04-2018 : remove or change. Currently only exists for testing
    // Reads and returns the first block in the chain
    public Block getFirstBlock(){
        BlockConverter blockConverter = new BlockConverter((short) 1);
        byte[] bytes = readFirstBlock();
        return blockConverter.instanceFromBytes(bytes);
    }

    // Reads and returns the bytes of the first block in the ledger file
    public byte[] readFirstBlock(){
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(ledgerFilePath))) {
            // Read the block size identifier and convert to an integer
            byte[] blockSizeBytes = new byte[BLOCK_SIZE_BYTE_LEN];
            bis.read(blockSizeBytes, 0, BLOCK_SIZE_BYTE_LEN);
            int blockSize = ByteUtils.toInt(blockSizeBytes);

            // Create a new byte array of corresponding size and read the block bytes into it
            byte[] blockBytes = new byte[blockSize];
            bis.read(blockBytes, 0, blockSize);

            return blockBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // TODO: 29-04-2018 maybe return empty byte array instead?
    }

    // TODO: 24-04-2018 : make this shit
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


