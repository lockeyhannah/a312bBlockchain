package blockchain.block.data_points;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageContractTest {

    private static final String userOneID = "1";
    private static final String userTwoID = "2";
    private static final String fileId = "file.format";
    private static final double reward = 0.35;
    private static long expiration;
    private static StorageContract expiredContract, activeContract;

    @BeforeAll
    static void setup(){
        // Create contract that is already expired
        expiredContract = new StorageContract(fileId, userOneID, userTwoID, 0, reward);
        // Add contract that expires in one hour
        activeContract = new StorageContract(fileId, userOneID, userTwoID, System.currentTimeMillis() + 1000 * 60 * 60, reward);
    }

    @Test
    void getterTest(){
        assertEquals(fileId, expiredContract.getFileId());
        assertEquals(userOneID, expiredContract.getFileOwnerID());
        assertEquals(userTwoID, expiredContract.getStorageUnitID());
        assertEquals(expiration, expiredContract.getContractTerminationTime());
    }

    @Test
    void containsIdTest(){
        assertTrue(expiredContract.containsIdentifier(userOneID));
        assertTrue(expiredContract.containsIdentifier(userTwoID));
        assertTrue(expiredContract.containsIdentifier(fileId));

        assertFalse(expiredContract.containsIdentifier("Unrelated id"));
    }

    @Test
    void expiredContractBalanceChangeTest(){
        assertEquals(-reward, expiredContract.getBalanceChange(userOneID));
        assertEquals(reward, expiredContract.getBalanceChange(userTwoID));

        assertEquals(0, expiredContract.getBalanceChange("Unrelated user"));
    }

    @Test
    void activeContractBalanceChangeTest(){
        assertEquals(-reward, activeContract.getBalanceChange(userOneID));
        assertEquals(0, activeContract.getBalanceChange(userTwoID));

        assertEquals(0, activeContract.getBalanceChange("Unrelated user"));
    }


}
