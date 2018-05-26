package blockchain.block.data_points;

import blockchain.utility.ByteUtils;

import java.util.ArrayList;

/*
 * Represents a transaction of tokens made between two units
 */

public class CoinTransaction implements DataPoint {

    private String giverID;
    private String recipientID;
    private double tokens;

    public CoinTransaction(String giverID, String recipientID, double tokens) {
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
}
