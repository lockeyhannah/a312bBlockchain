package blockchain.block;

import blockchain.block.mining.Hasher;

public class Block {

    private Data data;
    private Header header;

    public Block(Header header, Data data) {
        this.data = data;
        this.header = header;
    }

    public Data getData() {
        return data;
    }

    public Header getHeader() {
        return header;
    }

    public byte[] getHash(){
        return Hasher.applySHA(getHeader().getBytes());
    }

    // Prints out block information
    public void printBlock() {
        System.out.println(header.getString());
        System.out.println(data.getString());
    }

}
