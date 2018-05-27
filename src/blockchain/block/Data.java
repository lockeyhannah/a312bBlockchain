package blockchain.block;
/*
 * Represents the data section of the block and contains a list of all data points in the block
 * The data section can vary in size from block to block
 */

import blockchain.block.data_points.DataPoint;
import blockchain.utility.ByteUtils;

import java.io.*;
import java.util.ArrayList;

public class Data implements Serializable {

    private ArrayList<DataPoint> dataPoints;
    private int dataPointCount;

    // Construct a data object based on a list of DataPoints
    public Data(ArrayList<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
        dataPointCount = dataPoints.size();
    }

    // Constructs a data object with an empty list of DataPoints
    public Data() {
        this.dataPoints = new ArrayList<>();
        dataPointCount = 0;
    }

    // Adds a DataPoint and returns its' position in the list
    public int addData(DataPoint dataPoint) {
        dataPoints.add(dataPoint);

        // Increment after returning data point count
        return dataPointCount++;
    }

    public ArrayList<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public DataPoint getDataPoint(int index) {
        return dataPoints.get(index);
    }

    public int getDataPointCount() {
        return dataPointCount;
    }

    // Converts all DataPoints into a sequence of bytes for hashing
    public byte[] getDataBytes() {
        ArrayList<byte[]> allBytes = new ArrayList<>();
        for (DataPoint dp : dataPoints)
            allBytes.add(dp.getBytes());

        return ByteUtils.combineByteArrays(allBytes);
    }

    // Returns a string containing the formatted strings of each data point
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (DataPoint dp : dataPoints)
            sb.append(dp.getFormattedDataString());

        return sb.toString();
    }
}
