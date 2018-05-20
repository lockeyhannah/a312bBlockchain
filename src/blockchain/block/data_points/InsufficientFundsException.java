package blockchain.block.data_points;

public class InsufficientFundsException extends Exception {

    private DataPoint dataPoint;

    public InsufficientFundsException(String message, DataPoint dataPoint) {
        super(message);
        this.dataPoint = dataPoint;
    }

    public DataPoint getDataPoint() {
        return dataPoint;
    }
}
