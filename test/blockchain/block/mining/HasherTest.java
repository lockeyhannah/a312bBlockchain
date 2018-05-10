package blockchain.block.mining;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HasherTest {

    byte[] msg, output;

    @BeforeEach
    public void hasherTest00(){
        msg = "message".getBytes();
    }

    @Test
    public void hasherTest01(){
        output = Hasher.applySHA(msg);
        System.out.println(Hasher.hashToHexString(output));
    }

}