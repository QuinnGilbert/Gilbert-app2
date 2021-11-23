package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuControllerTest {
    @Test
    //saves observable list, changes it, and reloads it
    //then tests if it's the same
    //for all 3 save and load methods
    public void testSaveAndLoad(){
        MainMenuController controller = new MainMenuController();
        String absolutePath = new File("").getAbsolutePath();

        //test saving and loading to HTML
        ObservableList<InventoryItem> data = FXCollections.observableArrayList(
                new InventoryItem("name1","A-XXX-XXX-XXX","1")
        );
        File testFile = new File(absolutePath+"/src/test/resources/testFile.html");
        controller.saveHTML(testFile,data);
        data = FXCollections.observableArrayList(
                new InventoryItem("name1","A-XXX-XXX-XX1","2")
        );
        controller.loadHTML(testFile,data);
        assertTrue(data.get(0).getName().equals("name1")&&data.get(0).getSerial().equals("A-XXX-XXX-XXX")&&data.get(0).getValue().equals("1"));

        //test saving and loading to JSON
        data = FXCollections.observableArrayList(
                new InventoryItem("name1","A-XXX-XXX-XXX","1")
        );
        testFile = new File(absolutePath+"/src/test/resources/testFile.JSON");
        controller.saveJSON(testFile,data);
        data = FXCollections.observableArrayList(
                new InventoryItem("name1","A-XXX-XXX-XX1","2")
        );
        controller.loadJSON(testFile,data);
        assertTrue(data.get(0).getName().equals("name1")&&data.get(0).getSerial().equals("A-XXX-XXX-XXX")&&data.get(0).getValue().equals("1"));

        //test saving and loading to TSV
        data = FXCollections.observableArrayList(
                new InventoryItem("name1","A-XXX-XXX-XXX","1")
        );
        testFile = new File(absolutePath+"/src/test/resources/testFile.TSV");
        controller.saveTSV(testFile,data);
        data = FXCollections.observableArrayList(
                new InventoryItem("name1","A-XXX-XXX-XX1","2")
        );
        controller.loadTSV(testFile,data);
        assertTrue(data.get(0).getName().equals("name1")&&data.get(0).getSerial().equals("A-XXX-XXX-XXX")&&data.get(0).getValue().equals("1"));

    }

}