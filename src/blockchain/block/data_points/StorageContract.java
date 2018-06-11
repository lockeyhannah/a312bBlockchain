package blockchain.block.data_points;

/*
 * This contains information about a contract of file storage made between two units in the network
 */

import blockchain.utility.ByteUtils;

import java.util.ArrayList;
import java.util.Date;

public class StorageContract implements DataPoint {

    private final String fileId;
    private final String fileOwnerID;
    private final String storageUnitID;

    private final long contractTerminationTime; // Time of contract termination measured in millis since epoch
    private final double reward; // The amount of tokens storing the file until contract termination

    public StorageContract(String fileId, String fileOwnerID, String storageUnitID, long contractTerminationTime, double reward) {
        this.fileId = fileId;
        this.fileOwnerID = fileOwnerID;
        this.storageUnitID = storageUnitID;
        this.contractTerminationTime = contractTerminationTime;
        this.reward = reward;
    }

    @Override // Returns the bytes used for hashing
    public byte[] getBytes() {
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
        return String.format("Storage Contract\nFile Owner : %-10s | Storage unit : %-10s | Payment : %2.4f\n" +
                "File id : %-13s | Expiration : %s\n", fileOwnerID, storageUnitID, reward, fileId, new Date(contractTerminationTime).toString());
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

    @Override
    public boolean containsIdentifier(String id) {
        return (fileId.equals(id) || fileOwnerID.equals(id) || storageUnitID.equals(id));
    }

    @Override
    public double getBalanceChange(String userId) {
        // Return the negative reward if the user is the paying party of the contract
        if (fileOwnerID.equals(userId))
            return -reward;

        // Return the reward if the user is the storage unit and the contract has ended
        if (storageUnitID.equals(userId) && System.currentTimeMillis() > getContractTerminationTime())
            return reward;

        return 0;
    }
}


