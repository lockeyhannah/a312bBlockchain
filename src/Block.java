import block.Data;
import block.Header;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Block {

    //hej

    //Data
    //Datapoints[] - getStringValue(),
    //Smart contract, valuta,  nøgler
    //


    private Data data; //dataen fra blocken
    private Header header;

    public Block(String previousHash) {
        data = new Data();
        header = new Header();
        this.data.timeStamp = new Date().getTime();
    }

    public static byte[] calculateHash(byte[] input, byte[] nonce) {
        try{
            MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
            msgDigest.update(input);
            msgDigest.update(nonce);
            return msgDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Hashing algorithm not found");
            e.printStackTrace();
        }
        return null;
    }

    /* */
    public void mineBlock(BigInteger target){
        BigInteger nonce = new BigInteger("0");

        //Calculate the hash value with the default nonce and convert to an integer value
        byte[] hash = calculateHash(data.getDataString().getBytes(), nonce.toByteArray());
        BigInteger hashInteger = new BigInteger(1, hash);

        // Recalculate hash with new nonce values until the hash is below the target value
        while(hashInteger.compareTo(target) > 0){
            nonce = nonce.add(BigInteger.ONE);
            hash = calculateHash(data.getDataString().getBytes(), nonce.toByteArray());
            hashInteger = new BigInteger(1, hash);
        }

        // TODO: 19-04-2018 : save hash and nonce



    }


}
