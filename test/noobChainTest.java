import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class noobChainTest {

    @Test void noobChainTest01(){
        ArrayList<Block> blockchain = new ArrayList<>();
        blockchain.add(new Block("hej", "0"));
        blockchain.add(new Block("hejsa", blockchain.get(blockchain.size()-1).hash));

        assertEquals(NoobChain.isChainValid(), true);
    }
}
