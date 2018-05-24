package blockchain.block;

import blockchain.block.data_points.DataPoint;

import java.util.ArrayList;

class DataTest {

    ArrayList<DataPoint> dataPoints;
    Data data, data2;
    DataPoint contract;
    byte[] byteData1, byteData2;
/*
    @BeforeEach
    public void dataTest00(){
        dataPoints = new ArrayList<>();
        data = new Data(dataPoints);
        contract = new FileOverview("test")
                ("chunk", "storage",
                        "times up", 3, 2);
        data.addData(contract);

        data2 = data;
    }

    @Test
    public void dataTest01(){
        assertEquals(contract, data.getDataPoints().get(0));
    }

    @Test
    public void dataTest02(){
        byteData1 = data.getDataBytes();
        byteData2 = data2.getDataBytes();

        assertTrue(Arrays.compare(byteData1, byteData2) == 0);
    }*/
    // TODO: 13-05-2018 reiplement

}