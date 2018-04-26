package blockchain.block;

import blockchain.block.data_points.Savable;

import java.nio.ByteBuffer;

public class Header implements Savable{

    public static final String MAGIC_ID = "FAC"; //Hvis den her skal bruges skal jeg lige vide til hvad.

    private byte[] prevHash;    // Hash of previous block header
    private byte[] dataHash;    // Hash of block data (without nonce)

    private byte[] nonce;       // Nonce value appended to the hashed data to generate a hash below the target value
    private byte[] target;      // Hash values must be smaller than target to be valid

    private byte[] timeStamp;    // Block creation time
    private long blockId;

    public Header(long blockId, byte[] prevHash, byte[] dataHash, byte[] nonce, byte[] target, byte[] timeStamp) {
        this.blockId = blockId;
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

    public long getBlockId() {
        return blockId;
    }

    public void setBlockId(long blockId) {
        this.blockId = blockId;
    }

    @Override
    public Savable getInstanceFromBytes(byte[] b) {
        return null;
    }

    @Override
    public byte[] getByteArray() {
        return new byte[0];
    }

    @Override
    public int getByteSize() {
        return 0;
    }
}
