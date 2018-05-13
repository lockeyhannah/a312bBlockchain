package blockchain.ledger_file;

import blockchain.block.Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class LedgerReaderTest {

    Path path1, path2;
    Block block;

    @BeforeEach
    public void ledgerReaderTest00(){
        path1 = Paths.get("reader_test.txt");
        path2 = Paths.get("chain_test.txt");
    }

    // TODO: 11-05-2018 reeeeeee

    @Test
    public void ledgerReaderTest01(){
        try {
            new LedgerReader(path1);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    @Test
    public void ledgerReaderTest02(){

        try {
            block = new LedgerReader(path2).getFirstBlock();
        } catch (FileNotFoundException e) {
            System.out.println("couldnt get block");
        }

        System.out.println(block.toString());
    }
}