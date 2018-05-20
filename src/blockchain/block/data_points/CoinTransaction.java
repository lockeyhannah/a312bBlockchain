package blockchain.block.data_points;

import blockchain.utility.ByteUtils;

import java.util.ArrayList;

public class CoinTransaction implements DataPoint {

    private String giverID;
    private String recipientID;
    private double coins;

    public CoinTransaction(String giverID, String recipientID, double coins) {
        this.giverID = giverID;
        this.recipientID = recipientID;
        this.coins = coins;
    }

    @Override
    public String getFormattedDataString() {
        return "Giver ID : " + giverID
                + "\nRecipient ID : " + recipientID
                + "\nCredit moved : " + coins + "\n";
    }

    @Override
    public byte[] getBytes() {
        ArrayList<byte[]> byteArrays = new ArrayList<>();

        byteArrays.add(giverID.getBytes());
        byteArrays.add(recipientID.getBytes());
        byteArrays.add(ByteUtils.toByteArray(coins));

        return ByteUtils.combineByteArrays(byteArrays);
    }

    public String getGiverID() {
        return giverID;
    }

    public String getRecipientID() {
        return recipientID;
    }

    public double getCoins() {
        return coins;
    }
}
