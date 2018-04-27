package blockchain.block.data_points;

/*
 * This contains information about a contract of file storage
 * made between two units
 */


import blockchain.ledger_file.ByteUtils;

public class StorageContract implements DataPoint {
    // TODO : Indsætte metoder til Transaction

    private final int maxByteSize = chunkIdByteLen + storageIPByteLen +
            contractTerminationTimeByteLen + chunkSizeByteLen +
            rewardByteLen;

    private String chunkId;
    private String storageIp;
    private String contractTerminationTime;
    private long chunkSize;
    private long reward;

    private static final int chunkIdByteLen = 2, storageIPByteLen = 8,
            contractTerminationTimeByteLen = 20, chunkSizeByteLen = 4,
            rewardByteLen = 8;

    StorageContract(String chunkId, String storageIp, String terminationTime, long chunkSize, long reward){
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

        byte[] b1 = ByteUtils.extendByteArray(chunkId.getBytes(), chunkIdByteLen);
        byte[] b2 = ByteUtils.extendByteArray(storageIp.getBytes(), storageIPByteLen);
        byte[] b3 = ByteUtils.extendByteArray(contractTerminationTime.getBytes(), contractTerminationTimeByteLen);
        byte[] b4 = ByteUtils.extendByteArray(ByteUtils.longToBytes(chunkSize), chunkSizeByteLen);
        byte[] b5 = ByteUtils.extendByteArray(ByteUtils.longToBytes(reward), rewardByteLen);

        byte[] b = ByteUtils.combineByteArrays(b1, b2);
        b = ByteUtils.combineByteArrays(b, b3);
        b = ByteUtils.combineByteArrays(b, b4);
        b = ByteUtils.combineByteArrays(b, b5);

        return b;
    }

    @Override
    public int getByteSize() {
        return maxByteSize;
    }


    @Override
    public String getFormattedDataString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Chunk ID : ").append(chunkId).append("\n")
        .append("Storage Unit IP address : ").append(storageIp).append("\n")
        .append("Date for termination of contract : ").append(contractTerminationTime).append("\n")
        .append("Size of chunk : ").append(chunkSize).append("\n")
        .append("Reward for chunk : ").append(reward). append("\n\n");

        return sb.toString();
    }
}
