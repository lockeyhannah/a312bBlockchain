package blockchain.block;
/*
 * This is the interface for all classes that is a part of data in blockchain.block
 *
 */

import blockchain.block.data_points.DataPoint;

import java.io.*;
import java.util.ArrayList;

public class Data implements Serializable{

    private ArrayList<DataPoint> dataPoints;
    private int datapointCount;

    public Data(ArrayList<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
        datapointCount = dataPoints.size();
    }

    public void addData(DataPoint dataPoint){
        dataPoints.add(dataPoint);
        datapointCount++;
    }

    public ArrayList<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public int getDatapointCount() {
        return datapointCount;
    }

    // Converts all data into a sequence of bytes for hashing
    public byte[] getDataBytes(){
        ArrayList<byte[]> allBytes;
        for (DataPoint dp: dataPoints) {
            //dp.getBytes();
        }// TODO: 29-04-2018 Implement hash byte functionality
        return new byte[0];
    }

    public String getString(){
        StringBuilder sb = new StringBuilder();

        for (DataPoint dp : dataPoints){
            sb.append(dp.getFormattedDataString());
        }
        return sb.toString();
    }

}
