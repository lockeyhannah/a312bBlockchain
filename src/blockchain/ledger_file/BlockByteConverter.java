package blockchain.ledger_file;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.FileOverview;
import blockchain.block.data_points.StorageContract;
import blockchain.ledger_file.convertion.Converter;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockByteConverter {
/*
    // Amount of bytes used by each field in the header when stored in the ledger file
    private static final int blockIdByteLen = 8;    //BlockId length in bytes
    private static final int prevHashByteLen = 32;  //Previous hash length in bytes
    private static final int dataHashByteLen = 32;  //Data hash length in bytes
    private static final int targetByteLen = 32;    //Target length in bytes
    private static final int nonceByteLen = 32;     //Nonce length in bytes
    private static final int timeStampByteLen = 8;  //timeStamp length in bytes

    private static final int headerByteLen = blockIdByteLen + timeStampByteLen + targetByteLen +
            nonceByteLen + dataHashByteLen + prevHashByteLen;


    // Data
    public static final int DATA_POINT_COUNT_BYTE_LEN = 16;

    // File overview
    private static final int OWNER_IP_BYTE_LEN = 16;
    private static final int FILE_ID_BYTE_LEN = 4;
    private static final int CHUNK_COUNT_BYTE_LEN = 4;

    //
    private static final int chunkIdByteLen = 2;
    private static final int storageIPByteLen = 16;
    private static final int terminationTimeByteLen = 20;
    private static final int chunkSizeByteLen = 4;
    private static final int rewardByteLen = 8;

    private final int CONTRACT_BYTE_LEN = chunkIdByteLen + storageIPByteLen +
            terminationTimeByteLen + chunkSizeByteLen +
            rewardByteLen;


    public static Block getBlockFromBytes(byte[] bytes) {
        // Todo : add checks maybe
        Header header = getHeader(Arrays.copyOfRange(bytes, 0, headerByteLen));
        Data data = getData(Arrays.copyOfRange(bytes, headerByteLen + 1, bytes.length)); // Todo : maybe outofbounds???;

        return new Block(header, data);
    }

    public static byte[] getBytesFromBlock(Block block) {
        byte[] headerBytes = getHeaderBytes(block.getHeader());
        byte[] dataBytes = getDataBytes(block.getData());
        return ByteUtils.combineByteArrays(headerBytes, dataBytes);
    }

    private static byte[] getHeaderBytes(Header h) {
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(h.getBlockId()), blockIdByteLen));
        byteList.add(ByteUtils.extendByteArray(h.getPrevHash(), prevHashByteLen));
        byteList.add(ByteUtils.extendByteArray(h.getDataHash(), dataHashByteLen));
        byteList.add(ByteUtils.extendByteArray(h.getNonce(), nonceByteLen));
        byteList.add(ByteUtils.extendByteArray(h.getTarget(), targetByteLen));
        byteList.add(ByteUtils.extendByteArray(h.getTimeStamp().getBytes(), timeStampByteLen));

        return ByteUtils.combineByteArrays(byteList);
    }

    private static Header getHeader(byte[] bytes) {
        int offset = 0;

        byte[] blockIDBytes = Arrays.copyOfRange(bytes, offset, offset + blockIdByteLen);
        long blockID = ByteUtils.bytesToLong(blockIDBytes);
        offset += blockIdByteLen;

        byte[] prevHash = Arrays.copyOfRange(bytes, offset, offset + prevHashByteLen);
        offset += prevHashByteLen;

        byte[] dataHash = Arrays.copyOfRange(bytes, offset, offset + dataHashByteLen);
        offset += dataHashByteLen;

        byte[] nonce = Arrays.copyOfRange(bytes, offset, offset + nonceByteLen);
        offset += nonceByteLen;

        byte[] target = Arrays.copyOfRange(bytes, offset, offset + targetByteLen);
        offset += targetByteLen;

        String timeStamp = new String(Arrays.copyOfRange(bytes, offset, offset + timeStampByteLen));

        return new Header(blockID, prevHash, dataHash, nonce, target, timeStamp);
    }

    private static byte[] getDataBytes(Data data) {
        ArrayList<byte[]> byteList = new ArrayList<>();

        byte[] dataPointCountBytes = ByteUtils.toByteArray(data.getDatapointCount());
        byteList.add(ByteUtils.extendByteArray(dataPointCountBytes, DATA_POINT_COUNT_BYTE_LEN));

        ArrayList<DataPoint> dataPoints = data.getDataPoints();
        for(DataPoint dp : dataPoints){
            // TODO: 28-04-2018  TEMP : add different kinds of datapoints
            byteList.add(getBytesFromFileOverview((FileOverview) dp));
        }

        return ByteUtils.combineByteArrays(byteList);
    }

    private static Data getData(byte[] bytes) {
        int offset = 0;

        int dataPointCount = ByteUtils.toInt(Arrays.copyOfRange(bytes, offset, offset + DATA_POINT_COUNT_BYTE_LEN));
        offset += DATA_POINT_COUNT_BYTE_LEN;

        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        for(int i = 0; i < dataPointCount; i++){
            byte[] contractBytes = Arrays.copyOfRange(bytes, offset, offset + CONTRACT_BYTE_LEN);
            offset += CONTRACT_BYTE_LEN;
            contracts[i] = contractFromBytes(contractBytes);
        }

        return new FileOverview(ownerIP, fileId, contracts);
    }

    private static byte[] getBytesFromFileOverview(FileOverview fileOw){
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(fileOw.getOwnerIp().getBytes(), OWNER_IP_BYTE_LEN));
        byteList.add(ByteUtils.extendByteArray(fileOw.getFileId().getBytes(), FILE_ID_BYTE_LEN));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(fileOw.getChunkCount()), CHUNK_COUNT_BYTE_LEN));

        StorageContract[] contracts = fileOw.getStorageContracts();
        for(int i = 0; i < contracts.length; i++){
            byteList.add(getContractBytes(contracts[i]));
        }

        return ByteUtils.combineByteArrays(byteList);
    }

    private FileOverview getFileOverviewFromBytes(byte[] bytes) {
        int offset = 0;

        String ownerIP = new String(Arrays.copyOfRange(bytes, offset, offset + OWNER_IP_BYTE_LEN));
        offset += OWNER_IP_BYTE_LEN;

        String fileId = new String(Arrays.copyOfRange(bytes, offset, offset + FILE_ID_BYTE_LEN));
        offset += FILE_ID_BYTE_LEN;

        int chunkCount = ByteUtils.toInt(Arrays.copyOfRange(bytes, offset, offset + CHUNK_COUNT_BYTE_LEN));
        offset += CHUNK_COUNT_BYTE_LEN;

        StorageContract[] contracts = new StorageContract[chunkCount];
        for(int i = 0; i < chunkCount; i++){
            byte[] contractBytes = Arrays.copyOfRange(bytes, offset, offset + CONTRACT_BYTE_LEN);
            offset += CONTRACT_BYTE_LEN;
            contracts[i] = contractFromBytes(contractBytes);
        }

        return new FileOverview(ownerIP, fileId, contracts);
    }

    private static byte[] getContractBytes(StorageContract contract) {
        ArrayList<byte[]> byteList = new ArrayList<>();

        byteList.add(ByteUtils.extendByteArray(contract.getChunkId().getBytes(), chunkIdByteLen));
        byteList.add(ByteUtils.extendByteArray(contract.getContractTerminationTime().getBytes(), terminationTimeByteLen));
        byteList.add(ByteUtils.extendByteArray(contract.getStorageIp().getBytes(), storageIPByteLen));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(contract.getChunkSize()), chunkSizeByteLen));
        byteList.add(ByteUtils.extendByteArray(ByteUtils.toByteArray(contract.getReward()), rewardByteLen));

        return ByteUtils.combineByteArrays(byteList);
    }

    private StorageContract contractFromBytes(byte[] bytes) {
        int offset = 0;

        String chunkIDBytes = new String(Arrays.copyOfRange(bytes, offset, offset + chunkSizeByteLen));
        offset += chunkSizeByteLen;

        String terminationTime = new String(Arrays.copyOfRange(bytes, offset, offset + terminationTimeByteLen));
        offset += terminationTimeByteLen;

        String storageIP = new String(Arrays.copyOfRange(bytes, offset, offset +  storageIPByteLen));
        offset += storageIPByteLen;

        byte[] chunkSizeBytes = Arrays.copyOfRange(bytes, offset, offset + chunkSizeByteLen);
        long chunkSize = ByteUtils.bytesToLong(chunkSizeBytes);
        offset += chunkSizeByteLen;

        byte[] rewardBytes = Arrays.copyOfRange(bytes, offset, offset + rewardByteLen);
        double reward = ByteUtils.toDouble(rewardBytes);
        offset += rewardByteLen;

        String timeStamp = new String(Arrays.copyOfRange(bytes, offset, offset + timeStampByteLen));

        return new StorageContract(chunkIDBytes, terminationTime, storageIP, chunkSize, reward);
    }*/


}
