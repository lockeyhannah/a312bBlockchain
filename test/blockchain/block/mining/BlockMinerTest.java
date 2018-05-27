package blockchain.block.mining;

import blockchain.block.Block;
import blockchain.block.data_points.TokenTransaction;
import blockchain.block.data_points.InsufficientFundsException;
import blockchain.ledger_file.ConverterTest;
import blockchain.ledger_file.LedgerReader;
import blockchain.ledger_file.LedgerWriterReaderTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class BlockMinerTest {

    Block block;

    @BeforeEach
    public void setup() {
        block = ConverterTest.generateBlock();
    }

    @Test // Tests that blocks are mined with a correct hash value
    public void validHashTest() throws InsufficientFundsException{
        BlockBuilder builder = new BlockBuilder(new LedgerReader(Paths.get(LedgerWriterReaderTest.emptyFilePath)));

        // Mine 5 blocks and test hash validity for each one
        for (int i = 0; i < 5; i++) {
            // Add a different transaction for each block
            builder.addData(new TokenTransaction("" + i, "1", 0));

            // Mine block
            block = builder.build("1");

            // Check that the hash is below the target value
            BigInteger target = new BigInteger(block.getHeader().getDifficultyTarget());
            BigInteger hash = new BigInteger(Hasher.applySHA(block.getHeader().getBytes()));
            assertTrue(target.compareTo(hash) > 0);
        }
    }
}