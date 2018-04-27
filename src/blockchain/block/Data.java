package blockchain.block;
/*
 * This is the interface for all classes that is a part of data in blockchain.block
 *
 */

import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.Savable;

import java.io.*;
import java.util.ArrayList;

public class Data implements Savable{

    private ArrayList<DataPoint> dataPoints = new ArrayList<>();

    public void addData(DataPoint dataPoint){
        dataPoints.add(dataPoint);
    }

    // Converts all data into a sequence of bytes for hashing
    @Override
    public byte[] getByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        for(DataPoint element : dataPoints){
            try {
                out.writeUTF(element.getFormattedDataString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }
    @Override
    public Savable getInstanceFromBytes(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream in = new DataInputStream(bais);
        while (in.available() > 0) {
            String element = in.readUTF();
            System.out.println(element);
        } //Todo: Lige nu kommer den bare som en String. Dette skal laves om til Datapoints. How do?
        return null;
    }
    @Override
    public int getByteSize(){
            return getByteArray().length;
    }
}
