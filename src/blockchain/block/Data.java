package blockchain.block;
/*
 * This is the interface for all classes that is a part of data in blockchain.block
 *
 */

public class Data {

    StringBuilder data = new StringBuilder();

    public void addData(String text){
        data.append(text);
    }

    public String getDataString(){
        return data.toString();
    }

}
