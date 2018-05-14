package blockchain.ledger_file.convertion;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.utility.ByteArrayReader;
import blockchain.utility.ByteUtils;

import java.util.ArrayList;

/* This class converts block objects to byte arrays and back */

public class BlockConverter extends Converter<Block> {

    private final short OBJECT_TYPE_ID = 1; // Identifies the type of object that this class converts
    private HeaderConverter headerConverter;
    private DataConverter dataConverter;

    public BlockConverter(short CONVERTER_VERSION_UID) {
        super(CONVERTER_VERSION_UID);
        headerConverter = new HeaderConverter(CONVERTER_VERSION_UID);
        dataConverter = new DataConverter(CONVERTER_VERSION_UID);
    }

    @Override // Converts a block object to a corresponding byte array
    public byte[] bytesFromInstance(Block block) {
        ArrayList<byte[]> bytes = new ArrayList<>();

        bytes.add(headerConverter.bytesFromInstance(block.getHeader()));
        bytes.add(dataConverter.bytesFromInstance(block.getData()));

        return ByteUtils.combineByteArrays(bytes);
    }

    @Override // Converts a byte array to a corresponding Block object
    public Block instanceFromBytes(byte[] bytes) {
        ByteArrayReader byteReader = new ByteArrayReader(bytes);
        Header header = headerConverter.instanceFromBytes(byteReader.readNext(headerConverter.getByteSize(), false));

        int dataByteSize = bytes.length - headerConverter.getByteSize();
        Data data = dataConverter.instanceFromBytes(byteReader.readNext(dataByteSize, false));

        return new Block(header, data);
    }

    @Override
    public short getOBJECT_TYPE_UID() {
        return OBJECT_TYPE_ID;
    }

    @Override
    public int getByteSize() {
        return -1;
    }
}
