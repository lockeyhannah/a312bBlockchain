package blockchain.ledger_file;

import blockchain.block.Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ChainAnalyzerTest {

    String filePath = "chain_validation.dat";

    @BeforeEach
    private void setup() {
        LedgerWriterReaderTest.saveRandomBlocks(Paths.get(filePath), 10);
    }


    @Test
    public void blockValidationTest() throws FileNotFoundException {
        Path path = Paths.get(filePath);
        LedgerReader reader = new LedgerReader(path);
        Block block = reader.readBlock(4);

        assertTrue(ChainAnalyzer.isBlockValid(block, reader));
    }

    @Test
    public void chainValidationTest() throws FileNotFoundException {
        Path path = Paths.get(filePath);
        LedgerReader reader = new LedgerReader(path);

        assertTrue(ChainAnalyzer.isChainValid(reader));
    }

}