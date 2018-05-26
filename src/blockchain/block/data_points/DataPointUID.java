package blockchain.block.data_points;

/*
 * A unique identifier for a data point
  * Containing information about position in the chain for fast retrieval of data point
 */

public class DataPointUID {

    private final long blockId;
    private final int dataPointNumber; // Position of the data point in the block

    public DataPointUID(long blockId, int dataPointNumber) {
        this.blockId = blockId;
        this.dataPointNumber = dataPointNumber;
    }

    public long getBlockId() {
        return blockId;
    }

    public int getDataPointNumber() {
        return dataPointNumber;
    }
}
