import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class blockTest {

    Block block1 = new Block("0","0");
    Block block2 = new Block("0","0");

    @BeforeEach
    public void beforeTests(){
        block1 = new Block("hej med dig", "0");
        block2 = new Block("hoohua", block1.hash);
    }


    @Test
    public void blockTest01(){
        assertEquals(block2.hash, block2.calculateHash());
    }

    @Test
    public void blockTest02(){
        assertEquals(block2.previousHash, block1.hash);
    }

    @Test
    public void blockTest03(){
        StringUtil.applySha256("");
        //Ved ikke hvordan man tester en exception
    }
}
