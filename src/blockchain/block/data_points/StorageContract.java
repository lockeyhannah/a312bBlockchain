package blockchain.block.data_points;

/*
 * This contains information about a contract of file storage
 * made between two units
 */


import blockchain.utility.ByteUtils;

import java.util.ArrayList;

public class StorageContract {

    private String chunkId;
    private String storageIp;
    private String contractTerminationTime;
    private long chunkSize;
    private double reward;

    public StorageContract(String chunkId, String storageIp, String terminationTime, long chunkSize, double reward) {
        this.chunkId = chunkId;
        this.storageIp = storageIp;
        this.contractTerminationTime = terminationTime;
        this.chunkSize = chunkSize;
        this.reward = reward;
    }

    public byte[] getBytes(){
        ArrayList<byte[]> allBytes = new ArrayList<>();

        allBytes.add(chunkId.getBytes());
        allBytes.add(storageIp.getBytes());
        allBytes.add(contractTerminationTime.getBytes());
        allBytes.add(ByteUtils.toByteArray(chunkSize));
        allBytes.add(ByteUtils.toByteArray(reward));

        return ByteUtils.combineByteArrays(allBytes);
    }

    public String getFormattedDataString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Chunk ID : ").append(chunkId).append("\n")
                .append("Storage Unit IP address : ").append(storageIp).append("\n")
                .append("Date for termination of contract : ").append(contractTerminationTime).append("\n")
                .append("Size of chunk : ").append(chunkSize).append("\n")
                .append("Reward for chunk : ").append(reward).append("\n\n");

        return sb.toString();
    }

    public String getChunkId() {
        return chunkId;
    }

    public String getStorageIp() {
        return storageIp;
    }

    public String getContractTerminationTime() {
        return contractTerminationTime;
    }

    public long getChunkSize() {
        return chunkSize;
    }

    public double getReward() {
        return reward;
    }



}
