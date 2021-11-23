package baseline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

    @Test
    //Tests InventoryItem validity checks
    public void tests(){
        InventoryItem item = new InventoryItem("name","A-XXX-XXX-XXX","1000");
        assertTrue(item.testName());
        assertTrue(item.testSerial());
        assertTrue(item.testValue());
        item = new InventoryItem("n","1-XXX-XXX-XXX","0");
        assertFalse(item.testName());
        assertFalse(item.testSerial());
        assertFalse(item.testValue());
    }

}