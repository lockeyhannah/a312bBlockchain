package blockchain.block.data_points;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

class FileOverviewTest {

    FileOverview overview, overview2;
    ArrayList<StorageContract> contracts;
    StorageContract c;
    byte[] byteOverview, byteOverview2;

    @BeforeEach
    public void fileOverviewTest00(){
        c = new StorageContract("5", "100", "monday", 3, 5);
        contracts = new ArrayList<>();

        contracts.add(c);
        contracts.add(c);
        contracts.add(c);

        overview = new FileOverview("owner", "fileID", contracts);
        overview2 = overview;
    }

    @Test
    public void fileOverviewTest01(){
        assertTrue(overview.getFormattedDataString().contains("100"));
        assertTrue(overview.getFormattedDataString().contains("monday"));
        assertTrue(overview.getFormattedDataString().contains("fileID"));
    }

    @Test
    public void fileOverviewTest02(){
        byteOverview = overview.getBytes();
        byteOverview2 = overview2.getBytes();
        assertTrue(Arrays.compare(byteOverview, byteOverview2) == 0);
    }

}