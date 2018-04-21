package blockchain;


import blockchain.block.Block;
import blockchain.block.BlockGenerator;
import blockchain.block.Data;
import blockchain.block.data_points.SmartContract;
import blockchain.block.data_points.Transaction;

import java.math.BigInteger;


public class BlockchainClient {



    public static void main(String[] args) {
        Block testBlock;

        Data data = new Data();
        data.addData(new SmartContract());
        data.addData(new Transaction());

        // TODO: 21-04-2018 : Add difficulty calculation
        BigInteger tempDifficulty = new BigInteger("2").pow(245);


        testBlock = BlockGenerator.generateBlock(data, tempDifficulty.toByteArray(), "0".getBytes());
        testBlock.printBlock();
    }



}
