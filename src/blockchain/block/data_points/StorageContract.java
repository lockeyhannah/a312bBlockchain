package blockchain.block.data_points;

/*
 * This contains information about a contract of file storage made between two units in the network
 */

import blockchain.utility.ByteUtils;

import java.util.ArrayList;
import java.util.Date;

public class StorageContract {

    private String chunkId;
    private String storageID; // ID address of the storage unit
    private long contractTerminationTime;
    private double reward; // The reward for storing the file until contract termination

    public StorageContract(String chunkId, String storageIp, long terminationTime, double reward) {
        this.chunkId = chunkId;
        this.storageID = storageIp;
        this.contractTerminationTime = terminationTime;
        this.reward = reward;
    }

    // Returns the bytes used for hashing
    public byte[] getBytes(){
        ArrayList<byte[]> allBytes = new ArrayList<>();

        allBytes.add(chunkId.getBytes());
        allBytes.add(storageID.getBytes());
        allBytes.add(ByteUtils.toByteArray(contractTerminationTime));
        allBytes.add(ByteUtils.toByteArray(reward));

        return ByteUtils.combineByteArrays(allBytes);
    }

    // Returns a formatted string containing contract information
    public String getFormattedDataString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Chunk ID : ").append(chunkId).append("\n")
                .append("Storage Unit IP address : ").append(storageID).append("\n")
                .append("Date for termination of contract : ").append(contractTerminationTime).append("\n")
                .append("Reward for chunk : ").append(reward).append("\n\n");

        return sb.toString();
    }

    public String getChunkId() {
        return chunkId;
    }

    public String getStorageID() {
        return storageID;
    }

    public long getContractTerminationTime() {
        return contractTerminationTime;
    }

    public double getReward() {
        return reward;
    }
}
