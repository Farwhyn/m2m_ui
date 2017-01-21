package main;

import javafx.beans.property.SimpleStringProperty;

/*
 * Class: Patient
 * This class sets up a new "Patient" with 4 properties.
 */

public class Patient{
    private	String patient_id;
    private SimpleStringProperty lastName;
    private SimpleStringProperty firstName;
    private SimpleStringProperty visitDate;
    
    public Patient(String id, String lName, String fName, String vDate ){
    	this.patient_id = id;
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.visitDate = new SimpleStringProperty(vDate);
        //this.id = id;
    }
    
    public String getID(){
        return this.patient_id;
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
        firstName.set(fName);
    }
    
    public String getVisitDate(){
        return visitDate.get();
    }
    public void setVisitDate(String vDate){ 
        visitDate.set(vDate);
    }
}