package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class LedgerWriterTest {
    Block block;
    Header header;
    Data data;

    @BeforeEach
    public void ledgerWriterTest00(){
        header = new Header(3, new byte[]{0}, new byte[]{1}, new byte[]{2}, new byte[]{3}, "mandag");
        data = new Data(new ArrayList<>());
        block = new Block(header, data);
    }

    @Test
    public void ledgerWriterTest01(){
        Path path = Paths.get("test.txt");
        new LedgerWriter(path).writeBlock(block);
    }

    @Test
    public void ledgerWriterTest02(){

        Path path = Paths.get("test1.txt");
        new LedgerWriter(path).writeBlock(block);

    }

    @Test
    public void ledgerWriterTest03(){
        Path path = Paths.get("test2.txt");
        new LedgerWriter(path).writeBlock(block);
    }
}