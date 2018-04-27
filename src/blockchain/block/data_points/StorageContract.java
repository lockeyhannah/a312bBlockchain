package blockchain.block.data_points;

/*
 * This contains information about a contract of file storage
 * made between two units
 */


import blockchain.ledger_file.ByteUtils;

import java.util.ArrayList;

public class StorageContract implements DataPoint {
    // TODO : Indsætte metoder til Transaction



    private String chunkId;
    private String storageIp;
    private String contractTerminationTime;
    private long chunkSize;
    private long reward;

    private static final int chunkIdByteLen = 2, storageIPByteLen = 8,
            contractTerminationTimeByteLen = 20, chunkSizeByteLen = 4,
            rewardByteLen = 8;

    private final int maxByteSize = chunkIdByteLen + storageIPByteLen +
            contractTerminationTimeByteLen + chunkSizeByteLen +
            rewardByteLen;


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
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(chunkId.getBytes(), chunkIdByteLen));
        byteList.add(ByteUtils.extendByteArray(storageIp.getBytes(), storageIPByteLen));
        byteList.add(ByteUtils.extendByteArray(contractTerminationTime.getBytes(), contractTerminationTimeByteLen));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.longToBytes(chunkSize), chunkSizeByteLen));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.longToBytes(reward), rewardByteLen));

        return ByteUtils.combineByteArrays(byteList);
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
        .append("Reward for chunk : ").append(reward). append("\n");

        return sb.toString();
    }
}
