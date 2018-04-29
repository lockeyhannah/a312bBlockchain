package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */

public class FileOverview implements DataPoint {

    private String ownerIp;
    private String fileId;
    private StorageContract[] storageContracts;
    private int chunkCount;


    public FileOverview(String ownerIp, String fileId, StorageContract[] storageContracts) {
        this.ownerIp = ownerIp;
        this.fileId = fileId;
        this.storageContracts = storageContracts;
        chunkCount = storageContracts.length;
    }

    @Override
    public String getFormattedDataString() {
        StringBuilder sb = new StringBuilder();
        int amountOfChunks = storageContracts.length;

        sb.append("Owners IP address : ").append(ownerIp).append("\n")
                .append("File ID : ").append(fileId).append("\n")
                .append("Amount of chunks : ").append(amountOfChunks).append("\n")
                .append("All file contracts : ").append("\n");

        for (int i = 0; i < amountOfChunks; i++) {
            sb.append("Contract for chunk : ").append(i).append("\n");
            sb.append(storageContracts[i].getFormattedDataString()).append("\n");
        }

        sb.append("\n");

        return sb.toString();
    }

    public String getOwnerIp() {
        return ownerIp;
    }

    public String getFileId() {
        return fileId;
    }

    public StorageContract[] getStorageContracts() {
        return storageContracts;
    }

    public int getChunkCount() {
        return chunkCount;
    }

}
