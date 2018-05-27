package blockchain.block.mining;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    // Applies sha256 to the given input and returns the hash bytes
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


    // Converts the given bytes to a hex string representation
    public static String bytesToHexString(byte[] hashBytes) {
        StringBuilder hexString = new StringBuilder();

        for (byte hashByte : hashBytes) {
            // Convert byte to an unsigned value and then convert to a string
            String hex = Integer.toHexString(0xFF & hashByte);

            // Append a leading 0 if hex value is only one character long (ie. less than 16)
            if (hex.length() == 1)
                hexString.append('0');

            hexString.append(hex);
        }

        return hexString.toString();
    }
}
