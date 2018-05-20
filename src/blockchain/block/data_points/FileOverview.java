package blockchain.block.data_points;

/* Represents an overview of storage for all chunks of a single file
 * This contains information about the IP of the file owner
 *
 */

import blockchain.utility.ByteUtils;

import java.util.ArrayList;

public class FileOverview implements DataPoint {

    private String ownerID; // IP address of the original file owner
    private String fileId; //
    private ArrayList<StorageContract> storageContracts; // List of all chunk storage contracts for the fle
    private int chunkCount; // Amount of chunks that this file is split into

    public FileOverview(String ownerIp, String fileId, ArrayList<StorageContract> storageContracts) {
        this.ownerID = ownerIp;
        this.fileId = fileId;
        this.storageContracts = storageContracts;
        chunkCount = storageContracts.size();
    }

    @Override // Returns a formatted string including all storage contracts
    public String getFormattedDataString() {
        StringBuilder sb = new StringBuilder();
        int amountOfChunks = storageContracts.size();

        sb.append("Owners IP address : ").append(ownerID).append("\n")
                .append("File ID : ").append(fileId).append("\n")
                .append("Amount of chunks : ").append(amountOfChunks).append("\n")
                .append("All file contracts : ").append("\n");

        for (int i = 0; i < amountOfChunks; i++) {
            sb.append("Contract for chunk : ").append(i).append("\n");
            sb.append(storageContracts.get(i).getFormattedDataString()).append("\n");
        }

        sb.append("\n");

        return sb.toString();
    }

    @Override // Returns the bytes used for hashing
    public byte[] getBytes() {
        ArrayList<byte[]> byteArrays = new ArrayList<>();

        // Add overview details bytes
        byteArrays.add(ownerID.getBytes());
        byteArrays.add(fileId.getBytes());

        // Add bytes from all contracts
        for (StorageContract contract : storageContracts)
            byteArrays.add(contract.getBytes());

        return ByteUtils.combineByteArrays(byteArrays);
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getFileId() {
        return fileId;
    }

    public ArrayList<StorageContract> getStorageContracts() {
        return storageContracts;
    }

    public int getChunkCount() {
        return chunkCount;
    }

}
