package blockchain.ledger_file.convertion;

import blockchain.block.data_points.FileOverview;
import blockchain.block.data_points.StorageContract;
import blockchain.utility.ByteArrayReader;
import blockchain.utility.ByteUtils;

import java.util.ArrayList;

public class FileOverViewConverter extends Converter<FileOverview> {

    private final short OBJECT_TYPE_UID = 4;

    private static final int OWNER_IP_BYTE_LEN = 16;
    private static final int FILE_ID_BYTE_LEN = 8;
    private static final int CHUNK_COUNT_BYTE_LEN = 4;

    private StorageContractConverter contractConverter;

    public FileOverViewConverter(short CONVERTER_VERSION_UID) {
        super(CONVERTER_VERSION_UID);
        contractConverter = new StorageContractConverter(CONVERTER_VERSION_UID);
    }

    @Override
    public FileOverview instanceFromBytes(byte[] bytes) {
        ByteArrayReader byteReader = new ByteArrayReader(bytes);

        String ownerIP = new String(byteReader.readNext(OWNER_IP_BYTE_LEN));
        String fileId = new String(byteReader.readNext(FILE_ID_BYTE_LEN));
        int chunkCount = ByteUtils.toInt(byteReader.readNext(CHUNK_COUNT_BYTE_LEN));


        StorageContract[] contracts = new StorageContract[chunkCount];
        for (int i = 0; i < chunkCount; i++) {
            byte[] contractBytes = byteReader.readNext(contractConverter.getByteSize());
            contracts[i] = contractConverter.instanceFromBytes(contractBytes);
        }

        return new FileOverview(ownerIP, fileId, contracts);
    }

    @Override
    public byte[] bytesFromInstance(FileOverview fileOw) {
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(fileOw.getOwnerIp().getBytes(), OWNER_IP_BYTE_LEN));
        byteList.add(ByteUtils.extendByteArray(fileOw.getFileId().getBytes(), FILE_ID_BYTE_LEN));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(fileOw.getChunkCount()), CHUNK_COUNT_BYTE_LEN));

        StorageContract[] contracts = fileOw.getStorageContracts();
        for (int i = 0; i < contracts.length; i++) {
            byteList.add(contractConverter.bytesFromInstance(contracts[i]));
        }

        return ByteUtils.combineByteArrays(byteList);
    }

    @Override
    public short getOBJECT_TYPE_UID() {
        return OBJECT_TYPE_UID;
    }

    @Override
    public int getByteSize() {
        return -1;
    }
}
