package blockchain.ledger_file.convertion;

import blockchain.block.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HeaderConverterTest {

    Header header, header2;
    byte[] bytes1, bytes2;
    HeaderConverter converter;

    @BeforeEach
    public void headerConverterTest00(){
        converter = new HeaderConverter((short)3);
        header = new Header(3, new byte[32], new byte[]{1},
                new byte[]{2}, new byte[]{3}, "mandag");
        header2 = header;
        bytes1 = converter.bytesFromInstance(header);
        bytes2 = converter.bytesFromInstance(header2);
    }

    @Test
    public void headerConverterTest01(){
        assertTrue(Arrays.compare(bytes1, bytes2) == 0);
    }

    @Test
    public void headerConverterTest02(){
        header = converter.instanceFromBytes(bytes1);

        assertTrue(header.equals(header2));
    }
}