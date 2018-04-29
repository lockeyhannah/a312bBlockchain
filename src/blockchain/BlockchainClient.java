package blockchain;


public class BlockchainClient {


    public static void main(String[] args) {
       /* Data data = new Data();
        byte[] test = {12, 13, 15};
        byte[] extended = ByteUtils.extendByteArray(test, 8);
        for(int i = 0; i < extended.length; i++){
            System.out.print("," + extended[i]);
        }*/

        /*
        // TODO: 21-04-2018 : Add difficulty calculation
        BigInteger tempDifficulty = new BigInteger("2").pow(245);
        ArrayList<Block> blocks = new ArrayList<>();

        blocks.add(BlockGenerator.generateBlock(data, tempDifficulty.toByteArray(), "".getBytes()));
        System.out.println();
        blocks.get(0).printBlock();

        for (int i = 1; i < 20; i++) {
            tempDifficulty = new BigInteger("2").pow(245 - i);
            blocks.add(BlockGenerator.generateBlock(data, tempDifficulty.toByteArray(), Hasher.applySHA(blocks.get(i - 1).getHeader().getByteArray())));
            System.out.println();
            blocks.get(i).getHeader().setBlockId(blocks.get(i - 1).getHeader().getBlockId() + 1);
        }
*/
    }

}
