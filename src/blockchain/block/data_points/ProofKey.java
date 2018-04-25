package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */

public class ProofKey implements DataPoint {

    // TODO : Proofkey er indtil videre ikke brugbart og skal fjernes

    private String key;

    @Override
    public DataPoint getDataPoint(byte[] b) {
        return null;
    }

    @Override
    public byte[] getByteArray() {
        // TODO: 21-04-2018 : temporary functionality - Add functional behavior
        return key.getBytes();
    }

    public String getProofKey(){
        return key;
    }

    public void setProofKey(String key){
        this.key = key;
    }

}
