package blockchain.block;

import blockchain.ledger_file.ConverterTest;
import blockchain.ledger_file.LedgerReader;
import blockchain.ledger_file.LedgerWriterReaderTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

class BlockTest {

    private Block block1;
    private Block block2;

    @BeforeEach
    public void blockTest00(){
        block1 = ConverterTest.generateBlock(new LedgerReader(Paths.get(LedgerWriterReaderTest.emptyFilePath)));
        block2 = block1;
    }

    @Test
    public void blockTest01(){
        assertTrue(block1.toString().equals(block2.toString()));
    }

    @Test
    public void blockTest02(){
        assertTrue(block1.toString().contains(block1.getHeader().toString()));
    }

}