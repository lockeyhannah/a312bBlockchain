package blockchain.ledger_file.convertion;

import blockchain.block.data_points.StorageContract;
import blockchain.ledger_file.LedgerReader;
import blockchain.utility.ByteArrayReader;
import blockchain.utility.ByteUtils;

import java.util.ArrayList;
import java.util.Date;

public class StorageContractConverter extends Converter<StorageContract> {

    private final short OBJECT_TYPE_UID = 5;

    // Byte length of each individual field when converted
    private static final int chunkIdByteLen = Integer.BYTES;
    private static final int storageIPByteLen = 16;
    private static final int terminationTimeByteLen = Long.BYTES;
    private static final int rewardByteLen = Double.BYTES;

    private final int CONTRACT_BYTE_LEN = chunkIdByteLen + storageIPByteLen +
            terminationTimeByteLen +
            rewardByteLen;

    public StorageContractConverter(short CONVERTER_VERSION_UID) {
        super(CONVERTER_VERSION_UID);
    }

    //Converts byte array to a StorageContract object
    @Override
    public StorageContract instanceFromBytes(byte[] bytes) {
        ByteArrayReader byteReader = new ByteArrayReader(bytes);

        String chunkIDBytes = new String(byteReader.readNext(chunkIdByteLen, true));
        String storageIP = new String(byteReader.readNext(storageIPByteLen, true));
        byte[] timeStampBytes = byteReader.readNext(terminationTimeByteLen, true);
        long timeStamp = ByteUtils.toLong(timeStampBytes);

        double reward = ByteUtils.toDouble(byteReader.readNext(rewardByteLen, true));

        return new StorageContract(chunkIDBytes, storageIP, timeStamp, reward);
    }

    //Converts a StorageContract object to byte array
    @Override
    public byte[] bytesFromInstance(StorageContract contract) {
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(contract.getChunkId().getBytes(), chunkIdByteLen));
        byteList.add(ByteUtils.extendByteArray(contract.getStorageID().getBytes(), storageIPByteLen));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(contract.getContractTerminationTime()), terminationTimeByteLen));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(contract.getReward()), rewardByteLen));

        return ByteUtils.combineByteArrays(byteList);
    }

    @Override
    public short getOBJECT_TYPE_UID() {
        return OBJECT_TYPE_UID;
    }

    public int getByteSize() {
        return CONTRACT_BYTE_LEN;
    }

}
