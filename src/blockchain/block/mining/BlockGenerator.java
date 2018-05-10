package blockchain.block.mining;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlockGenerator {

    // Mines and returns a new block based on the given data
    public static Block generateBlock(Data data, byte[] difficulty, byte[] prevHeaderHash){
        String timeStamp = generateTimeStamp();

        byte[] dataHash = Hasher.applySHA(data.getDataBytes());
        byte[] nonce = {0};
        int blockNo = 0;

        Header header = new Header(blockNo, prevHeaderHash, dataHash, nonce, difficulty, timeStamp);
        mineBlock(header);

        return new Block(header, data);
    }

    public static String generateTimeStamp(){
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    // Mines a block and returns the resulting nonce value
    private static void mineBlock(Header header){ // TODO: 29-04-2018 Reimplement
        BigInteger nonce = new BigInteger(header.getNonce());
        BigInteger target = new BigInteger(header.getDifficultyTarget());

        //Calculate the hash value with the default nonce and convert to an integer value
        byte[] hash = Hasher.applySHA(header.getBytes());
        BigInteger hashInteger = new BigInteger(1, hash);

        // Recalculate hash with new nonce values until the hash is below the target value
        while(hashInteger.compareTo(target) > 0){
            // Update nonce value
            nonce = nonce.add(BigInteger.ONE);
            header.setNonce(nonce.toByteArray());

            // Hash header with new nonce value
            hash = Hasher.applySHA(header.getBytes());
            hashInteger = new BigInteger(1, hash);

        }

    }



}
