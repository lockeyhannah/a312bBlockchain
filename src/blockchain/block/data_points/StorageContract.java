package blockchain.block.data_points;

/*
 * This contains information about a contract of file storage
 * made between two units
 */


public class StorageContract implements DataPoint {
    // TODO : Indsætte metoder til Transaction

    private String chunkId;
    private String storageIp;
    private String contractTerminationTime;
    private long chunkSize;
    private double reward;

    public StorageContract(String chunkId, String storageIp, String terminationTime, long chunkSize, double reward){
        this.chunkId = chunkId;
        this.storageIp = storageIp;
        this.contractTerminationTime = terminationTime;
        this.chunkSize = chunkSize;
        this.reward = reward;
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
