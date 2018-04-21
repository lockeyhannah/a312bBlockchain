package blockchain.block;

public class Header {

    public static final String MAGIC_ID = "FAC";

    private byte[] prevHash;    // Hash of previous block header
    private byte[] dataHash;    // Hash of block data (without nonce)

    private byte[] nonce;       // Nonce value appended to the hashed data to generate a hash below the target value
    private byte[] target;      // Hash values must be smaller than target to be valid

    private long timeStamp;     // Block creation time


    public Header(byte[] prevHash, byte[] dataHash, byte[] nonce, byte[] target, long timeStamp) {
        this.prevHash = prevHash;
        this.dataHash = dataHash;
        this.nonce = nonce;
        this.target = target;
        this.timeStamp = timeStamp;
    }

    public byte[] getBytes(){
        return null; // TODO: 21-04-2018 : add functionality
    }

    public byte[] getDifficultyTarget(){
        return target;
    }

    public byte[] getNonce() {
        return nonce;
    }

    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }


}
