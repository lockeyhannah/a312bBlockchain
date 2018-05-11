package blockchain.block.mining;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.utility.ByteUtils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlockGenerator {

    // Mines and returns a new block based on the given data
    // If previous header is null generate standard header data
    public static Block generateBlock(Data data, Header previousHeader){
        String timeStamp = generateTimeStamp();
        byte[] dataHash = Hasher.applySHA(data.getDataBytes());
        byte[] difficulty = BigInteger.TWO.pow(238).toByteArray(); // Set standard difficulty

        byte[] nonce = new byte[]{0};
        byte[] prevHeaderHash = new byte[32];

        long blockNo = 0;

        // Update information if previous header is available
        if(previousHeader != null){
            blockNo = previousHeader.getBlockId() + 1;
            prevHeaderHash = Hasher.applySHA(previousHeader.getBytes());
            difficulty = previousHeader.getDifficultyTarget(); // todo : calculate difficulty
        }

        Header header = new Header(blockNo, prevHeaderHash, dataHash, nonce, difficulty, timeStamp);
        mineBlock(header);

        return new Block(header, data);
    }

    public static String generateTimeStamp(){
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    // Mines a block and returns the resulting nonce value
    private static void mineBlock(Header header){
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

    }



}
