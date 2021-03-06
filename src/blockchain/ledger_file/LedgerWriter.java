package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.ledger_file.convertion.BlockConverter;
import blockchain.utility.ByteUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.APPEND;


public class LedgerWriter {

    private final Path ledgerFilePath;
    public static final int BLOCK_SIZE_BYTE_LEN = Integer.BYTES;
    public static final int BLOCKCHAIN_VERSION_BYTE_LEN = Short.BYTES;

    public LedgerWriter(Path ledgerFilePath) {
        this.ledgerFilePath = ledgerFilePath;
        if (!Files.exists(ledgerFilePath)) createNewLedgerFile(ledgerFilePath);
    }

    public static void createNewLedgerFile(Path newFilePath) {
        try {
            Files.createFile(newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Appends a block to the ledger file
    public void writeBlock(Block block) {
        BlockConverter blockConverter = new BlockConverter((short) 1);

        // Convert block to bytes and find block size in bytes
        byte[] blockBytes = blockConverter.bytesFromInstance(block);
        byte[] blockLength = ByteUtils.extendByteArray(ByteUtils.toByteArray(blockBytes.length), BLOCK_SIZE_BYTE_LEN);
        byte[] blockChainVersion = ByteUtils.extendByteArray(ByteUtils.toByteArray(Block.BLOCKCHAIN_VERSION), BLOCKCHAIN_VERSION_BYTE_LEN);

        // Write blockchain version to the file so that the reader will know which converter version to use
        writeToFile(blockChainVersion);
        // Write block length to the file so that the parser will know how many bytes to read
        writeToFile(blockLength);
        // Write block bytes to the file
        writeToFile(blockBytes);
    }

    // Appends the given byte array to the end of the ledger file
    private void writeToFile(byte[] bytes) {

        // NOTE : Files.newOutPutStream is given a second optional input : APPEND
        // This makes the outputStream append to the file rather than overwrite the existing data
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(ledgerFilePath, APPEND))) {
            bos.write(bytes);
        } catch (NoSuchFileException e) {
            System.out.println("Could not write to ledger file : File not found - " + ledgerFilePath.toString());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error writing bytes to ledger file");
            e.printStackTrace();
        }
    }
}