package blockchain.block.data_points;

// A unique identifier of a data point, containing information about position in the chain for easy retrieval

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
