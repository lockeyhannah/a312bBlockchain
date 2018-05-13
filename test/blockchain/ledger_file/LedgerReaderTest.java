package blockchain.ledger_file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class LedgerReaderTest {



    @BeforeEach
    public void ledgerReaderTest00(){

    }

    // TODO: 11-05-2018 reeeeeee

    @Test
    public void ledgerReaderTest01(){
        Path path = Paths.get("chain_test.txt");
        try {
            new LedgerReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}