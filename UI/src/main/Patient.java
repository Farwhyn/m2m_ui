package main;

import javafx.beans.property.SimpleStringProperty;

public class Patient{
    private int id;
    private SimpleStringProperty lastName;
    private SimpleStringProperty firstName;
    private SimpleStringProperty visitDate;
    
    public Patient(int id, String lName, String fName, String vDate ){
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.visitDate = new SimpleStringProperty(vDate);
        this.id = id;
    }
    
    public int getID(){
        return id;
    }
    public String getLastName(){
        return lastName.get();
    }
    public void setLastName(String lName){  
        lastName.set(lName);
    }
    
    public String getFirstName(){
        return firstName.get();
    }
    public void setFirstName(String fName){ 
        lastName.set(fName);
    }
    
    public String getVisitDate(){
        return visitDate.get();
    }
    public void setVisitDate(String vDate){ 
        lastName.set(vDate);
    }
}