package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */


public class Transaction implements DataPoint {
    // TODO : Indsætte metoder til Transaction

    String chunkId;
    String storageIp;

    Transaction(String chunkId, String storageIp){
        this.chunkId = chunkId;
        this.storageIp = storageIp;
    }

    @Override
    public DataPoint getDataPoint(byte[] b) {
        //TODO : 25-04-2018 : Lave bytes om til string i nyt transaction objekt

        //kommer an på hvordan Tobias gemmer

        String str1 = "chunkID";
        String str2 = "storageIP";

        return new Transaction(str1, str2);
    }

    @Override
    public byte[] getByteArray() {
        // TODO: 21-04-2018 : temporary functionality - Add functional behavior
        return "bitch".getBytes();
    }


}
