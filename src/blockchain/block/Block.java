package blockchain.block;

import blockchain.block.mining.Hasher;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(data, block.data) &&
                Objects.equals(header, block.header);
    }
}
