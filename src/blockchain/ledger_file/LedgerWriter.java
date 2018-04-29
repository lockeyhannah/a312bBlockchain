package blockchain.ledger_file;

import blockchain.block.Block;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static blockchain.ledger_file.ByteUtils.combineByteArrays;


public class LedgerWriter {
    private File fil = new File("block.txt");

    public void writeBlockToFile(Block block) throws IOException {
        Integer blockLength = block.getByteSize()+1;
        byte lengthArray = blockLength.byteValue();
        byte[] byteValueofBlock=new byte[1];
        byteValueofBlock[0] = lengthArray;
        byteValueofBlock = combineByteArrays(byteValueofBlock,block.getByteArray());



        FileOutputStream fos = new FileOutputStream(fil,true);
        fos.write(byteValueofBlock, 0,blockLength);
        fos.flush(); //Todo: Tror ikke er nødvendig, men better safe than sorry.
        fos.close();
    }

}
