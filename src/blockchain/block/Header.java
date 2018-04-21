package blockchain.block;

public class Header {

    private byte[] target;      // Hash values must be smaller than target to be valid
    private byte[] nonce;       // Nonce value appended to the hashed data to generate a hash below the target value

    private byte[] dataHash;    // Hash of block data (without nonce)
    private byte[] prevHash;    // Hash of previous block header
    private long timeStamp;     // Block creation time

    public Header(){

    }


}
