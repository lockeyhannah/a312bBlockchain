package blockchain.block.data_points;

/*
 * Interface for a data point which is stored in the data part of the block
 */

public interface DataPoint{

    // Return a well-formatted string containing all relevant information from the data point
    String getFormattedDataString();

    // Return the bytes used for hashing
    byte[] getBytes();

    // Returns true if the data point contains the given identifier
    boolean containsIdentifier(String id);

    // Returns the change in balance for the given user in this data point
    double getBalanceChange(String userId);

}
