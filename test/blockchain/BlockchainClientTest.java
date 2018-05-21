package blockchain;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class BlockchainClientTest {

    private static final String PATH = "client_chain.dat";
    private static final String CLIENT_ID = "client_chain.dat";

    @Test
    void systemTest(){
        BlockchainClient blockchainClient = new BlockchainClient(Paths.get(PATH), CLIENT_ID);
        blockchainClient.mineCurrentBlock();
    }


}
