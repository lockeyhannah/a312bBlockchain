package blockchain.block.data_points;

/*
 * This contains information about a contract of file storage
 * made between two units
 */


import blockchain.ledger_file.ByteUtils;

public class StorageContract implements DataPoint {
    // TODO : Indsætte metoder til Transaction

    private final int maxByteSize = 16;

    String chunkId;
    String storageIp;
    String contractTerminationTime;
    int chunkSize;
    double reward;

    private static final int chunkIdByteLen = 2, storageIPByteLen = 8;

    StorageContract(String chunkId, String storageIp, String terminationTime, int chunkSize, double reward){
        this.chunkId = chunkId;
        this.storageIp = storageIp;
        this.contractTerminationTime = terminationTime;
        this.chunkSize = chunkSize;
        this.reward = reward;
    }

    @Override
    public StorageContract getInstanceFromBytes(byte[] b) {
        //TODO : 25-04-2018 : Lave bytes om til string i nyt transaction objekt
        return null;
    }

    @Override
    public byte[] getByteArray() {
        return ByteUtils.combineByteArrays(chunkId.getBytes(), storageIp.getBytes());
    }

    @Override
    public int getByteSize() {
        return maxByteSize;
    }


    @Override
    public String getFormattedDataString() {
        return null;
    }
}
