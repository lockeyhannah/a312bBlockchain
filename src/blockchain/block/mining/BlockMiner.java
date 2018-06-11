package blockchain.block.mining;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.utility.ByteUtils;

import java.math.BigInteger;


public class BlockMiner {

    // Mines for a valid nonce value and returns a valid block
    public static Block mineBlock(Header header, Data data) {
        BigInteger nonce = new BigInteger(header.getNonce());
        BigInteger target = new BigInteger(header.getDifficultyTarget());

        //Calculate the hash value with the default nonce and convert to an integer value for comparing
        byte[] hash = Hasher.applySHA(header.getBytes());
        BigInteger hashInteger = new BigInteger(1, hash);

        // Set a max value for the nonce value
        BigInteger nonceMax = BigInteger.TWO.pow(255);

        // Recalculate hash with new nonce values until the hash is below the target value
        while (hashInteger.compareTo(target) > 0) {
            // Update nonce value
            nonce = nonce.add(BigInteger.ONE);
            header.setNonce(ByteUtils.trimLeadingZeroes(nonce.toByteArray()));

            // Hash header with new nonce value
            hash = Hasher.applySHA(header.getBytes());
            hashInteger = new BigInteger(1, hash);

            // Print progress every 100000 tries
            if(nonce.mod(new BigInteger("100000")) == BigInteger.ZERO){
                System.out.println("Mining Block. Current Nonce : " + nonce.toString());
            }

            // Pick new timestamp and reset nonce if nonce value is above max
            if (nonce.compareTo(nonceMax) > 0) {
                header.setTimeStamp(System.currentTimeMillis());
                nonce = BigInteger.ONE;
            }
        }

        System.out.println("Block mined. Nonce : " + nonce.toString());
        System.out.println("Resulting hash : 0x" + Hasher.bytesToHexString(hash));
        System.out.println();

        return new Block(header, data);
    }


}
