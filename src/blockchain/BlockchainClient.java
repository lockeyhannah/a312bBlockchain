package blockchain;


import blockchain.block.Block;
import blockchain.block.BlockGenerator;
import blockchain.block.Data;
import blockchain.block.Hasher;
import blockchain.block.data_points.SmartContract;
import blockchain.block.data_points.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;


public class BlockchainClient {



    public static void main(String[] args) {
        Block testBlock, testBlock2;

        Data data = new Data();
        data.addData(new SmartContract());
        data.addData(new Transaction());

        // TODO: 21-04-2018 : Add difficulty calculation
        BigInteger tempDifficulty = new BigInteger("2").pow(245);
        ArrayList<Block> blocks = new ArrayList<>();

        blocks.add(BlockGenerator.generateBlock(data, tempDifficulty.toByteArray(), "".getBytes()));
        System.out.println();
        blocks.get(0).printBlock();

        for(int i = 1; i < 20; i++){
            tempDifficulty = new BigInteger("2").pow(245 - i);
            blocks.add(BlockGenerator.generateBlock(data, tempDifficulty.toByteArray(), Hasher.applySHA(blocks.get(i - 1).getHeader().getBytes())));
            System.out.println();
            blocks.get(i).printBlock();
        }

    }

}
