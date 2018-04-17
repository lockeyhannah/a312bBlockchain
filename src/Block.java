import java.util.Date;

public class Block {

    public String hash; //hashen fra nuværende block
    public String previousHash; //hashen fra en tidligere block
    private String data; //dataen fra blocken
    private long timeStamp;
    private int nonce;
    private int storFaderAlfons = 69;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();

    }

    public String calculateHash() {
        String calculatedHash = StringUtil.applySha256(
                previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data);
        return calculatedHash;
    }

    public void mineBlock(int difficulty){
        //Laver en String med difficulty 0:
        String target = new String(new char[difficulty]).replace('\0', '0');

        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Blocken er lavet: " + hash);
    }

}
