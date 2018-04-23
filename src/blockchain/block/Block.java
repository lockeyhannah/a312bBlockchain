package blockchain.block;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Block {

    private Data data;
    private Header header;

    public Block(Data data, Header header) {
        this.data = data;
        this.header = header;
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
        byte[] hash = calculateHash(data.getByteArray(), nonce.toByteArray());
        BigInteger hashInteger = new BigInteger(1, hash);

        // Recalculate hash with new nonce values until the hash is below the target value
        while(hashInteger.compareTo(target) > 0){
            nonce = nonce.add(BigInteger.ONE);
            hash = calculateHash(data.getByteArray(), nonce.toByteArray());
            hashInteger = new BigInteger(1, hash);
        }

        // TODO: 19-04-2018 : save hash and nonce
    }

    public Data getData() {
        return data;
    }

    public Header getHeader() {
        return header;
    }

    // Prints out block information
    public void printBlock(){
        System.out.println("Block header hash : " + Hasher.hashToHexString(Hasher.applySHA(header.getBytes())));
        System.out.println("Previous header hash : " + Hasher.hashToHexString(header.getPrevHash()));
        System.out.println("Hash of block data : " + Hasher.hashToHexString(header.getDataHash()));
        System.out.println("Nonce : " + new BigInteger(header.getNonce()).toString());
        System.out.println("TimeStamp : " + Hasher.hashToHexString(header.getTimeStamp()));
    }


}
