package blockchain.block;

import blockchain.block.data_points.Savable;


import java.nio.ByteBuffer;

import static blockchain.ledger_file.ByteUtils.*;

public class Header implements Savable{

    private byte[] prevHash;    // Hash of previous block header
    private byte[] dataHash;    // Hash of block data (without nonce)

    private byte[] nonce;       // Nonce value appended to the hashed data to generate a hash below the target value
    private byte[] target;      // Hash values must be smaller than target to be valid

    private String timeStamp;    // Block creation time
    private long blockId;

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
        return combineByteArrays(combineByteArrays(longToBytes(this.blockId),this.prevHash),
                combineByteArrays(this.dataHash,combineByteArrays(this.nonce,
                        combineByteArrays(this.target,this.timeStamp.getBytes()))));



    }

    @Override
    public int getByteSize() {
        return 0;
    }
}
