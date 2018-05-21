package blockchain.ledger_file;

import blockchain.block.Block;
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

public class LedgerWriterReaderTest {

    public static final String emptyFilePath = "empty_file.dat";
    private static final String filePath = "chain_test.txt";
    private ArrayList<Block> originalBlocks;
    private String minerID = "1";

    @BeforeEach
    private void setup() {
        originalBlocks = saveRandomBlocks(Paths.get(filePath), 10, minerID);
    }

    // Saves and returns a randomly generated list of blocks
    public static ArrayList<Block> saveRandomBlocks(Path path, int amount, String minerID) {
        clearFile(path);
        LedgerWriter ledgerWriter = new LedgerWriter(path);
        LedgerReader ledgerReader = new LedgerReader(path);

        ArrayList<Block> blocks = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Block b = ConverterTest.generateBlock(ledgerReader, minerID);
            ledgerWriter.writeBlock(b);
            blocks.add(b);
        }

        return blocks;
    }

    // Empties the given file
    public static void clearFile(Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void blockchainReadTest() {
        LedgerReader ledgerReader = new LedgerReader(Paths.get(filePath));

        for (Block originalBlock : originalBlocks) {
            Block readBlock = ledgerReader.readBlock(originalBlock.getHeader().getBlockId());
            Assert.assertEquals(originalBlock.toString(), readBlock.toString());
        }


    }


}