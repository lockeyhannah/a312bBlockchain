package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.data_points.InsufficientFundsException;
import blockchain.block.data_points.TokenTransaction;
import blockchain.block.mining.BlockBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ChainAnalyzerTest {

    private static ChainAnalyzer chainAnalyzer;
    private static String filePath = "chain_validation.dat";
    private static String minerID = "1";
    static int blockCount = 5;

    @BeforeAll
    private static void setup() {
        Path path = Paths.get(filePath);
        LedgerWriterReaderTest.saveRandomBlocks(path, blockCount, minerID);
        chainAnalyzer = new ChainAnalyzer(new LedgerReader(path));
    }

    @Test // Tests that a valid block passes the validation
    public void blockValidationTest() {
        Path path = Paths.get(filePath);
        LedgerReader reader = new LedgerReader(path);
        Block block = reader.readBlock(4);

        assertTrue(ChainAnalyzer.isBlockValid(block, reader));
    }

    @Test // Tests that a valid chain passes the validation
    public void chainValidationTest() {
        Path path = Paths.get(filePath);
        LedgerReader reader = new LedgerReader(path);
        assertTrue(chainAnalyzer.isChainValid());
    }

    @Test // Test that a manipulated does not pass the validation method
    public void invalidBlockTest() {
        LedgerReader ledgerReader = new LedgerReader(Paths.get(filePath));
        Block block = ConverterTest.generateBlock(ledgerReader, minerID);

        // Assert that block is valid from the beginning
        assertTrue(ChainAnalyzer.isBlockValid(block, ledgerReader));

        // Add an extra transaction
        block.getData().addData(new TokenTransaction("0", "1", 50.0));

        // Assert that the block is now invalid
        assertFalse(ChainAnalyzer.isBlockValid(block, ledgerReader));
    }


    @Test // Tests that the miner receives the correct rewards
    public void miningRewardTest(){
        assertEquals(chainAnalyzer.getUserBalance(minerID), blockCount * BlockBuilder.miningReward);
    }


    @Test // Tests that a coin transfer results in the correct account balance changes
    void transferBalanceTest(){
        LedgerWriterReaderTest.saveRandomBlocks(Paths.get(filePath), blockCount, minerID);
        BlockBuilder blockBuilder = new BlockBuilder(new LedgerReader(Paths.get(filePath)));
        String userTwoID = "2";

        // Transfer all the money available from the user one
        double transferAmount = BlockBuilder.miningReward * blockCount;
        TokenTransaction transaction = new TokenTransaction(minerID, userTwoID, transferAmount);

        try {
            // Add transaction to block
            blockBuilder.addData(transaction);
        } catch (InsufficientFundsException e) {
            // Fail if an exception is thrown
            fail("User with sufficient funds could not complete transaction");
        }

        // Mine the block and write it to the ledger
        new LedgerWriter(Paths.get(filePath)).writeBlock(blockBuilder.build("Unrelated miner"));

        ChainAnalyzer chainAnalyzer = new ChainAnalyzer(new LedgerReader(Paths.get(filePath)));

        // Check that the balance is 0 for the paying user
        assertEquals(0.0, chainAnalyzer.getUserBalance(minerID));
        // Check that the balance is correct for the receiving user
        assertEquals(transferAmount, chainAnalyzer.getUserBalance(userTwoID));
    }


    @Test // Tests that a contract results in the correct account balance changes
    void contractBalanceTest(){
        LedgerWriterReaderTest.saveRandomBlocks(Paths.get(filePath), blockCount, minerID);
        BlockBuilder blockBuilder = new BlockBuilder(new LedgerReader(Paths.get(filePath)));
        String userTwoID = "2";

        // Transfer all the money available from the user one
        double transferAmount = BlockBuilder.miningReward * blockCount;
        TokenTransaction transaction = new TokenTransaction(minerID, userTwoID, transferAmount);

        try {
            // Add transaction to block
            blockBuilder.addData(transaction);
        } catch (InsufficientFundsException e) {
            // Fail if an exception is thrown
            fail("User with sufficient funds could not complete transaction");
        }

        // Mine the block and write it to the ledger
        new LedgerWriter(Paths.get(filePath)).writeBlock(blockBuilder.build("Unrelated miner"));

        ChainAnalyzer chainAnalyzer = new ChainAnalyzer(new LedgerReader(Paths.get(filePath)));

        // Check that the balance is 0 for the paying user
        assertEquals(0.0, chainAnalyzer.getUserBalance(minerID));
        // Check that the balance is correct for the receiving user
        assertEquals(transferAmount, chainAnalyzer.getUserBalance(userTwoID));
    }

}