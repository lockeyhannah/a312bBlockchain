package blockchain.block;

import java.nio.ByteBuffer;
import java.util.List;

public class Header {

    public static final String MAGIC_ID = "FAC";

    private byte[] prevHash;    // Hash of previous block header
    private byte[] dataHash;    // Hash of block data (without nonce)

    private byte[] nonce;       // Nonce value appended to the hashed data to generate a hash below the target value
    private byte[] target;      // Hash values must be smaller than target to be valid

    private byte[] timeStamp;    // Block creation time


    public Header(byte[] prevHash, byte[] dataHash, byte[] nonce, byte[] target, byte[] timeStamp) {
        this.prevHash = prevHash;
        this.dataHash = dataHash;
        this.nonce = nonce;
        this.target = target;
        this.timeStamp = timeStamp;
    }

    // Combines all header variables to a single byte array
    public byte[] getBytes(){
        int totalBytes = prevHash.length + dataHash.length + nonce.length + target.length + timeStamp.length;
        ByteBuffer bb = ByteBuffer.allocate(totalBytes);

        bb.put(prevHash);
        bb.put(dataHash);
        bb.put(nonce);
        bb.put(target);
        bb.put(timeStamp);

        return bb.array();
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

    public byte[] getPrevHash() {
        return prevHash;
    }

    public byte[] getDataHash() {
        return dataHash;
    }

    public byte[] getTarget() {
        return target;
    }

    public byte[] getTimeStamp() {
        return timeStamp;
    }
}
