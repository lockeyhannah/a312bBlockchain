package blockchain.block;
/*
 * This is the interface for all classes that is a part of data in blockchain.block
 *
 */

import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.Savable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Data {

    private ArrayList<DataPoint> dataPoints = new ArrayList<>();

    public void addData(DataPoint dataPoint){
        dataPoints.add(dataPoint);
    }

    // Converts all data into a sequence of bytes for hashing
    public byte[] getByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        for(DataPoint element : dataPoints){
            out.writeUTF(element.getFormattedDataString());
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }



}
