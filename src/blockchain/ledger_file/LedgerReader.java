package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.ledger_file.convertion.BlockConverter;
import blockchain.ledger_file.convertion.HeaderConverter;
import blockchain.utility.ByteUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static blockchain.ledger_file.LedgerWriter.BLOCK_SIZE_BYTE_LEN;

public class LedgerReader {

    // Path of the ledger file
    private Path ledgerFilePath;

    // Converters for creating a block from bytes
    private BlockConverter blockConverter;
    private HeaderConverter headerConverter;

    public LedgerReader(Path ledgerFilePath) throws FileNotFoundException{
        this.ledgerFilePath = ledgerFilePath;

        if(!Files.exists(ledgerFilePath))
            throw new FileNotFoundException("Ledger file note found : " + ledgerFilePath.toString());
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

        return null;
    }

    // Reads and returns the block with the given id. Returns null if no matching block was found
    public Block readBlock(long blockID){

        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(ledgerFilePath))) {
            byte[] blockSizeBytes = new byte[BLOCK_SIZE_BYTE_LEN];
            BlockConverter blockConverter = new BlockConverter((short) 1);

            // read while there's blocks left to read
            while(bis.read(blockSizeBytes, 0, BLOCK_SIZE_BYTE_LEN) != -1){
                // Convert Block size to an integer
                int blockSize = ByteUtils.toInt(blockSizeBytes);

                // Create a new byte array of corresponding size and read the block bytes into it
                byte[] blockBytes = new byte[blockSize];
                bis.read(blockBytes, 0, blockSize);

                // Convert bytes to a block object
                Block block = blockConverter.instanceFromBytes(blockBytes);

                // Return block if it matches the given blockID
                if(block.getHeader().getBlockId() == blockID) return block;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return null if no block was found with the given ID
        return null;
    }

    private byte[] readBytes(int byteCount){
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(ledgerFilePath))) {
            byte[] bytes = new byte[byteCount];
            // TODO: 13-05-2018 hva sker der her
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


