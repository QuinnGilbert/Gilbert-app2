/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Quinn Gilbert
 */
package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import javax.swing.*;

public class MainMenuController {

    @FXML
    private TextField searchName;

    @FXML
    private TextField searchSerial;

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
    private TableView<InventoryItem> table;

    private ObservableList<InventoryItem> data;

    @FXML
    void addData(ActionEvent event) {
        //add data contained in textfields to tableview
        InventoryItem newItem = new InventoryItem(addName.getText(),addSerial.getText(),addValue.getText());
        //test if newItem is valid (and output appropriate messages if isn't
        data.add(newItem);
        table.setItems(data);
    }

    @FXML
    void remove(ActionEvent event) {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
    }

    @FXML
    void tableKeyPressed(KeyEvent event) {
        if(event.getCode()== KeyCode.DELETE){
            remove(new ActionEvent());
        }
    }

    @FXML
    void clearAll(ActionEvent event) {
        //remove all values from list
        data.clear();
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
    void updateSearchName(Event event) {
        //keep all values stored in list
        //make new list with part of the searched name
        ObservableList<InventoryItem> temp = FXCollections.observableArrayList();
        for(InventoryItem i:data){
            if(i.getName().contains(searchName.getText())){
                temp.add(i);
            }
        }
        //add that list
        table.setItems(temp);
    }

    @FXML
    void updateSearchSerial(Event event) {
        //same implementation as SearchName but with serial number
        ObservableList<InventoryItem> temp = FXCollections.observableArrayList();
        for(InventoryItem i:data){
            if(i.getSerial().contains(searchSerial.getText())){
                temp.add(i);
            }
        }
        //add that list
        table.setItems(temp);
    }

    @FXML
    void initialize(){
        //load a new table with properly constructed table columns
        TableColumn name = new TableColumn("Name");
        TableColumn serial = new TableColumn("Serial #");
        TableColumn value = new TableColumn("Value");
        table.getColumns().addAll(name,serial,value);

        name.setCellValueFactory(new PropertyValueFactory<InventoryItem,String>("name"));
        serial.setCellValueFactory(new PropertyValueFactory<InventoryItem,String>("serial"));
        value.setCellValueFactory(new PropertyValueFactory<InventoryItem,String>("value"));

        //add functions to change values

        data = FXCollections.observableArrayList(
                new InventoryItem("name1","serial1","1"),
                new InventoryItem("name2","serial2","2"),
                new InventoryItem("name3","serial3","3")
        );
        table.setItems(data);
    }

}
