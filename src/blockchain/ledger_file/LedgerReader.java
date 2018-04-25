package blockchain.ledger_file;

import blockchain.block.Block;

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
        byte[] getBytes;
        byte[] target = longToBytes(blockID);
        int x = 0;
        try {
            File file = new File(ledgerFilePath.toString());
            getBytes = new byte[4];
            InputStream is = new FileInputStream(file);
            while(is != null) {//Todo: 25-04-2018: Fix this so it makes sense
                is.read(getBytes, x, 4);
                if (Arrays.compare(getBytes,target) == 0) {
                    //Found the right stuff. Start writing the block then return it. Remember to close the file.
                } else {
                    //increase offset or skip. Also do something to the loop.
                }
            }
                is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
}


