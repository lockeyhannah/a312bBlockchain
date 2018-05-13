package blockchain.block;
/*
 * This is the interface for all classes that is a part of data in blockchain.block
 *
 */

import blockchain.block.data_points.DataPoint;
import blockchain.utility.ByteUtils;

import java.io.*;
import java.util.ArrayList;

public class Data implements Serializable{

    private ArrayList<DataPoint> dataPoints;
    private int datapointCount;

    public Data(ArrayList<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
        datapointCount = dataPoints.size();
    }

    public Data() {
        this.dataPoints = new ArrayList<>();
        datapointCount = 0;
    }

    // Adds a given data point to the list and returns the data point count
    public int addData(DataPoint dataPoint){
        dataPoints.add(dataPoint);
        // Increment before returning data point count
        return ++datapointCount;
    }

    public ArrayList<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public int getDatapointCount() {
        return datapointCount;
    }

    // Converts all data into a sequence of bytes for hashing
    public byte[] getDataBytes(){
        ArrayList<byte[]> allBytes = new ArrayList<>();
        for (DataPoint dp: dataPoints)
            allBytes.add(dp.getBytes());

        return ByteUtils.combineByteArrays(allBytes);
    }

    public String getString(){
        StringBuilder sb = new StringBuilder();

        for (DataPoint dp : dataPoints){
            sb.append(dp.getFormattedDataString());
        }
        return sb.toString();
    }

}
