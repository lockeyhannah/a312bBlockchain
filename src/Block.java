import block.Data;
import java.util.Date;

public class Block {

    //hej
    private Data data; //dataen fra blocken


    public Block(String previousHash) {
        this.data.previousHash = previousHash;
        this.data.timeStamp = new Date().getTime();
        this.data.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedHash = StringUtil.applySha256(
                data.previousHash + Long.toString(data.timeStamp) + Integer.toString(data.nonce) + data.getData());
        return calculatedHash;
    }

    public void mineBlock(int difficulty){
        //Laver en String med difficulty 0:
        String target = new String(new char[difficulty]).replace('\0', '0');

        //Dette while loop siger: imen at hashens første x tal IKKE er lige så mange 0'er
        //som difficultyen siger så skal den prøve igen med en ny nonce.
        while (!data.hash.substring(0, difficulty).equals(target)) {
            data.nonce++;
            data.hash = calculateHash();
        }
        System.out.println("Blocken er lavet: " + data.hash);
    }

}
