package blockchain.block.data_points;

/*
 * This contains information about a contract of file storage made between two units in the network
 */

import blockchain.utility.ByteUtils;

import java.util.ArrayList;

public class StorageContract {

    private String chunkId;
    private String storageIp; // IP address of the storage unit
    private String contractTerminationTime;
    private long chunkSize; // Size of the chunk measured in bytes
    private double reward; // The reward for storing the file until contract termination

    public StorageContract(String chunkId, String storageIp, String terminationTime, long chunkSize, double reward) {
        this.chunkId = chunkId;
        this.storageIp = storageIp;
        this.contractTerminationTime = terminationTime;
        this.chunkSize = chunkSize;
        this.reward = reward;
    }

    // Returns the bytes used for hashing
    public byte[] getBytes(){
        ArrayList<byte[]> allBytes = new ArrayList<>();

        allBytes.add(chunkId.getBytes());
        allBytes.add(storageIp.getBytes());
        allBytes.add(contractTerminationTime.getBytes());
        allBytes.add(ByteUtils.toByteArray(chunkSize));
        allBytes.add(ByteUtils.toByteArray(reward));

        return ByteUtils.combineByteArrays(allBytes);
    }

    // Returns a formatted string containing contract information
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
