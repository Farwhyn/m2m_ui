package main;

import javafx.beans.property.SimpleStringProperty;

public class Sessions {
		private String session_id;
		private	SimpleStringProperty patient_id;
	    private SimpleStringProperty lastName;
	    private SimpleStringProperty firstName;
	    private SimpleStringProperty visitDate;
	    private SimpleStringProperty gameType;
	    
	    public Sessions(String id, String pid, String lName, String fName, String vDate, String gType ){
	    	this.session_id = id;
	    	this.patient_id = new SimpleStringProperty(pid);
	        this.lastName = new SimpleStringProperty(lName);
	        this.firstName = new SimpleStringProperty(fName);
	        this.visitDate = new SimpleStringProperty(vDate);
	        this.gameType = new SimpleStringProperty(gType);
	        //this.id = id;
	    }
	    
	    public String getID(){
	        return this.session_id;
	    }
	    public String getPID(){
	    	return patient_id.get();
	    }
	    public void setPID(String pid){  
	        patient_id.set(pid);
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
	    public String getGType(){
	    	return gameType.get();
	    }
	    public void setType(String gType){  
	        gameType.set(gType);
	    }
}
