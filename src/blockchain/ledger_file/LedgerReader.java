package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.DataPointUID;
import blockchain.ledger_file.convertion.BlockConverter;
import blockchain.utility.ByteUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static blockchain.ledger_file.LedgerWriter.BLOCK_SIZE_BYTE_LEN;

public class LedgerReader {

    // Path of the ledger file
    private final Path ledgerFilePath;

    public LedgerReader(Path ledgerFilePath) {
        this.ledgerFilePath = ledgerFilePath;

        if (!Files.exists(ledgerFilePath)) {
            System.out.println("Creating new ledger file : " + ledgerFilePath.toString());
            LedgerWriter.createNewLedgerFile(ledgerFilePath);
        }
    }

    // Reads and returns the block with the given block number from the ledger file,
    // returns null if no such block could be read.
    public Block readBlock(long blockNum) {
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(ledgerFilePath))) {
            long skippedBlocks = 0;

            // Skip all blocks prior to the target block
            while (skippedBlocks < blockNum) {
                // Skip next block
                if (skipNextBlock(bis))
                    skippedBlocks++;
                else // Return null if block could not be skipped
                    return null;
            }

            // Return the next block in the chain
            return readNextBlock(bis);

        } catch (IOException e) {
            System.out.println("Could not read block " + blockNum + " - at path " + ledgerFilePath.toString());
        }

        return null;
    }

    // Reads and returns the next block from the given input stream
    private Block readNextBlock(BufferedInputStream bis) throws IOException {
        byte[] chainVersionBytes = new byte[LedgerWriter.BLOCKCHAIN_VERSION_BYTE_LEN];
        byte[] blockSizeBytes = new byte[BLOCK_SIZE_BYTE_LEN];

        // Read blockchain version into byte array and return null if end of file is reached
        if (bis.read(chainVersionBytes, 0, LedgerWriter.BLOCKCHAIN_VERSION_BYTE_LEN) == -1)
            return null;

        // Read block size
        bis.read(blockSizeBytes, 0, BLOCK_SIZE_BYTE_LEN);
        // Convert block size to an integer
        int blockSize = ByteUtils.toInt(blockSizeBytes);

        // Create a new array and read block bytes into it
        byte[] blockBytes = new byte[blockSize];
        bis.read(blockBytes, 0, blockSize);

        // Initiate converter with the version read from the file
        BlockConverter blockConverter = new BlockConverter(ByteUtils.toShort(chainVersionBytes));
        // Convert bytes into a block object and return it
        return blockConverter.instanceFromBytes(blockBytes);
    }

    // Skips the next block from the given input stream
    private boolean skipNextBlock(BufferedInputStream bis) throws IOException {
        byte[] chainVersionBytes = new byte[LedgerWriter.BLOCKCHAIN_VERSION_BYTE_LEN];
        byte[] blockSizeBytes = new byte[BLOCK_SIZE_BYTE_LEN];

        // Read blockchain version into byte array and return null if end of file is reached
        if (bis.read(chainVersionBytes, 0, LedgerWriter.BLOCKCHAIN_VERSION_BYTE_LEN) == -1)
            return false;

        // Read block size and convert it to an integer
        bis.read(blockSizeBytes, 0, BLOCK_SIZE_BYTE_LEN);
        int blockSize = ByteUtils.toInt(blockSizeBytes);

        // Skip to next block and return true if it skipped the correct amount of bytes
        long totalSkippedBytes = bis.skip(blockSize);

        // Skip bytes until the end of the block
        while (totalSkippedBytes < blockSize) {
            // Attempt to skip till the end of the block and get actual amount of bytes skipped
            long tempSkipped = bis.skip(blockSize - totalSkippedBytes);

            //Return false if no bytes could be skipped
            if (tempSkipped < 0) return false;

            totalSkippedBytes += tempSkipped;
        }

        return true;
    }

    // Return the block most recently added to the chain
    public Block getNewestBlock() {
        return readBlock(getBlockCount() - 1);
    }

    // Returns the amount of blocks currently stored in the chain
    public long getBlockCount() {
        long blockCount = 0;

        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(ledgerFilePath))) {

            // Skip blocks until end of file and increment counter for each block skipped
            while (skipNextBlock(bis)) {
                blockCount++;
            }

        } catch (IOException e) {
            System.out.println("Could not read ledger file - " + ledgerFilePath.toString());
            return 0;
        }

        return blockCount;
    }

    // Returns a data point from the ledger based on the given unique data point id
    public DataPoint getDataPoint(DataPointUID uid) {
        Block block = readBlock(uid.getBlockId());

        if (block != null)
            return block.getData().getDataPoints().get(uid.getDataPointNumber());

        return null;
    }


}


