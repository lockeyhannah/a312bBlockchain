package blockchain.block.data_points;

/*
 *
 * This class is a datapoint in blocks.
 *
 */


class Transaction implements DataPoint {
    // TODO : Indsætte metoder til Transaction

    @Override
    public byte[] getByteArray() {
        // TODO: 21-04-2018 : temporary functionality - Add functional behavior
        return "bitch".getBytes();
    }
}
