package blockchain.block.data_points;

import blockchain.utility.ByteUtils;

import java.util.ArrayList;

/*
 * Represents a transaction of tokens made between two units
 */

public class TokenTransaction implements DataPoint {

    private final String giverID;
    private final String recipientID;
    private final double tokens;

    public TokenTransaction(String giverID, String recipientID, double tokens) {
        this.giverID = giverID;
        this.recipientID = recipientID;
        this.tokens = tokens;
    }

    @Override
    public String getFormattedDataString() {
        return "Giver ID : " + giverID
                + "\nRecipient ID : " + recipientID
                + "\nCredit moved : " + tokens + "\n";
    }

    @Override // Returns the bytes used for hashing
    public byte[] getBytes() {
        ArrayList<byte[]> byteArrays = new ArrayList<>();

        // Add bytes from all fields
        byteArrays.add(giverID.getBytes());
        byteArrays.add(recipientID.getBytes());
        byteArrays.add(ByteUtils.toByteArray(tokens));

        return ByteUtils.combineByteArrays(byteArrays);
    }

    public String getGiverID() {
        return giverID;
    }

    public String getRecipientID() {
        return recipientID;
    }

    public double getTokens() {
        return tokens;
    }

    @Override
    public boolean containsIdentifier(String id) {
        return (giverID.equals(id) || recipientID.equals(id));
    }

    @Override
    public double getBalanceChange(String userId) {
        // Return the negative token amount if the user is the paying party of the transaction
        if (giverID.equals(userId))
            return -tokens;

        // Return the token amount if the user is the recipient
        if (recipientID.equals(userId))
            return tokens;

        return 0;
    }

}
