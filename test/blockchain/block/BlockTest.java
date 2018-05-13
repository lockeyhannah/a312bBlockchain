package blockchain.block;

import blockchain.ledger_file.ConverterTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

class BlockTest {

    Block block1, block2;

    @BeforeEach
    public void blockTest00(){
        block1 = ConverterTest.generateBlock(null);
        block2 = block1;
    }

    @Test
    public void blockTest01(){
        assertTrue(block1.equals(block2));
    }

    @Test
    public void blockTest02(){
        assertTrue(block1.toString().contains(block1.getHeader().getString()));
    }

}