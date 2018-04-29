package blockchain.block;

import blockchain.block.data_points.Savable;
import blockchain.block.mining.Hasher;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import static blockchain.ledger_file.ByteUtils.combineByteArrays;

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
        //TODO: Har brug for lidt hjælp med at finde ud af hvorfor Savable confilicter med Data og Header
        ByteBuffer headerByteBuffer = ByteBuffer.allocate(header.getByteSize());
        ByteBuffer dataByteBuffer = ByteBuffer.allocate(b.length-header.getByteSize());
        Savable tempHeader = header.getInstanceFromBytes(headerByteBuffer.array());
        Savable tempData = null;
        try {
            tempData = data.getInstanceFromBytes(dataByteBuffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Block tempBlock = new Block(tempHeader,tempData);
        return tempBlock;
    }

    @Override
    public byte[] getByteArray() {
        return combineByteArrays(header.getByteArray(),data.getByteArray());
    }

    @Override
    public int getByteSize() {
        return header.getByteSize()+data.getByteSize();
    }
}