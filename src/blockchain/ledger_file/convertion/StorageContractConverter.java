package blockchain.ledger_file.convertion;

import blockchain.block.data_points.CoinTransaction;
import blockchain.block.data_points.StorageContract;
import blockchain.utility.ByteArrayReader;
import blockchain.utility.ByteUtils;

import java.util.ArrayList;

public class StorageContractConverter extends Converter<StorageContract> {

    private final short OBJECT_TYPE_UID = 5;

    // Byte length of each individual field when converted
    private static final int fileIdByteLen = Integer.BYTES;
    private static final int storageIdByteLen = CoinTransactionConverter.userIdByteLen;
    private static final int ownerIDByteLen = CoinTransactionConverter.userIdByteLen;
    private static final int terminationTimeByteLen = Long.BYTES;
    private static final int rewardByteLen = Double.BYTES;

    private final int CONTRACT_BYTE_LEN = fileIdByteLen + storageIdByteLen + ownerIDByteLen +
            terminationTimeByteLen + rewardByteLen;

    public StorageContractConverter(short CONVERTER_VERSION_UID) {
        super(CONVERTER_VERSION_UID);
    }

    //Converts byte array to a StorageContract object
    @Override
    public StorageContract instanceFromBytes(byte[] bytes) {
        ByteArrayReader byteReader = new ByteArrayReader(bytes);

        String chunkIDBytes = new String(byteReader.readNext(fileIdByteLen, true));
        String storageID = new String(byteReader.readNext(storageIdByteLen, true));
        String ownerID = new String(byteReader.readNext(ownerIDByteLen, true));
        byte[] timeStampBytes = byteReader.readNext(terminationTimeByteLen, true);
        long timeStamp = ByteUtils.toLong(timeStampBytes);

        double reward = ByteUtils.toDouble(byteReader.readNext(rewardByteLen, true));

        return new StorageContract(chunkIDBytes, ownerID, storageID, timeStamp, reward);
    }

    //Converts a StorageContract object to byte array
    @Override
    public byte[] bytesFromInstance(StorageContract contract) {
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(contract.getFileId().getBytes(), fileIdByteLen));
        byteList.add(ByteUtils.extendByteArray(contract.getStorageUnitID().getBytes(), storageIdByteLen));
        byteList.add(ByteUtils.extendByteArray(contract.getFileOwnerID().getBytes(), ownerIDByteLen));
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
