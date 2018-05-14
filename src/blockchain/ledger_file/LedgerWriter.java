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

    private Path ledgerFilePath;
    public static final int BLOCK_SIZE_BYTE_LEN = Integer.BYTES;

    public LedgerWriter(Path ledgerFilePath) {// TODO: 29-04-2018  throw exception
        this.ledgerFilePath = ledgerFilePath;
    }

    // Appends a block to the ledger file
    public void writeBlock(Block block) {
        BlockConverter blockConverter = new BlockConverter((short) 1);

        // Convert block to bytes and find block size in bytes
        byte[] blockBytes = blockConverter.bytesFromInstance(block);
        byte[] blockLength = ByteUtils.extendByteArray(ByteUtils.toByteArray(blockBytes.length), BLOCK_SIZE_BYTE_LEN);

        //Write block length to the file so that the parser will know how many bytes to read
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
            System.out.println("Error. File not found.");
        } catch (IOException e) {
            System.out.println("Error writing bytes to ledger file");
        }
    }
}