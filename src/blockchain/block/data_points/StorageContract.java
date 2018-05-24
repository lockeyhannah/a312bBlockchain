package blockchain.block.data_points;

/*
 * This contains information about a contract of file storage made between two units in the network
 */

import blockchain.utility.ByteUtils;
import java.util.ArrayList;

public class StorageContract implements DataPoint {

    private String fileId; // Unique identifier of the file
    private String fileOwnerID; // ID of the original file owner
    private String storageUnitID; // ID of the storage unit

    private long contractTerminationTime; // Time of contract termination measuered in millis since epoch
    private double reward; // The amount of tokens storing the file until contract termination

    public StorageContract(String fileId, String fileOwnerID, String storageUnitID, long contractTerminationTime, double reward) {
        this.fileId = fileId;
        this.fileOwnerID = fileOwnerID;
        this.storageUnitID = storageUnitID;
        this.contractTerminationTime = contractTerminationTime;
        this.reward = reward;
    }

    // Returns the bytes used for hashing
    public byte[] getBytes(){
        ArrayList<byte[]> allBytes = new ArrayList<>();

        allBytes.add(fileId.getBytes());
        allBytes.add(fileOwnerID.getBytes());
        allBytes.add(storageUnitID.getBytes());
        allBytes.add(ByteUtils.toByteArray(contractTerminationTime));
        allBytes.add(ByteUtils.toByteArray(reward));

        return ByteUtils.combineByteArrays(allBytes);
    }

    // Returns a formatted string containing contract information
    public String getFormattedDataString() {
        StringBuilder sb = new StringBuilder();

        sb.append("File ID : ").append(fileId).append("\n")
                .append("File owner ID : ").append(fileOwnerID).append("\n")
                .append("Storage Unit IP address : ").append(storageUnitID).append("\n")
                .append("Date for termination of contract : ").append(contractTerminationTime).append("\n")
                .append("Reward for chunk : ").append(reward).append("\n\n");

        return sb.toString();
    }

    public long getContractTerminationTime() {
        return contractTerminationTime;
    }

    public double getReward() {
        return reward;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFileOwnerID() {
        return fileOwnerID;
    }

    public String getStorageUnitID() {
        return storageUnitID;
    }

}


