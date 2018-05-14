package blockchain.block.mining;

import blockchain.block.Block;
import blockchain.block.Data;
import blockchain.block.Header;
import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.DataPointUID;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Builder for the block class
 * When built a header is generated and the block is mined according to its difficulty
 */

public class BlockBuilder{

    Data data;
    Header header;
    Header previousHeader;
    long blockNumber;

    // Initiates the builder potentially based on a previous header
    public BlockBuilder(Header previousHeader){
        this.previousHeader = previousHeader;

        if(previousHeader != null)
            blockNumber = previousHeader.getBlockId() - 1;
        else
            blockNumber = 0;

        data = new Data();
    }

    // Generates a new header with values based on the previous header
    // Generates standard values if the previous header is null
    private void generateHeader(){
        String timeStamp = BlockMiner.generateTimeStamp();

        byte[] dataHash = Hasher.applySHA(data.getDataBytes()); // Hash data
        byte[] nonce = new byte[]{0};
        byte[] difficulty;
        byte[] prevHeaderHash;

        if(previousHeader == null) { // Set standard values if no previous header is given
            dataHash = new byte[32];
            difficulty = BigInteger.TWO.pow(238).toByteArray();
            prevHeaderHash = new byte[32];
        }else{ // Otherwise set values based on previous block
            prevHeaderHash = Hasher.applySHA(previousHeader.getBytes());
            difficulty = previousHeader.getDifficultyTarget(); // todo Add difficulty calculation
        }

        header = new Header(blockNumber, prevHeaderHash, dataHash, nonce, difficulty, timeStamp);
    }

    // Adds a data point to the block and return a unique identifier for this data point
    public DataPointUID addData(DataPoint dp){
        int dataPointNumber = data.addData(dp);
        return new DataPointUID(blockNumber, dataPointNumber);
    }


    // Builds the block, generating the header and mining the block
    public Block build(){
        generateHeader();
        return BlockMiner.mineBlock(header, data);

    }




}
