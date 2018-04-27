package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */

public class FileOverview implements DataPoint {

    int maxByteSize;

    String ownerIp;
    String fileId;
    StorageContract[] storageContracts;
    int amountOfChunks;

    FileOverview(String ownerIp, String fileId, StorageContract[] storageContracts){
        this.ownerIp = ownerIp;
        this.fileId = fileId;
        this.storageContracts = storageContracts;
        amountOfChunks = storageContracts.length;
    }

    @Override
    public Savable getInstanceFromBytes(byte[] b) {
        return null;
    }

    public byte[] getByteArray() {
        // TODO: 21-04-2018 : temporary functionality - Add functional behavior
        return "bitch".getBytes();
    }

    @Override
    public int getByteSize() {
        return maxByteSize;
    }

    @Override
    public String getFormattedDataString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Owners IP address : ").append(ownerIp).append("\n")
        .append("File ID : ").append(fileId).append("\n")
        .append("Amount of chunks : ").append(amountOfChunks).append("\n")
        .append("All file contracts : ").append("\n");

        for(int i = 0; i < amountOfChunks; i++) {
            sb.append("Contract for chunk : ").append(i).append("\n");
            sb.append(storageContracts[i]).append("\n");
        }

        sb.append("\n");

        return sb.toString();
    }
}
