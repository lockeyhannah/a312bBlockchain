package blockchain.block;

import blockchain.block.mining.Hasher;
import blockchain.utility.ByteUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

import static blockchain.utility.ByteUtils.*;

public class Header {

    private final long blockId;

    private final byte[] prevHash;    // Hash of previous block header
    private byte[] dataHash;    // Hash of block data (without nonce)

    private byte[] nonce;       // Nonce value appended to the hashed data to generate a hash below the target value
    private final byte[] target;      // Hash values must be smaller than target to be valid

    private long timeStamp;   // Block creation time

    public Header(long blockId, byte[] prevHash, byte[] dataHash, byte[] nonce, byte[] target, long timeStamp) {
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public long getBlockId() {
        return blockId;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    // Returns the combined byte arrays of all fields for hashing
    public byte[] getBytes() {
        byte[] bytes = combineByteArrays(ByteUtils.toByteArray(blockId), prevHash);
        bytes = combineByteArrays(bytes, dataHash);
        bytes = combineByteArrays(bytes, nonce);
        bytes = combineByteArrays(bytes, target);
        bytes = combineByteArrays(bytes, ByteUtils.toByteArray(timeStamp));
        return bytes;
    }

    @Override
    public String toString() {
        return String.format("Block ID : %d\nPrevious header hash : %s\nCurrent header hash  : %s\nHash target value    : %64s\n",
                getBlockId(), Hasher.bytesToHexString(getPrevHash()),
                Hasher.bytesToHexString(Hasher.applySHA(getBytes())),
                Hasher.bytesToHexString(getDifficultyTarget()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Header header = (Header) o;
        return blockId == header.blockId &&
                Arrays.equals(prevHash, header.prevHash) &&
                Arrays.equals(dataHash, header.dataHash) &&
                Arrays.equals(nonce, header.nonce) &&
                Arrays.equals(target, header.target) &&
                Objects.equals(timeStamp, header.timeStamp);
    }
}
