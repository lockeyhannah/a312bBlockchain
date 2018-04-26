package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */


import blockchain.ledger_file.ByteUtils;

public class Transaction implements Savable, DataPoint {
    // TODO : Indsætte metoder til Transaction

    private final int maxByteSize = 16;

    String chunkId;
    String storageIp;
    int chunkSize;


    Transaction(String chunkId, String storageIp){
        this.chunkId = chunkId;
        this.storageIp = storageIp;
    }

    @Override
    public Transaction getInstanceFromBytes(byte[] b) {
        //TODO : 25-04-2018 : Lave bytes om til string i nyt transaction objekt

        //kommer an på hvordan Tobias gemmer
        String str1 = "chunkID";
        String str2 = "storageIP";

        return new Transaction(str1, str2);
    }

    @Override
    public byte[] getByteArray() {
        return ByteUtils.combineByteArrays(chunkId.getBytes(), storageIp.getBytes());
    }

    @Override
    public int getByteSize() {
        return maxByteSize;
    }


}
