package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.mining.BlockMiner;
import blockchain.block.mining.Hasher;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Arrays;

public class ChainValidator {
    
    public static boolean isChainValid(LedgerReader reader) throws FileNotFoundException {
        long totalBlocks = reader.getBlockCount();

        for (int i = 0; i < totalBlocks; i++){
            if(!(isBlockValid(reader.readBlock(i), reader))){
                return false;
            }
        }
        return true;
    }
    
    public static boolean isBlockValid(Block block, LedgerReader reader) throws FileNotFoundException {

        BigInteger target = new BigInteger(block.getHeader().getDifficultyTarget());
        BigInteger hash = new BigInteger(Hasher.applySHA(block.getHeader().getBytes()));

        if (hash.compareTo(target) > 0){
            return false;
        }
        
        if (!(Arrays.compare(Hasher.applySHA(block.getData().getDataBytes()), block.getHeader().getDataHash()) == 0)){
            return false;
        }

        if(block.getHeader().getBlockId() == 0){
            return true;
        }

        Block previousBlock = reader.readBlock(block.getHeader().getBlockId() - 1);

        System.out.println(block.getHeader().getBlockId());
        System.out.println(previousBlock.getHeader().getBlockId());

        if(!(Arrays.compare((Hasher.applySHA(previousBlock.getHeader().getBytes())), block.getHeader().getPrevHash()) == 0)){
            return false;
        }
        
        return true;
    }
}
