package blockchain.ledger_file;

import static junit.framework.TestCase.assertEquals;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.file.Paths;

class WriterTest {


    @Test
    public void fileAppendTest(){
        LedgerWriter ledgerWriter = new LedgerWriter(Paths.get("ledger.dat"));
        byte[] encodedTest = ByteUtils.longToBytes(15978645);
        ledgerWriter.writeToFile(encodedTest, 8);

        LedgerReader ledgerReader = new LedgerReader(Paths.get("ledger.dat"));
        byte[] readBytes = ledgerReader.readBytes(8);

        System.out.println(ByteUtils.bytesToLong(readBytes));


    }


}