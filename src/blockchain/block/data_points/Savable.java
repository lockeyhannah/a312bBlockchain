package blockchain.block.data_points;

public interface Savable {

    Savable getInstanceFromBytes(byte[] b);
    byte[] getByteArray();
    int getByteSize();

}
