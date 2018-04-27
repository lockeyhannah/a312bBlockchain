package blockchain.block.data_points;

import java.io.IOException;

public interface Savable {

    Savable getInstanceFromBytes(byte[] b) throws IOException;
    byte[] getByteArray();
    int getByteSize();

}
