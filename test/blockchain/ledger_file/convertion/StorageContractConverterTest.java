package blockchain.ledger_file.convertion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageContractConverterTest {

    StorageContractConverter converter;

    @BeforeEach
    public void setup(){
        converter = new StorageContractConverter((short) 3);
    }

    @Test
    public void correctDataTest(){
        assertEquals(5, converter.getOBJECT_TYPE_UID());
    }

}