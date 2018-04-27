package blockchain.block;

import blockchain.block.data_points.Savable;
import blockchain.ledger_file.ByteUtils;


import java.nio.ByteBuffer;
import java.util.ArrayList;

import static blockchain.ledger_file.ByteUtils.*;

public class Header implements Savable{

    private long blockId;
    private final int blockIdLength = 8;//BlockId length in bytes

    private byte[] prevHash;    // Hash of previous block header
    private final int prevHashLength = 32; //Previous hash length in bytes
    private byte[] dataHash;    // Hash of block data (without nonce)
    private final int dataHashLength = 32;//Data hash length in bytes

    private byte[] nonce;// Nonce value appended to the hashed data to generate a hash below the target value
    private final int nonceLength = 32; //Nonce length in bytes
    private byte[] target;      // Hash values must be smaller than target to be valid
    private final int targetLength = 32; //Target length in bytes


    private String timeStamp;    // Block creation time
    private final int timeStampLength = 8;//timeStamp length in bytes

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
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(ByteUtils.longToBytes(blockId), blockIdLength));
        byteList.add(ByteUtils.extendByteArray(prevHash, prevHashLength));
        byteList.add(ByteUtils.extendByteArray(dataHash, dataHashLength));
        byteList.add(ByteUtils.extendByteArray(nonce, nonceLength));
        byteList.add(ByteUtils.extendByteArray(target, targetLength));
        byteList.add(ByteUtils.extendByteArray(timeStamp.getBytes(), timeStampLength));

        return ByteUtils.combineByteArrays(byteList);
    }

    @Override
    public int getByteSize() {
        return nonceLength+targetLength+blockIdLength+prevHashLength+dataHashLength+timeStampLength;
    }
}
