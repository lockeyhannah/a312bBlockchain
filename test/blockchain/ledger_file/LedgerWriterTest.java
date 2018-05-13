package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class LedgerWriterTest {
    Block block;
    Header header;
    Data data;

    ArrayList<Block> blocks = new ArrayList<>();
    int blockCount = 10;

    @BeforeEach
    public void setup(){
        // Generate first block
        blocks.add(ConverterTest.generateBlock(null));

        for(int i = 1; i < blockCount; i++){
            blocks.add(ConverterTest.generateBlock(blocks.get(i - 1).getHeader()));
        }

        header = new Header(3, new byte[]{0}, new byte[]{1}, new byte[]{2}, new byte[]{3}, "mandag");
        data = new Data(new ArrayList<>());
        block = ConverterTest.generateBlock(null);

        Path path = Paths.get("chain_test.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path)){
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateRandomBlockChain(){

    }

    // todo add functionality to delete previous test files

    @Test
    public void blockchainSaveTest(){
        String testFileName = "chain_test.txt";
        LedgerWriter ledgerWriter = new LedgerWriter(Paths.get(testFileName));

        //
        for(int i = 0; i < blockCount; i++){
            ledgerWriter.writeBlock(blocks.get(i));
        }

        try {
            LedgerReader ledgerReader = new LedgerReader(Paths.get(testFileName));

            for(int i = 0; i < blockCount; i++){
                System.out.println(i);
                Assert.assertEquals(blocks.get(i).toString(), ledgerReader.readBlock(i).toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }


    /*
    @Test
    public void ledgerWriterTest01(){
        Path path = Paths.get("test.txt");
        new LedgerWriter(path).writeBlock(block);
    }

    @Test
    public void ledgerWriterTest02(){
        Path path = Paths.get("test1.txt");
        new LedgerWriter(path).writeBlock(block);
    }

    @Test
    public void ledgerWriterTest03(){
        Path path = Paths.get("test2.txt");
        new LedgerWriter(path).writeBlock(block);
    }*/

}