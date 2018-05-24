package blockchain.block;

import blockchain.ledger_file.ConverterTest;
import blockchain.ledger_file.LedgerReader;
import blockchain.ledger_file.LedgerWriterReaderTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

class BlockTest {

    private static Block block1;
    private static Block block2;

    private static String minerID = "1";

    @BeforeAll
    public static void setup(){
        block1 = ConverterTest.generateBlock(new LedgerReader(Paths.get(LedgerWriterReaderTest.emptyFilePath)), minerID);
        block2 = block1;
    }

    @Test
    public void sameBlockTest(){
        assertTrue(block1.toString().equals(block2.toString()));
    }

    @Test
    public void sameHeaderTest(){
        assertTrue(block1.toString().contains(block1.getHeader().toString()));
    }

}