package blockchain.block;

/*
 * Represents a single block in the chain.
 * The block contains a header and a data section.
 */


import blockchain.utility.StringUtil;

public class Block {

    // Represents the current version of the block chain
    public static final short BLOCKCHAIN_VERSION = 1;

    private final Data data;
    private final Header header;

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

    // Prints out all block information including all data points
    public String toString() {
        return StringUtil.line(88) + "Header:\n" + StringUtil.line(88) + header.toString()
                + StringUtil.line(88) + "Data:\n" + StringUtil.line(88) + data.toString()
                + StringUtil.line(88);
    }
}
