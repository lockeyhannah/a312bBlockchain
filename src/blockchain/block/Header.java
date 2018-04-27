package blockchain.block;

import blockchain.block.data_points.Savable;


import java.nio.ByteBuffer;

import static blockchain.ledger_file.ByteUtils.*;

public class Header implements Savable{

    private byte[] prevHash;    // Hash of previous block header
    private byte[] dataHash;    // Hash of block data (without nonce)

    private byte[] nonce;// Nonce value appended to the hashed data to generate a hash below the target value
    private final int nonceLength = 32; //nonce length in bytes
    private byte[] target;      // Hash values must be smaller than target to be valid
    private final int targetLength = 32;


    private String timeStamp;    // Block creation time
    private final int timeStampLength = 8;
    private long blockId;
    private final int blockIdLength = 8;

    public Header(long blockId, byte[] prevHash, byte[] dataHash, byte[] nonce, byte[] target, String timeStamp) {
        this.blockId = blockId;
        this.prevHash = prevHash;
        this.dataHash = dataHash;
        this.nonce = nonce;
        this.target = target;
        this.timeStamp = timeStamp;
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

    public String getTimeStamp() {
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
        byte[] newNonce = new byte[nonceLength];
        System.arraycopy(this.nonce, 0, newNonce, nonceLength - this.nonce.length, this.nonce.length);

        byte[] newTarget = new byte[targetLength];
        System.arraycopy(this.target, 0, newTarget, targetLength - this.target.length, this.target.length);

        byte[] newTimeStamp = new byte[timeStampLength];
        System.arraycopy(this.timeStamp.getBytes(), 0, newTimeStamp, timeStampLength - this.timeStamp.length(), this.timeStamp.length());

        byte[] newBlockId = new byte[nonceLength];
        System.arraycopy(longToBytes(this.blockId), 0, newBlockId, blockIdLength - longToBytes(this.blockId).length, longToBytes(this.blockId).length);

        return combineByteArrays(combineByteArrays(newBlockId,this.prevHash),combineByteArrays(this.dataHash,combineByteArrays(newNonce,combineByteArrays(newTarget,newTimeStamp))));
    }

    @Override
    public int getByteSize() {
        return 0;
    }
}
