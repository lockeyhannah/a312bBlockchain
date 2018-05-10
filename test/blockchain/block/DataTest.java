package blockchain.block;

import blockchain.block.data_points.DataPoint;
import blockchain.block.data_points.StorageContract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {

    ArrayList<DataPoint> dataPoints;
    Data data;
    DataPoint contract;

    @BeforeEach
    public void dataTest00(){
        dataPoints = new ArrayList<>();
        data = new Data(dataPoints);
        contract = new StorageContract
                ("chunk", "storage", "times up", 3, 2);
    }

    @Test
    public void dataTest01(){
        data.addData(contract);

        assertEquals(contract, data.getDataPoints().get(0));
    }

}