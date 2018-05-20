package blockchain.ledger_file.convertion;

import blockchain.block.Header;
import blockchain.utility.ByteArrayReader;
import blockchain.utility.ByteUtils;

import java.util.ArrayList;
import java.util.Date;

public class HeaderConverter extends Converter<Header> {

    private final short OBJECT_TYPE_UID = 2;

    private static final int blockIdByteLen = 8;            //BlockId length in bytes
    private static final int prevHashByteLen = 32;          //Previous hash length in bytes
    private static final int dataHashByteLen = 32;          //Data hash length in bytes
    private static final int targetByteLen = 32;            //Target length in bytes
    private static final int nonceByteLen = 32;             //Nonce length in bytes
    private static final int timeStampByteLen = Long.BYTES; //timeStamp length in bytes

    public HeaderConverter(short CONVERTER_VERSION_UID) {
        super(CONVERTER_VERSION_UID);
    }

    //Converts byte array to a Header object
    @Override
    public Header instanceFromBytes(byte[] bytes) {
        ByteArrayReader byteReader = new ByteArrayReader(bytes);

        byte[] blockIDBytes = byteReader.readNext(blockIdByteLen, false);
        long blockID = ByteUtils.toLong(blockIDBytes);
        byte[] prevHash = byteReader.readNext(prevHashByteLen, false);
        byte[] dataHash = byteReader.readNext(dataHashByteLen, false);
        byte[] nonce = byteReader.readNext(nonceByteLen, true);
        byte[] target = byteReader.readNext(targetByteLen, true);

        byte[] timeStampBytes = byteReader.readNext(timeStampByteLen, true);
        long timeStamp = ByteUtils.toLong(timeStampBytes);

        return new Header(blockID, prevHash, dataHash, nonce, target, timeStamp);
    }

    //Converts Header object to byte array
    @Override
    public byte[] bytesFromInstance(Header h) {
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(h.getBlockId()), blockIdByteLen));
        byteList.add(ByteUtils.extendByteArray(h.getPrevHash(), prevHashByteLen));
        byteList.add(ByteUtils.extendByteArray(h.getDataHash(), dataHashByteLen));
        byteList.add(ByteUtils.extendByteArray(h.getNonce(), nonceByteLen));
        byteList.add(ByteUtils.extendByteArray(h.getTarget(), targetByteLen));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(h.getTimeStamp()), timeStampByteLen));

        return ByteUtils.combineByteArrays(byteList);
    }

    @Override
    public short getOBJECT_TYPE_UID() {
        return OBJECT_TYPE_UID;
    }

    @Override
    public int getByteSize() {
        return blockIdByteLen + prevHashByteLen + dataHashByteLen + targetByteLen + nonceByteLen + timeStampByteLen;
    }
}
