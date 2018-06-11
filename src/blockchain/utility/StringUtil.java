package blockchain.utility;

public abstract class StringUtil {

    public static String line(int len){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < len; i++){
            sb.append("-");
        }
        sb.append("\n");
        return sb.toString();
    }

}
