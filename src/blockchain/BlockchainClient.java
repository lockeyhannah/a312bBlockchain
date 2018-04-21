package blockchain;


import blockchain.block.Block;
import blockchain.block.Data;


public class BlockchainClient {

    Block block;

    public static void main(String[] args) {
        Data data = new Data();

        data.addData(new SmartContract());

    }


}
