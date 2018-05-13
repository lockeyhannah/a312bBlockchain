package blockchain.block.data_points;

public class DataPointUID {

    private final long blockNumber;
    private final int dataPointNumber;

    public DataPointUID(long blockNumber, int dataPointNumber) {
        this.blockNumber = blockNumber;
        this.dataPointNumber = dataPointNumber;
    }

    public long getBlockNumber() {
        return blockNumber;
    }

    public int getDataPointNumber() {
        return dataPointNumber;
    }
}
