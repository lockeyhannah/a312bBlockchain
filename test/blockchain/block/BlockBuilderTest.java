package blockchain.block;

import blockchain.block.data_points.InsufficientFundsException;
import blockchain.block.data_points.StorageContract;
import blockchain.block.data_points.TokenTransaction;
import blockchain.block.mining.BlockBuilder;
import blockchain.ledger_file.ChainAnalyzer;
import blockchain.ledger_file.LedgerReader;
import blockchain.ledger_file.LedgerWriterReaderTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class BlockBuilderTest {

    private static final String minerID = "User1";
    private static final String filePath = "CoinTransaction.dat";
    private static LedgerReader ledgerReader;
    private static final int blockCount = 5;


    @BeforeAll
    static void setup() {
        // Generate and save 5 random blocks mined by userOne
        ledgerReader = new LedgerReader(Paths.get(filePath));
        LedgerWriterReaderTest.saveRandomBlocks(Paths.get(filePath), blockCount, minerID);
    }


    @Test // Tests that an empty block can be built and mined correctly
    public void emptyBlockBuildTest() {
        LedgerReader reader = new LedgerReader(LedgerWriterReaderTest.getEmptyPath());
        Block block = new BlockBuilder(reader).build("1");
        assertTrue(ChainAnalyzer.isBlockValid(block, reader));
    }


    @Test
        // Tests that a transaction cannot be added when the user does not have sufficient funds
    void transactionInsufficientFundsTest() {
        BlockBuilder blockBuilder = new BlockBuilder(ledgerReader);

        // Create transaction from user who doesn't have sufficient funds
        TokenTransaction transaction = new TokenTransaction(minerID, minerID, BlockBuilder.miningReward * blockCount + 1.0);
        Executable insufficientFundsTransaction = () -> blockBuilder.addData(transaction);

        // Attempt to add the invalid transaction
        assertThrows(InsufficientFundsException.class, insufficientFundsTransaction);
    }

    @Test
        // Tests that a transaction can be added when the user does have sufficient funds
    void transactionSufficientFundsTest() {
        BlockBuilder blockBuilder = new BlockBuilder(ledgerReader);

        // Create transaction from user who has sufficient funds
        TokenTransaction transaction = new TokenTransaction(minerID, "2", BlockBuilder.miningReward * blockCount);

        try {
            // Add transaction to builder
            blockBuilder.addData(transaction);
        } catch (InsufficientFundsException e) {
            // Fail if an InsufficientFundsException is thrown
            e.printStackTrace();
            fail("User with sufficient funds could not complete transaction");
        }
    }


    @Test
        // Tests that a contract cannot be added when the user does not have sufficient funds
    void contractInsufficientFundsTest() {
        BlockBuilder blockBuilder = new BlockBuilder(ledgerReader);

        // Create transaction from user who doesn't have sufficient funds
        StorageContract contract = new StorageContract("file.format", minerID, minerID,
                0, BlockBuilder.miningReward * blockCount + 1.0);

        // Attempt to add the invalid transaction
        Executable insufficientFundsTransaction = () -> blockBuilder.addData(contract);
        assertThrows(InsufficientFundsException.class, insufficientFundsTransaction);
    }

    @Test
        // Tests that a contract can be added when the user does have sufficient funds
    void contractSufficientFundsTest() {
        BlockBuilder blockBuilder = new BlockBuilder(ledgerReader);

        // Create transaction with reward corresponding to user one's funds
        StorageContract contract = new StorageContract("file.format", minerID, minerID,
                0, BlockBuilder.miningReward * blockCount);

        try {
            // Add contract to builder
            blockBuilder.addData(contract);
        } catch (InsufficientFundsException e) {
            // Fail if an InsufficientFundsException is thrown
            e.printStackTrace();
            fail("User with sufficient funds could not complete transaction");
        }
    }
}
