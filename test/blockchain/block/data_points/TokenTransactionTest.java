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

public class TokenTransactionTest {

    private static final String userOneID = "1";
    private static final String userTwoID = "2";
    private static final String filePath = "CoinTransaction.dat";
    private static int blockCount = 5;

    @BeforeAll
    private static void setup(){
        LedgerWriterReaderTest.saveRandomBlocks(Paths.get(filePath), blockCount, userOneID);
    }

    @Test
    void getterTest(){
        double reward = 0.35;
        TokenTransaction tokenTransaction = new TokenTransaction(userOneID, userTwoID, reward);
        assertEquals(userOneID, tokenTransaction.getGiverID());
        assertEquals(userTwoID, tokenTransaction.getRecipientID());
        assertEquals(reward, tokenTransaction.getTokens());
    }

    @Test
    void containsIdTest(){
        double transferAmount = 0.35;
        TokenTransaction tokenTransaction = new TokenTransaction(userOneID, userTwoID, transferAmount);

        assertTrue(tokenTransaction.containsIdentifier(userOneID));
        assertTrue(tokenTransaction.containsIdentifier(userTwoID));
        assertFalse(tokenTransaction.containsIdentifier("12"));
    }

    @Test
    void getBalanceChangeTest(){
        double transferAmount = 0.35;
        TokenTransaction tokenTransaction = new TokenTransaction(userOneID, userTwoID, transferAmount);

        assertEquals(-transferAmount, tokenTransaction.getBalanceChange(userOneID));
        assertEquals(transferAmount, tokenTransaction.getBalanceChange(userTwoID));
        assertEquals(0, tokenTransaction.getBalanceChange("Unrelated user"));
    }



}
