package blockchain.ledger_file.convertion;

import blockchain.block.data_points.StorageContract;
import blockchain.utility.ByteArrayReader;
import blockchain.utility.ByteUtils;

import java.util.ArrayList;

public class StorageContractConverter extends Converter<StorageContract> {

    private final short OBJECT_TYPE_UID = 5;

    // Byte length of each individual field when converted
    private static final int chunkIdByteLen = Integer.BYTES;
    private static final int storageIPByteLen = 16;
    private static final int terminationTimeByteLen = 20;
    private static final int chunkSizeByteLen = Long.BYTES;
    private static final int rewardByteLen = Double.BYTES;

    private final int CONTRACT_BYTE_LEN = chunkIdByteLen + storageIPByteLen +
            terminationTimeByteLen + chunkSizeByteLen +
            rewardByteLen;

    public StorageContractConverter(short CONVERTER_VERSION_UID) {
        super(CONVERTER_VERSION_UID);
    }

    @Override
    public StorageContract instanceFromBytes(byte[] bytes) {
        ByteArrayReader byteReader = new ByteArrayReader(bytes);

        String chunkIDBytes = new String(byteReader.readNext(chunkIdByteLen));
        String storageIP = new String(byteReader.readNext(storageIPByteLen));
        String terminationTime = new String(byteReader.readNext(terminationTimeByteLen));

        long chunkSize = ByteUtils.bytesToLong(byteReader.readNext(chunkSizeByteLen));
        double reward = ByteUtils.toDouble(byteReader.readNext(rewardByteLen));

        return new StorageContract(chunkIDBytes, storageIP, terminationTime, chunkSize, reward);
    }

    @Override
    public byte[] bytesFromInstance(StorageContract contract) {
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(contract.getChunkId().getBytes(), chunkIdByteLen));
        byteList.add(ByteUtils.extendByteArray(contract.getStorageIp().getBytes(), storageIPByteLen));
        byteList.add(ByteUtils.extendByteArray(contract.getContractTerminationTime().getBytes(), terminationTimeByteLen));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(contract.getChunkSize()), chunkSizeByteLen));
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
