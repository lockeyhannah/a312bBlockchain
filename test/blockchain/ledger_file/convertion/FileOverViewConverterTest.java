package blockchain.ledger_file.convertion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileOverViewConverterTest {

    FileOverViewConverter converter;

    @BeforeEach
    public void fileOverviewConverterTest00(){
        converter = new FileOverViewConverter((short)3);
    }

    @Test
    public void filerOverviewConverterTest01(){
        assertEquals(-1, converter.getByteSize());
    }


}