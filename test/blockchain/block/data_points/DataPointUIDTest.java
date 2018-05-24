package blockchain.block.data_points;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataPointUIDTest {

    DataPointUID dpUID;

    @BeforeEach
    public void setup(){
        dpUID = new DataPointUID(234, 789);
    }

    @Test
    public void sameDataPointUIDTest(){
        assertEquals(234, dpUID.getBlockNumber());
        assertEquals(789, dpUID.getDataPointNumber());
    }

}