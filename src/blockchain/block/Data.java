package blockchain.block;
/*
 * This is the interface for all classes that is a part of data in blockchain.block
 *
 */

import blockchain.block.data_points.Savable;

import java.util.ArrayList;

public class Data {

    private ArrayList<Savable> savables = new ArrayList<>();

    public void addData(Savable savable){
        savables.add(savable);
    }

    // Converts all data into a sequence of bytes for hashing
    public byte[] getByteArray(){
        // TODO: 21-04-2018 : Temporary behaviour for testing - Add actual functionality
        return savables.get(0).getByteArray();
    }



}
