package block;
/*
 * This class contains the data stored in a block
 *
 */

public class Data {

    StringBuilder data = new StringBuilder();

    public void addData(String text){
        data.append(text);
    }

    public String getData(){
        return data.toString();
    }



}
