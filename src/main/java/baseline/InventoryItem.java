/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Quinn Gilbert
 */
package baseline;

import javafx.beans.property.SimpleStringProperty;

public class InventoryItem {
    //InventoryItem will hold data for a single row in the table

    private final SimpleStringProperty name;
    private final SimpleStringProperty serial;
    private final SimpleStringProperty value;

    InventoryItem(String name, String serial, String cost){
        this.name = new SimpleStringProperty(name);
        this.serial = new SimpleStringProperty(serial);
        this.value = new SimpleStringProperty(cost);
    }

    //getters and setters for all major variables

    public String getName(){
        return name.get();
    }
    public void setName(String name){
        this.name.set(name);
    }

    public String getSerial(){
        return serial.get();
    }
    public void setSerial(String serial){
        this.serial.set(serial);
    }

    public String getValue(){
        return value.get();
    }
    public void setValue(String value){
        this.value.set(value);
    }

}
