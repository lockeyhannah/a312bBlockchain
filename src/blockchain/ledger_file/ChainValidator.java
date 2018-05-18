package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.mining.BlockMiner;
import blockchain.block.mining.Hasher;
import blockchain.utility.ByteUtils;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Arrays;

public class ChainValidator {
    
    public boolean isChainValid(Path path) throws FileNotFoundException {
        for (int i = 0; i < new LedgerReader(path).getBlockCount(); i++){
            if(!(isBlockValid(new LedgerReader(path).readBlock(i)))){
                return false;
            }
        }
        return true;
    }
    
    public boolean isBlockValid(Block block){
        
        Block copy;
        
        BigInteger target = new BigInteger(block.getHeader().getDifficultyTarget());
        BigInteger hash = new BigInteger(Hasher.hashToHexString(Hasher.applySHA(block.getHeader().getBytes())));

        if (hash.compareTo(target) > 0){
            return false;
        }

        copy = BlockMiner.mineBlock(block.getHeader(), block.getData());
        
        if (!(Arrays.compare(block.getHeader().getBytes(), copy.getHeader().getBytes()) == 0)){
            return false;
        }

        // TODO: 18-05-2018 add checker for previous blocks hash. 
        
        return true;
    }
}
