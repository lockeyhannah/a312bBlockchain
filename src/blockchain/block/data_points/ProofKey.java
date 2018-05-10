package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */

public class ProofKey implements DataPoint {

    private String key;

    public String getProofKey(){
        return key;
    }

    public void setProofKey(String key){
        this.key = key;
    }

    @Override
    public String getFormattedDataString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Proof key : ").append(key).append("\n");

        return sb.toString();
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }
}
