package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */

import blockchain.utility.ByteUtils;

import java.util.ArrayList;

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

    @Override // Returns the bytes used for hashing
    public byte[] getBytes() {
        ArrayList<byte[]> byteArrays = new ArrayList<>();

        // Add overview details bytes
        byteArrays.add(ownerIp.getBytes());
        byteArrays.add(fileId.getBytes());

        // Add bytes from all contracts
        for(StorageContract contract : storageContracts)
            byteArrays.add(contract.getBytes());

        return ByteUtils.combineByteArrays(byteArrays);
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
