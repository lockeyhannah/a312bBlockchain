package blockchain.ledger_file;

import blockchain.block.Block;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ChainValidatorTest {

    @Test
    public void chainValidatorTest01() throws FileNotFoundException {
        Path path = Paths.get("chain_test.txt");
        LedgerReader reader = new LedgerReader(path);
        Block block = reader.readBlock(4);

        assertTrue(ChainValidator.isBlockValid(block, reader));
    }

    @Test
    public void chainValidatorTest02() throws FileNotFoundException {
        Path path = Paths.get("chain_test.txt");
        LedgerReader reader = new LedgerReader(path);

        assertTrue(ChainValidator.isChainValid(reader));
    }

}