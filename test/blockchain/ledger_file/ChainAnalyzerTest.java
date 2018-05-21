package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.data_points.CoinTransaction;
import blockchain.block.mining.BlockBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ChainAnalyzerTest {

    static String filePath = "chain_validation.dat";
    static String minerID = "1";
    static int blockCount = 10;

    @BeforeAll
    private static void setup() {
        LedgerWriterReaderTest.saveRandomBlocks(Paths.get(filePath), blockCount, minerID);
    }

    @Test // Tests that a valid block passes the validation
    public void blockValidationTest() throws FileNotFoundException {
        Path path = Paths.get(filePath);
        LedgerReader reader = new LedgerReader(path);
        Block block = reader.readBlock(4);

        assertTrue(ChainAnalyzer.isBlockValid(block, reader));
    }

    @Test // Tests that a valid chain passes the validation
    public void chainValidationTest() {
        Path path = Paths.get(filePath);
        LedgerReader reader = new LedgerReader(path);
        assertTrue(ChainAnalyzer.isChainValid(reader));
    }

    @Test // Test that a manipulated does not pass the validation method
    public void invalidBlockTest() {
        LedgerReader ledgerReader = new LedgerReader(Paths.get(filePath));
        Block block = ConverterTest.generateBlock(ledgerReader, minerID);

        // Assert that block is valid from the beginning
        assertTrue(ChainAnalyzer.isBlockValid(block, ledgerReader));

        // Add an extra transaction
        block.getData().addData(new CoinTransaction("0", "1", 50.0));

        // Assert that the block is now invalid
        assertFalse(ChainAnalyzer.isBlockValid(block, ledgerReader));
    }


    @Test // Tests that the miner receives the correct rewards
    public void miningRewardTest(){
        LedgerReader ledgerReader = new LedgerReader(Paths.get(filePath));
        assertEquals(ChainAnalyzer.getUserBalance(ledgerReader, minerID), blockCount * BlockBuilder.miningReward);
    }

}