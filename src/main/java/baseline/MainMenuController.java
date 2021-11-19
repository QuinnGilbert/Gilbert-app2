package baseline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainMenuController {

    @FXML
    private TextField SearchName;

    @FXML
    private TextField SearchSerial;

    @FXML
    private Button addButton;

    @FXML
    private TextField addName;

    @FXML
    private TextField addSerial;

    @FXML
    private TextField addValue;

    @FXML
    private Button clearButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<?> table;

    @FXML
    void addData(ActionEvent event) {
        //add data contained in textfields to tableview
    }

    @FXML
    void clearAll(ActionEvent event) {
        //remove all values from list
        //update table
    }

    @FXML
    void loadTable(ActionEvent event) {
        //open file
        //read in data from file
        //update list with data
        //update table
    }

    @FXML
    void saveTable(ActionEvent event) {
        //Open file
        //get string with properly formatted data
        //save data to file
    }

    @FXML
    void updateSearchName(ActionEvent event) {
        //keep all values stored in list
        //make new list with part of the searched name
        //add that list
    }

    @FXML
    void updateSearchSerial(ActionEvent event) {
        //same implementation as SearchName but with serial number
    }

    @FXML
    void initialize(){
        //load a new table with properly constructed table columns
    }

}
