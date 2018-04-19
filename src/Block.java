import block.Data;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Block {

    //hej
    private Data data; //dataen fra blocken


    public Block(String previousHash) {
        data = new Data();
        this.data.previousHash = previousHash;
        this.data.timeStamp = new Date().getTime();
        this.data.hash = calculateHash(data.getData().getBytes(), BigInteger.ONE.toByteArray());
    }

    public byte[] calculateHash(byte[] input, byte[] nonce) {
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

    public void mineBlock(BigInteger target){
        BigInteger nonce = new BigInteger("0");
        byte[] hash = calculateHash(data.getData().getBytes(), nonce.toByteArray());
        BigInteger hashInteger = new BigInteger(1, hash);

        while(hashInteger.compareTo(target) > 0){
            nonce = nonce.add(BigInteger.ONE);
            hash = calculateHash(data.getData().getBytes(), nonce.toByteArray());
            hashInteger = new BigInteger(1, hash);
        }

        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xFF & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        System.out.println("Nonce : " + nonce.toString());
        System.out.println("Hash (Decimal) : " + hashInteger.toString());
        System.out.println("Hash (Hex) : " + hexString.toString());
    }


}
