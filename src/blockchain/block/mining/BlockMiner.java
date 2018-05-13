package blockchain.block.mining;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.utility.ByteUtils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlockMiner {

    public static String generateTimeStamp(){
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    // Mines for valid nonce value and returns a valid block
    public static Block mineBlock(Header header, Data data){
        BigInteger nonce = new BigInteger(header.getNonce());
        BigInteger target = new BigInteger(header.getDifficultyTarget());

        //Calculate the hash value with the default nonce and convert to an integer value
        byte[] hash = Hasher.applySHA(header.getBytes());
        BigInteger hashInteger = new BigInteger(1, hash);

        BigInteger nonceMax = BigInteger.TWO.pow(240);

        // Recalculate hash with new nonce values until the hash is below the target value
        while(hashInteger.compareTo(target) > 0){
            // Update nonce value
            nonce = nonce.add(BigInteger.ONE);
            header.setNonce(ByteUtils.trimLeadingZeroes(nonce.toByteArray()));

            // Hash header with new nonce value
            hash = Hasher.applySHA(header.getBytes());
            hashInteger = new BigInteger(1, hash);

            if(nonce.compareTo(nonceMax) > 0){
                header.setTimeStamp(generateTimeStamp());
                nonce = BigInteger.ONE;
            }
        }

        return new Block(header, data);
    }



}
