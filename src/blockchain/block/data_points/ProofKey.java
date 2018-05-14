package blockchain.block.data_points;

/*
 *
 */

public class ProofKey implements DataPoint {

    private String key;

    public ProofKey(String key) {
        this.key = key;
    }

    public String getProofKey(){
        return key;
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
