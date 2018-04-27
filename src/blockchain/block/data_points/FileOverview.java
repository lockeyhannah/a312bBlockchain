package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */

public class FileOverview implements DataPoint {

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

    public DataPoint getDataPoint(byte[] b) {

        //TODO : 25-04-2018 : lav denne method så den kan returne et nyt object med info i strings fra byte

        String str1 = "owner";
        String str2 = "fileID";
        StorageContract[] trans1 = new StorageContract[2];

        return new FileOverview(str1, str2, trans1);
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
        return 0;
    }

    @Override
    public String getFormattedDataString() {
        return null;
    }
}
