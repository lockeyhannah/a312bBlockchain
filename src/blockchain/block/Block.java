package blockchain.block;

import blockchain.block.data_points.Savable;
import blockchain.block.mining.Hasher;

import java.math.BigInteger;

public class Block implements Savable{

    private Data data;
    private Header header;

    public Block(Data data, Header header) {
        this.data = data;
        this.header = header;
    }

    public Data getData() {
        return data;
    }

    public Header getHeader() {
        return header;
    }

    // Prints out block information
    public void printBlock() {
        System.out.println("Block ID : " + header.getBlockId());
        System.out.println("Block header hash : " + Hasher.hashToHexString(Hasher.applySHA(header.getByteArray())));
        System.out.println("Previous header hash : " + Hasher.hashToHexString(header.getPrevHash()));
        System.out.println("Hash of block data : " + Hasher.hashToHexString(header.getDataHash()));
        System.out.println("Nonce : " + new BigInteger(header.getNonce()).toString());
        System.out.println("TimeStamp : " + header.getTimeStamp());
    }


    @Override
    public Block getInstanceFromBytes(byte[] b) {

        return null;
    }

    @Override
    public byte[] getByteArray() {

        return new byte[0];
    }

    @Override
    public int getByteSize() {
        return null;
    }
}
