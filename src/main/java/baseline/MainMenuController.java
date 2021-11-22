/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Quinn Gilbert
 */
package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        if(!newItem.testName()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Name", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if(!newItem.testSerial()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Serial", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if(!newItem.testValue()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Value", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        InventoryItem.addSerial(newItem.getSerial());
        data.add(newItem);
        table.setItems(data);
    }

    @FXML
    void remove(ActionEvent event) {
        InventoryItem.removeSerial(table.getSelectionModel().getSelectedItem().getSerial());
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
        InventoryItem.clearSerialSet();
    }

    @FXML
    void saveTable(ActionEvent event) {
        //Open file
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter HTML = new FileChooser.ExtensionFilter("HTML","*.html");
        FileChooser.ExtensionFilter JSON = new FileChooser.ExtensionFilter("JSON","*.json");
        FileChooser.ExtensionFilter TSV = new FileChooser.ExtensionFilter("TSV","*.txt");
        fileChooser.getExtensionFilters().addAll(HTML,JSON,TSV);
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);

        System.out.println(getExtension(file));
        if(getExtension(file).equals("txt")){
            saveTSV(file);
        }
        if(getExtension(file).equals("html")){
            saveHTML(file);
        }
        if(getExtension(file).equals("json")){
            saveJSON(file);
        }
        //get string with properly formatted data
        //save data to file
    }

    String getExtension(File file){
        String filename = file.getName();
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    void saveTSV(File file){
        StringBuilder listData=new StringBuilder("SerialNumber\tName\tValue\n");
        for(InventoryItem i : data){
            listData.append(i.getSerial()+"\t"+i.getName()+"\t"+i.getValue()+"\n");
        }

        if(file!=null){
            try{
                PrintWriter out = new PrintWriter(file);
                out.println(listData);
                out.close();
            } catch(IOException ex){
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void saveHTML(File file){
        StringBuilder listData=new StringBuilder("<!DOCTYPE html><html><body><table>\n<tr><th>Serial</th><th>Name</th><th>Value</th></tr>\n");
        for(InventoryItem i : data){
            listData.append("<tr><td>"+i.getSerial()+"</td><td>"+i.getName()+"</td><td>"+i.getValue()+"</td></tr>\n");
        }
        listData.append("</table></body></html>");
        if(file!=null){
            try{
                PrintWriter out = new PrintWriter(file);
                out.println(listData);
                out.close();
            } catch(IOException ex){
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void saveJSON(File file){

        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> serial = new ArrayList<>();
        ArrayList<String> value = new ArrayList<>();
        for(InventoryItem i : data){
            name.add(i.getName());
            serial.add(i.getSerial());
            value.add(i.getValue());
        }
        StringBuilder listData=new StringBuilder("{");
        listData.append("\n\"name\":[");
        for(int i=0;i<name.size();i++){
            listData.append("\""+name.get(i)+"\"");
            if(i!=name.size()-1) listData.append(",");
        }
        listData.append("],\n\"serial\":[");
        for(int i=0;i<serial.size();i++){
            listData.append("\""+serial.get(i)+"\"");
            if(i!=serial.size()-1) listData.append(",");
        }
        listData.append("],\n\"value\":[");
        for(int i=0;i<value.size();i++){
            listData.append("\""+value.get(i)+"\"");
            if(i!=value.size()-1) listData.append(",");
        }
        listData.append("]\n}");
        if(file!=null){
            try{
                PrintWriter out = new PrintWriter(file);
                out.println(listData);
                out.close();
            } catch(IOException ex){
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void loadTable(ActionEvent event) {
        //open file
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("List Files:","*.html","*.json","*.txt");
//        FileChooser.ExtensionFilter JSON = new FileChooser.ExtensionFilter("JSON","*.json");
//        FileChooser.ExtensionFilter TSV = new FileChooser.ExtensionFilter("TSV","*.txt");
        fileChooser.getExtensionFilters().addAll(ext);
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);

        if(getExtension(file).equals("txt")){
            loadTSV(file);
        }
        if(getExtension(file).equals("html")){
            loadHTML(file);
        }
        if(getExtension(file).equals("json")){
            loadJSON(file);
        }
        //read in data from file
        //update list with data
        //update table
        //Update serial set with all values
    }

    void loadTSV(File file){
        if(file!=null) {
            data.clear();
            try {
                BufferedReader in = new BufferedReader(new FileReader(file));
                in.readLine();
                while (in.ready()) {
                    StringTokenizer st = new StringTokenizer(in.readLine());
                    String serial = st.nextToken();
                    String name = st.nextToken();
                    String value = st.nextToken();
                    data.add(new InventoryItem(name,serial,value));
                }
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            table.setItems(data);
        }
    }

    void loadHTML(File file){
        if(file!=null) {
            data.clear();
            try {
                BufferedReader in = new BufferedReader(new FileReader(file));
                in.readLine();
                in.readLine();
                while (in.ready()) {
                    String line = in.readLine();
                    String[] values = new String[3];
                    values[0] = "";
                    values[1] = "";
                    values[2] = "";
                    int index=0;
                    boolean write = false;
                    for(char i:line.toCharArray()){
                        if(i=='<'){
                            write=false;
                            index++;
                        }
                        if(write){
                            values[(index-1)/2]+=i;
                        }
                        if(i=='>'){
                            write=true;
                        }
                    }
                    data.add(new InventoryItem(values[1],values[0],values[2]));
                }
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            table.setItems(data);
        }
    }

    void loadJSON(File file){
        if(file!=null) {
            data.clear();
            try {
                BufferedReader in = new BufferedReader(new FileReader(file));
                in.readLine();
                String namesString = in.readLine();
                String serialString = in.readLine();
                String valueString = in.readLine();
                namesString = namesString.substring(8,namesString.length()-2);
                serialString = serialString.substring(10,serialString.length()-2);
                valueString = valueString.substring(9,valueString.length()-1);
                String[] nameList = namesString.split(",");
                String[] serialList = serialString.split(",");
                String[] valueList = valueString.split(",");

//                ArrayList<String> name = new ArrayList<>();
//                ArrayList<String> serial = new ArrayList<>();
//                ArrayList<String> value = new ArrayList<>();

                for(int i=0;i<nameList.length;i++){
                    data.add(new InventoryItem(nameList[i].substring(1,nameList[i].length()-1),serialList[i].substring(1,serialList[i].length()-1),valueList[i].substring(1,valueList[i].length()-1)));
                }
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            table.setItems(data);
        }
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

        //functions to edit values
        table.setEditable(true);
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<InventoryItem,String>>(){
            @Override
            //validate name on edit
            public void handle(TableColumn.CellEditEvent<InventoryItem,String> event){
                String oldValue = event.getOldValue();
                InventoryItem item = event.getRowValue();
                item.setName(event.getNewValue());
                if(!item.testName()){
                    item.setName(oldValue);
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Name", ButtonType.OK);
                    alert.showAndWait();
                    table.getColumns().get(0).setVisible(false);
                    table.getColumns().get(0).setVisible(true);
                    return;
                }
            }
        });
        serial.setCellFactory(TextFieldTableCell.forTableColumn());
        serial.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<InventoryItem,String>>(){
            @Override
            //validate serial on edit
            public void handle(TableColumn.CellEditEvent<InventoryItem,String> event){
                String oldValue = event.getOldValue();
                InventoryItem item = event.getRowValue();
                item.setSerial(event.getNewValue());
                if(!item.testSerial()){
                    item.setSerial(oldValue);
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Serial", ButtonType.OK);
                    alert.showAndWait();
                    table.getColumns().get(1).setVisible(false);
                    table.getColumns().get(1).setVisible(true);
                    return;
                }
                InventoryItem.removeSerial(oldValue);
                InventoryItem.addSerial(item.getSerial());
            }
        });
        value.setCellFactory(TextFieldTableCell.forTableColumn());
        value.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<InventoryItem,String>>(){
            @Override
            //validate value on edit
            public void handle(TableColumn.CellEditEvent<InventoryItem,String> event){
                String oldValue = event.getOldValue();
                InventoryItem item = event.getRowValue();
                item.setValue(event.getNewValue());
                if(!item.testValue()){
                    item.setValue(oldValue);
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Value", ButtonType.OK);
                    alert.showAndWait();
                    table.getColumns().get(2).setVisible(false);
                    table.getColumns().get(2).setVisible(true);
                    return;
                }
            }
        });

        data = FXCollections.observableArrayList(
                new InventoryItem("name1","serial1","1"),
                new InventoryItem("name2","serial2","2"),
                new InventoryItem("name3","serial3","3")
        );
        table.setItems(data);
    }

}
