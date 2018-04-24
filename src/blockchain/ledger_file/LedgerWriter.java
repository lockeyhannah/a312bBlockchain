package blockchain.ledger_file;

import blockchain.block.Block;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.APPEND;


public class LedgerWriter {

    private Path ledgerFilePath;


    public LedgerWriter(Path ledgerFilePath){
           this.ledgerFilePath = ledgerFilePath;
    }

    // Appends a block to the blockchain
    public void writeBlock(Block block){
        byte[] nonce = block.getHeader().getNonce();
        byte[] timeStamp = block.getHeader().getTimeStamp();
        byte[] dataHash = block.getHeader().getDataHash();
        byte[] prevHash = block.getHeader().getPrevHash();
        byte[] target = block.getHeader().getTarget();
        // TODO: 23-04-2018 : Write block data (byte array) to the file, in a way that can be parsed later
        /* Jeg foreslår at alle datapunkterne skrives med en fast længde (f.eks. kan nonce værdierne altid være 32 bytes lange,
         * også selvom der ikke er brug for så mange bytes for at skrive hele værdien)
         * På den måde kan vi bare kan vi bare læse et bestemt antal bytes frem når vi skal læse filen igen senere
         * Ved ikke om det giver mening. bare giv mig en lammer hvis det er volapyk

         * Nedenfor har jeg et forslag til strukturen af filens blokke, samt bytelængde for hvert punkt
         * Kraftigt inspireret af bitcoin OwO
         */


        /*  ------ Write header -------
         * Magic number         #4 bytes
         * Block length         #4 bytes  (Measured in bytes. This enables the parser to jump directly to the next block)
         * Block number         #8 bytes
         * Timestamp            #8 bytes
         * Hash, nonce & target #32 bytes each (equivalent to 256 bits as in sha256)

         *  ------- Write data --------
         * Amount of data points #4 bytes (This lets the reader know how much to read before a new block is encountered)
         * Data points
         */


        writeToFile(nonce,32);


    }

    // Appends the given data to the ledger file
    private void writeToFile(byte[] bytes){
        writeToFile(bytes, bytes.length);
    }

    // Appends a byte array of target size to the ledger file
    // Adds preceding zeroes to compensate if the given byte array is smaller than the targetsize
    public void writeToFile(byte[] bytes, int targetSize){
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







}
