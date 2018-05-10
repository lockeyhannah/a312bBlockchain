package blockchain.block.mining;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    public static byte[] applySHA(byte[] input) {
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
            msgDigest.update(input);
            return msgDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Hashing algorithm not found");
            e.printStackTrace();
        }
        return null;
    }


    // TODO: 21-04-2018 : stjaalet fra det store interweb - forstaa lige hvad der sker
    public static String hashToHexString(byte[] hashBytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < hashBytes.length; i++) {
            String hex = Integer.toHexString(0xFF & hashBytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
