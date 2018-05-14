package blockchain.block.data_points;

/*
 * Interface for a data point which is stored in the data part of the block
 */

public interface DataPoint{

    String getFormattedDataString();
    byte[] getBytes();

}
