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
/*
    private Path ledgerFilePath;

    public LedgerReader(Path ledgerFilePath) {
        this.ledgerFilePath = ledgerFilePath;
    }

    // Reads and returns the block with the given id
    public Block readBlock(long blockID){
        byte[] getBytes;
        byte[] target = longToBytes(blockID);
        int x = 0;
        try {
            File file = new File(ledgerFilePath.toString());
            getBytes = new byte[4];
            InputStream is = new FileInputStream(file);
            while(is.read(getBytes, x, 4) != -1) {
                if (Arrays.compare(getBytes,target) == 0) {
                    byte[] tempPrevHash = new byte[32];
                    is.read(tempPrevHash, x+4, 32);
                    byte[] tempDataHash = new byte[32];
                    is.read(tempDataHash, x+36, 32);
                    byte[] tempNonce = new byte[32];
                    is.read(tempNonce, x+68, 32);
                    byte[] tempTarget = new byte[32];
                    is.read(tempTarget, x+100, 32);
                    byte[] tempTimestamp = new byte[8];
                    is.read(tempTimestamp, x+132, 8);

                    Header newHeader = new Header(bytesToLong(getBytes),tempPrevHash,tempDataHash,tempNonce,tempTarget,tempTimestamp);

                    byte[] newbyte = new byte[64];
                    is.read(newbyte, x+140, 4);//todo: Insæt det der data længde

                    Data newData = newbyte.byteToData; //Todo: Temporary.

                    Block newBlock = new Block(newData, newHeader);
                    is.close();
                    return newBlock;
                    //Todo: compact this this shit.
                } else {
                    //ToDo: Find ud af hvor mange byte data som standard er.
                }
            }
                is.close();
            System.out.println("Block not found. Returning blank block.");
            return new Block(null,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
*/}


