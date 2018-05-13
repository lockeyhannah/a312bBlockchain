package blockchain.ledger_file.convertion;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    BlockConverter converter;

    @BeforeEach
    public void converterTest00(){
        converter = new BlockConverter((short)2);
    }

    @Test
    public void converterTest01(){
        assertEquals((short)2, converter.getCONVERTER_VERSION_UID());
    }

}