package blockchain.block;
/*
 * This is the interface for all classes that is a part of data in blockchain.block
 *
 */

import blockchain.block.data_points.DataPoint;

import java.util.ArrayList;

public class Data {

    ArrayList<DataPoint> dataPoints = new ArrayList<>();

    public void addData(DataPoint dataPoint){
        dataPoints.add(dataPoint);
    }

    // Converts all data into a sequence of bytes for hashing
    public byte[] getByteArray(){
        // TODO: 21-04-2018 : Temporary behaviour for testing - Add actual functionality
        return dataPoints.get(0).getByteArray();
    }



}
