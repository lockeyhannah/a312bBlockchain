package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */

public class SmartContract implements DataPoint {

    // TODO : Indsætte metoder til SmartContracts

    String ownerIp;
    String fileId;
    Transaction[] chunks;

    SmartContract(String ownerIp, String fileId, Transaction[] chunks){

        this.ownerIp = ownerIp;
        this.fileId = fileId;
        this.chunks = chunks;

    }

    @Override
    public DataPoint getDataPoint(byte[] b) {

        //TODO : 25-04-2018 : lav denne method så den kan returne et nyt object med info i strings fra byte

        String str1 = "owner";
        String str2 = "fileID";
        Transaction[] trans1 = new Transaction[2];

        return new SmartContract(str1, str2, trans1);
    }

    @Override
    public byte[] getByteArray() {
        // TODO: 21-04-2018 : temporary functionality - Add functional behavior
        return "bitch".getBytes();
    }

}
