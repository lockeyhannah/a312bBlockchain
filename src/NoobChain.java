import java.util.ArrayList;

public class NoobChain {

    public static ArrayList<Block> blockchain = new ArrayList<>();

    //Hvor mange nuller der skal være
    public static int difficulty = 3;

    public static void main(String[] args) {

        String info = new String("hej");

        blockchain.add(new Block("first block", "0"));
        System.out.println("Igang med at lave block 1...");
        blockchain.get(0).mineBlock(difficulty);

        for (int i = 1; i < 5; i++){
            blockchain.add(new Block(info, blockchain.get(blockchain.size()-1).hash));
            System.out.println("Igang med at lave block " + (i+1) + "..." );
            blockchain.get(i).mineBlock(difficulty);
        }

        System.out.println("\nBlockchainen er ok: " + isChainValid() + "\n");

        for (int i = 0; i < blockchain.size(); i++){
            System.out.println("Hash for block " + i + ": " + blockchain.get(i).hash);
        }
    }

    //Tjekker om blockchainens data matcher hinanden
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        //Laver en ny String med difficulty 0
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //Dette forloop tjekker de tre exceptions for hver block.
        for (int i = 1; i < blockchain.size(); i++){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            
            //Disse if statements kan laves til exceptions.
            if(!currentBlock.hash.equals(currentBlock.calculateHash())){
                String msg = "Nuværende Hashes er ikke ens.";
                throw new IllegalArgumentException(msg);
            }

            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                String msg = "Tidligere Hashes er ikke ens.";
                throw new IllegalArgumentException(msg);
            }

            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)){
                String msg = "Denne block er ikke lavet.";
                throw new IllegalArgumentException(msg);
            }
        }
        return true;
    }
}
