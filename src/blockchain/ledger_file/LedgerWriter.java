package blockchain.ledger_file;

import blockchain.block.Block;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.APPEND;


public class LedgerWriter {

    private Path ledgerFilePath;

/*

    public LedgerWriter(Path ledgerFilePath){
           this.ledgerFilePath = ledgerFilePath;
    }

    // Appends a block to the blockchain
    public void writeBlock(Block block){




        byte[] nonce = block.getHeader().getNonce(); //suppose to be 32 bytes long.
        byte[] timeStamp = block.getHeader().getTimeStamp(); //suppose to be 8 bytes long.
        byte[] dataHash = block.getHeader().getDataHash(); //suppose to be 32 bytes long.
        byte[] prevHash = block.getHeader().getPrevHash(); //suppose to be 32 bytes long.
        byte[] target = block.getHeader().getTarget(); //suppose to be 32 bytes long.
        long blockNum = block.getHeader().getBlockId();//suppose to be 4 bytes long.
        byte[] data = block.getData().getByteArray();

        byte[] blockBytes = block.getByteArray();

        //writes the header
        writeHeaderToFile(longToBytes(blockNum),4);
        writeHeaderToFile(prevHash, 32);
        writeHeaderToFile(dataHash, 32);
        writeHeaderToFile(nonce, 32);
        writeHeaderToFile(target, 32);
        writeHeaderToFile(timeStamp, 8);


        //Writes the data
        writeDataToFile(data.toString());

    }

    // Appends the given data to the ledger file
    private void writeToFile(byte[] bytes){
        writeHeaderToFile(bytes, bytes.length);
    }

    // Appends a byte array of target size to the ledger file
    // Adds preceding zeroes to compensate if the given byte array is smaller than the targetsize
    private void writeHeaderToFile( byte[] bytes, int targetSize){
        int extraBytes = targetSize - bytes.length; // TODO: 23-04-2018 : Might be off by one kan ikke tænke nu pls hjælp og test

        // NOTE : Files.newOutPutStream is given a second optional input : APPEND
        // This makes the outputStream append to the file rather than overwrite the existing data
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(ledgerFilePath, APPEND))){

            // Add preceding zeroes to match target size
            for(int i = 0; i < extraBytes; i++){
                bos.write(0);
            }

            bos.write(bytes);
        } catch (FileNotFoundException e){
            System.out.println("Error. File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error writing bytes to ledger file");
            e.printStackTrace();
        }
    }

    private byte[] longToBytes(long num) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(num);
        return buffer.array();
    }

    //Syntes at data bør være i string format. Dette gør det nemt at finde en slutning og en begyndelse på.
    private String writeDataToFile(String input) {
        try (FileWriter fileWriter = new FileWriter(ledgerFilePath.toString(), true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter output = new PrintWriter(bufferedWriter)) {
            output.println(input);
            return "Data added to block.";
        } catch (IOException e) {
            System.out.println("Error! This file doesn't exist: Block.txt");
            return "An Error has occured. Data was not saved.";
        }

    }
*/}
