package blockchain.block;

import blockchain.block.mining.Hasher;


import java.io.Serializable;
import java.math.BigInteger;

import static blockchain.utility.ByteUtils.*;

public class Header implements Serializable{

    private long blockId;

    private byte[] prevHash;    // Hash of previous block header
    private byte[] dataHash;    // Hash of block data (without nonce)

    private byte[] nonce;       // Nonce value appended to the hashed data to generate a hash below the target value
    private byte[] target;      // Hash values must be smaller than target to be valid

    private String timeStamp;    // Block creation time

    public Header(long blockId, byte[] prevHash, byte[] dataHash, byte[] nonce, byte[] target, String timeStamp) {
        this.blockId = blockId;
        this.prevHash = prevHash;
        this.dataHash = dataHash;
        this.nonce = nonce;
        this.target = target;
        this.timeStamp = timeStamp;
    }

    public byte[] getDifficultyTarget() {
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

    public byte[] getBytes() {
        byte[] bytes = combineByteArrays(prevHash, dataHash);
        bytes = combineByteArrays(bytes, nonce);
        bytes = combineByteArrays(bytes, target);
        bytes = combineByteArrays(bytes, timeStamp.getBytes());
        return bytes;
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Block ID : " + getBlockId()).append("\n").
                append("Block header hash : " + Hasher.hashToHexString(Hasher.applySHA(getBytes()))).append("\n").
                append("Previous header hash : " + Hasher.hashToHexString(getPrevHash())).append("\n").
                append("Hash of block data : " + Hasher.hashToHexString(getDataHash())).append("\n").
                append("Nonce : " + new BigInteger(getNonce()).toString()).append("\n").
                append("Target : " + new BigInteger(getTarget()).toString()).append("\n").
                append("TimeStamp : " + getTimeStamp()).append("\n");
        return sb.toString();
    }

}
