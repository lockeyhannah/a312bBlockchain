package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */

public class ProofKey implements DataPoint {

    // TODO : Indsætte metoder til ProofKey

    private String key;

    @Override
    public byte[] getByteArray() {
        // TODO: 21-04-2018 : temporary functionality - Add functional behavior
        return "bitch".getBytes();
    }

    public String getProofKey(){
        return key;
    }

    public void setProofKey(String key){
        this.key = key;
    }

}
