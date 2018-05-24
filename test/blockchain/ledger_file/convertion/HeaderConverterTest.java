package blockchain.ledger_file.convertion;

import blockchain.block.Header;
import blockchain.ledger_file.ConverterTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HeaderConverterTest {

    Header header, header2;
    byte[] bytes1, bytes2;
    HeaderConverter converter;

    @BeforeEach
    public void setup(){
        converter = new HeaderConverter((short)3);
        header = ConverterTest.generateBlock().getHeader();
        header2 = header;
        bytes1 = converter.bytesFromInstance(header);
        bytes2 = converter.bytesFromInstance(header2);
    }

    @Test
    public void sameBytesTest(){
        assertEquals(0, Arrays.compare(bytes1, bytes2));
    }

    @Test
    public void sameHeaderTest(){
        header = converter.instanceFromBytes(bytes1);

        assertEquals(header, header2);
    }

    @Test
    public void correctDataTest(){
        assertEquals(2, converter.getOBJECT_TYPE_UID());
    }
}