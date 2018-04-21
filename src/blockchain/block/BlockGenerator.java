package blockchain.block;

import java.math.BigInteger;

public class BlockGenerator {

    // Mines and returns a new block based on the given data
    public static Block generateBlock(Data data, byte[] difficulty, byte[] prevHeaderHash){
        byte[] dataHash = Hasher.applySHA(data.getByteArray());
        long timeStamp = System.currentTimeMillis();
        byte[] nonce = {0};

        Header header = new Header(prevHeaderHash, dataHash, nonce, difficulty, timeStamp);
        mineBlock(header);

        return new Block(data, header);
    }


    // Mines a block and returns the resulting nonce value
    private static void mineBlock(Header header){
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
