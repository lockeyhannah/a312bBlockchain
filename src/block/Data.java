package block;
/*
 * This class contains the data stored in a block
 *
 */

public class Data {

    StringBuilder data = new StringBuilder();
    public byte[] hash; //hashen fra nuværende block
    public String previousHash; //hashen fra en tidligere block
    public long timeStamp;
    public int nonce;

    public void addData(String text){
        data.append(text);
    }

    public String getData(){
        return data.toString();
    }



}
