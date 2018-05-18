package blockchain.block.mining;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.ledger_file.ConverterTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class BlockMinerTest {

    Block block;

    @BeforeEach
    public void blockGeneratorTest00() {
        block = ConverterTest.generateBlock(null);
    }

    @Test
    public void blockMinerTest01() {
        for (int i = 0; i < 5; i++) {
            block = ConverterTest.generateBlock(block.getHeader());
            String hashString = Hasher.hashToHexString(Hasher.applySHA(block.getHeader().getBytes()));

            //Check that the first 5 chars are zeroes
            for (int k = 0; k < 4; k++)
                assertEquals('0', hashString.charAt(k));
        }
    }
/*
    @Test
    public void blockGeneratorTest02() {
        assertEquals(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()),
                BlockMiner.generateTimeStamp());
    }*/
}