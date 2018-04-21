package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */

public class SmartContract implements DataPoint {

    // TODO : Indsætte metoder til SmartContracts

    @Override
    public byte[] getByteArray() {
        // TODO: 21-04-2018 : temporary functionality - Add functional behavior
        return "bitch".getBytes();
    }

}
