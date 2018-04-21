package blockchain.block;
/*
 * This class contains the data stored in a blockchain.block
 *
 */

public class Data {

    StringBuilder data = new StringBuilder();
    public long timeStamp;
    public int nonce;

    public void addData(String text){
        data.append(text);
    }

    public String getDataString(){
        return data.toString();
    }



}
