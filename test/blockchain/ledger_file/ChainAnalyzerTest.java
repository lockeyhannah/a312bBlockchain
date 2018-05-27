package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.InsufficientFundsException;
import blockchain.block.data_points.StorageContract;
import blockchain.block.data_points.TokenTransaction;
import blockchain.block.mining.BlockBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class ChainAnalyzerTest {

    private static ChainAnalyzer chainAnalyzer;
    private static final String filePath = "chain_validation.dat";
    private static final String minerID = "1";
    private static final int blockCount = 5;

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
    public void miningRewardTest() {
        assertEquals(chainAnalyzer.getUserBalance(minerID), blockCount * BlockBuilder.miningReward, 0);
    }


    @Test
        // Tests that a coin transfer results in the correct account balance changes
    void transferBalanceTest() {
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
        assertEquals(0.0, chainAnalyzer.getUserBalance(minerID), 0);
        // Check that the balance is correct for the receiving user
        assertEquals(transferAmount, chainAnalyzer.getUserBalance(userTwoID), 0);
    }


    @Test
        // Tests that a contract results in the correct account balance changes
    void contractBalanceTest() {
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
        assertEquals(0.0, chainAnalyzer.getUserBalance(minerID), 0);
        // Check that the balance is correct for the receiving user
        assertEquals(transferAmount, chainAnalyzer.getUserBalance(userTwoID), 0);
    }

    @Test
        // Tests that the chain analyzer can correctly retrieve all the datapoints
    void getContractsTest() throws InsufficientFundsException {
        Path path = LedgerWriterReaderTest.getEmptyPath();

        LedgerReader ledgerReader = new LedgerReader(path);
        BlockBuilder blockBuilder = new BlockBuilder(ledgerReader);

        // Create a list of data points with a common identifier between them
        ArrayList<DataPoint> originalData = new ArrayList<>();
        String sharedId = "shared id";
        originalData.add(new TokenTransaction(sharedId, "other user", 0));
        originalData.add(new TokenTransaction("otherer user", sharedId, 0));
        originalData.add(new StorageContract(sharedId, "fileOwner", "user9", 0, 0));

        // Add these DataPoints to the block along with a data point not containing the shared id
        for (DataPoint dp : originalData)
            blockBuilder.addData(dp);
        blockBuilder.addData(new TokenTransaction("unrelated", "unrelated", 0));
        // build it and add it to the chain
        new LedgerWriter(path).writeBlock(blockBuilder.build("unrelated id"));

        // Retrieve the list of data points with same identifier from the chain
        ArrayList<DataPoint> retrievedData = new ChainAnalyzer(ledgerReader).getDataPoints(sharedId);

        // Check that the retrieved data points match the originals
        assertEquals(originalData.size(), retrievedData.size());

        for (int i = 0; i < originalData.size(); i++)
            assertEquals(originalData.get(i).getFormattedDataString(), retrievedData.get(i).getFormattedDataString());

    }


}