package blockchain.block.mining;

import blockchain.block.Block;
import blockchain.block.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class BlockMinerTest {

    Data data;
    Block block;
/*
    @BeforeEach
    public void blockGeneratorTest00() {
        data = new Data(new ArrayList<>());
        BigInteger difficulty = BigInteger.TWO.pow(235);
        block = BlockMiner.generateBlock(data, null);
    }

    @Test
    public void blockGeneratorTest01() {
        String hashString = Hasher.hashToHexString(block.getHash());
        System.out.println(hashString);
        //Check that the first 5 chars are zeroes
        for (int i = 0; i < 4; i++)
            assertEquals('0', hashString.charAt(i));
    }

    @Test
    public void blockGeneratorTest02() {
        assertEquals(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()),
                BlockMiner.generateTimeStamp());
    }*/ // todo reimplement
}