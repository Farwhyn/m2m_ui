package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteSync {
    
    private static Connection con;
    private static boolean hasData = false;
    
    public ResultSet displayUsers() throws ClassNotFoundException, SQLException {
        if (con == null) {
            getConnection();
        }
        
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT fname, lname, pdate FROM patient");
        return res;
    }

    private void getConnection() throws ClassNotFoundException, SQLException {
        
        Class.forName("org.sqlite.JDBC"); //find the sqlite JDBC driver
        con = DriverManager.getConnection("jdbc:sqlite:patientDB"); //connect to the database
        initialise();
    }

    private void initialise() throws SQLException {
      
        if(!hasData) {
            hasData = true;
        }
        
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = 'patient'");
        if(!res.next()) {
            System.out.println("Initializing the patient table");
            
            //build the table
            Statement state2 = con.createStatement();
            state2.execute("CREATE TABLE patient(id integer," + "fName varchar(60)," + "lName varchar(60)," + "pdate varchar(60),"
                    + "primary key(id));");
            
            //insert some sample data
            PreparedStatement prep = con.prepareStatement("INSERT INTO patient values(?, ?, ?)");
            prep.setString(2, "James");
            prep.setString(3, "Zhou");
            prep.setString(4, "August 11, 2016");
            prep.execute();
            
            
            PreparedStatement prep2 = con.prepareStatement("INSERT INTO patient values(?, ?, ?)");
            prep2.setString(2, "Andrew");
            prep2.setString(3, "Yan");
            prep2.setString(4, "September 8, 2016");
            prep2.execute();
            
            PreparedStatement prep3 = con.prepareStatement("INSERT INTO patient values(?, ?, ?)");
            prep3.setString(2, "May");
            prep3.setString(3, "Liang");
            prep3.setString(4, "October 6, 2016");
            prep3.execute();
            
        }
        
    }
    
    public void addPatient(String firstname, String lastname, String date) throws ClassNotFoundException, SQLException {
        if(con == null) {
            getConnection();
        }
        
        PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?, ?, ?)");
        prep.setString(2, firstname);
        prep.setString(3, lastname);
        prep.setString(4, date);
        prep.execute();
    }


}