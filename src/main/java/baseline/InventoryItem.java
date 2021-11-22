/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Quinn Gilbert
 */
package baseline;

import javafx.beans.property.SimpleStringProperty;

import java.util.HashSet;

public class InventoryItem {
    //InventoryItem will hold data for a single row in the table

    private static HashSet<String> serialSet = new HashSet<>();

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

    public boolean testSerial(){
        //test if meets format
        String s = serial.get();
        if(s.length()!=13) return false;
        if(!( (s.charAt(0)>='A'&&s.charAt(0)<='Z') || (s.charAt(0)>='a'&&s.charAt(0)<='z') )) return false;
        if(!(s.charAt(1)=='-'&&s.charAt(9)=='-'&&s.charAt(5)=='-')) return false;
        //check if the appropriate values are either numbers or digits
        for(int i=0;i<3;i++){
            int start = i*4+2;
            for(int j=start;j<start+3;j++){
                if(!( (s.charAt(j)<='9'&&s.charAt(j)>='0')|| (s.charAt(j)>='A'&&s.charAt(j)<='Z') || (s.charAt(j)>='a'&&s.charAt(j)<='z') )) return false;
            }
        }

        //test if the serial has already been used
        if(serialSet.contains(s)) return false;

        return true;
    }

    public boolean testValue(){
        String s = value.get();
        if(s.equals("")) return false;
        for(int i:s.toCharArray()){
            if(!( i<='9'&&i>='0' )) return false;
        }
        return Integer.parseInt(s)>0;
    }

    public boolean testName(){
        String s = name.get();
        return s.length()<=256 && s.length()>=2;
    }

    static void addSerial(String serial){
        serialSet.add(serial);
    }

    static void removeSerial(String serial){
        serialSet.remove(serial);
    }

    static void clearSerialSet(){
        serialSet.clear();
    }
}
