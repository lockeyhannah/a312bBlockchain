package blockchain.block.data_points;

import blockchain.block.Header;

public interface DataPoint{

    String getFormattedDataString();
    byte[] getBytes();

}
