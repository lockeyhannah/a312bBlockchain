package blockchain.block.data_points;


import blockchain.block.mining.BlockBuilder;
import blockchain.ledger_file.ChainAnalyzer;
import blockchain.ledger_file.LedgerReader;
import blockchain.ledger_file.LedgerWriter;
import blockchain.ledger_file.LedgerWriterReaderTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

public class CoinTransactionTest {

    private static final String userOneID = "1";
    private static final String filePath = "CoinTransaction.dat";
    private static int blockCount = 5;

    @BeforeAll
    private static void setup(){
        LedgerWriterReaderTest.saveRandomBlocks(Paths.get(filePath), blockCount, userOneID);
    }

    @Test // Tests that a transaction cannot be added when the user does not have sufficient
    void insufficientFundsTest(){
        BlockBuilder blockBuilder = new BlockBuilder(new LedgerReader(Paths.get(filePath)));

        // Create transaction from user who doesn't have sufficient funds
        CoinTransaction transaction = new CoinTransaction(userOneID, userOneID, BlockBuilder.miningReward * blockCount + 1.0);

        Executable insufficientFundsTransaction = () -> blockBuilder.addData(transaction);

        // Attempt to add the invalid transaction
        assertThrows(InsufficientFundsException.class, insufficientFundsTransaction);
    }

    @Test // Tests that a transaction cannot be added when the user does not have sufficient
    void sufficientFundsTest(){
        BlockBuilder blockBuilder = new BlockBuilder(new LedgerReader(Paths.get(filePath)));

        // Create transaction from user who has sufficient funds
        CoinTransaction transaction = new CoinTransaction(userOneID, "2", BlockBuilder.miningReward * blockCount);

        try {
            // Add transaction to builder
            blockBuilder.addData(transaction);
        } catch (InsufficientFundsException e) {
            // Fail if an exception is thrown
            e.printStackTrace();
            fail("User with sufficient funds could not complete transaction");
        }
    }


    @Test // Tests that a coin transfer results in the correct account balance changes
    void transferTest(){
        BlockBuilder blockBuilder = new BlockBuilder(new LedgerReader(Paths.get(filePath)));
        String userTwoID = "2";

        // Transfer all the money available from the user one
        double transferAmount = BlockBuilder.miningReward * blockCount;
        CoinTransaction transaction = new CoinTransaction(userOneID, userTwoID, transferAmount);

        // Add transaction to block
        try {
            blockBuilder.addData(transaction);
        } catch (InsufficientFundsException e) {
            // Fail if an exception is thrown
            e.printStackTrace();
            fail("User with sufficient funds could not complete transaction");
        }

        // Mine the block and write it to the ledger
        new LedgerWriter(Paths.get(filePath)).writeBlock(blockBuilder.build("10"));

        LedgerReader ledgerReader = new LedgerReader(Paths.get(filePath));

        // Check that the balance is 0 for the paying user
        assertEquals(0.0, ChainAnalyzer.getUserBalance(ledgerReader, userOneID));

        // Check that the balance is correct for the receiving user
        assertEquals(transferAmount, ChainAnalyzer.getUserBalance(ledgerReader, userTwoID));

    }

}
