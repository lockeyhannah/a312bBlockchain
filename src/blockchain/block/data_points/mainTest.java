package blockchain.block.data_points;

public class mainTest {
    public static void main(String[] args) {
        Transaction trans1 = new Transaction();
        SmartContract smart1 = new SmartContract();

        trans1.addData("hej trans1");
        smart1.addData("hej smart1");
        trans1.addData("hoowee");

        System.out.println(trans1.getDataString());
        System.out.println(smart1.getDataString());
    }
}
