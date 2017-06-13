package main;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import main.Patient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import  main.SQLiteSync;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import com.fazecast.jSerialComm.SerialPort;

import graph.MainGraph;

/*
 * Class: Main
 * This class sets up the login and main pages to allow the user to interact with device.
 * The user has the ability to access patient records, as well as begin a new session with the device.
 */

public class Main extends Application{
    SerialPort chosenPort;
    private static MainGraph maing;
	
	Scene loginScreen, landing, sessions;
	public static void main(String[] args){
		launch(args);
	}
	
	
	@SuppressWarnings("unchecked")
    @Override
	public void start(Stage primaryStage) throws Exception {
		double columnWidth = 200;
		
		String user = "m2m";
		String pw = "foo";
		String blank = "";
		
		//Data hub
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(10,10,10,10));
		
		primaryStage.setTitle("Music to Movement");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("light_logo.png")));
		
		Text welcomeText = new Text("Welcome");
		welcomeText.setId("welcome-text");
		grid.add(welcomeText, 0, 0);
		
		Label userName = new Label("Username:");
		grid.add(userName, 0, 1);
		
		TextField userNameField = new TextField();
		grid.add(userNameField, 1, 1);
		
		Label password = new Label("Password:");
		grid.add(password, 0, 2);

		
		PasswordField passwordField = new PasswordField();
		grid.add(passwordField, 1, 2);
		
		Button login = new Button("Log In");
		grid.add(login, 1, 4);
		GridPane.setHalignment(login, HPos.RIGHT);
		login.setDefaultButton(true);
		
		Button help = new Button("?");
		grid.add(help, 1, 0);
		GridPane.setHalignment(help, HPos.RIGHT);
		
		Button quit = new Button("Quit");
		grid.add(quit, 0, 4);
		
		
		help.setOnAction(e -> Help.display("Music to Movement Help", "This is the help window."));
		login.setOnAction(e -> {
			if(passwordField.getText().equals(pw) && userNameField.getText().equals(user))
			{
				//message.setText(blank);
			    
			    //main = new MainGraph();
				primaryStage.setScene(landing);
			}
			else{
				LoginFail.display("Login Failed", "Incorrect Username and/or Password");
				
			}
			passwordField.setText(blank);

		});
		quit.setOnAction(e -> {
			primaryStage.close();
			
		});
		primaryStage.setOnCloseRequest(e-> {
			Platform.exit();
			System.exit(0);
		});

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
//////////////////////////////////////////////// MAIN LANDING PAGE //////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		BorderPane layout = new BorderPane();
		HBox menu = new HBox(10);
		HBox changeView = new HBox(10);
		AnchorPane display = new AnchorPane();
		HBox patientOption = new HBox(10);
		GridPane pData = new GridPane();
		SQLiteSync db = new SQLiteSync();
		
		AnchorPane.setTopAnchor(pData, 20.0);
		AnchorPane.setBottomAnchor(changeView, 40.0);
		AnchorPane.setRightAnchor(changeView, 5.0);
		AnchorPane.setRightAnchor(patientOption, 5.0);
		AnchorPane.setBottomAnchor(patientOption, 8.0);
		//gamemode.setAlignment(Pos.CENTER);
		
		menu.setAlignment(Pos.CENTER_RIGHT);
	
		Label patientID = new Label();
		Label patientData  = new Label ("Patient Data:");
		Label patientFirst = new Label ("First Name:\t");
		Label patientLast = new Label ("Last Name:\t");
		Label patientDate = new Label ("Last Session:\t");
		//if(fNameDisplay != NULL)
		Label firstData = new Label ();
		//firstData.textProperty().bind(rowData.getFirstName().textProperty());
		Label lastData = new Label ();
		Label dateData = new Label ();
		
		TableView<Patient> sTable = new TableView<>();
		TableView<Patient> pTable = new TableView<>();
		ObservableList<Sessions> sData;
		ObservableList<Patient> data;
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
////////////////////////////////////////////////SESSIONS TABLE //////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		sTable.setEditable(true);
		TableColumn<Sessions, String> vDate = new TableColumn<Sessions, String>("Session Date");
		TableColumn<Sessions, String> gType = new TableColumn<Sessions, String>("Game Type");
		
		//sTable.getColumns().setAll(vDate,gType);
		sTable.setPrefWidth(10);
		sTable.setPrefHeight(620);
		
		vDate.setMinWidth(columnWidth);
		vDate.setCellValueFactory(new PropertyValueFactory<Sessions,String>("visitDate"));
		
		gType.setMinWidth(columnWidth);
		gType.setCellValueFactory(new PropertyValueFactory<Sessions,String>("gameType"));
		
		//date.setMinWidth(columnWidth);
		//date.setCellValueFactory(new PropertyValueFactory<Patient,String>("visitDate"));
		
		sTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		//sData = getData(db);
		//sTable.setItems(sData);
		//TableColumn<>
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
//////////////////////////////////////////////// PATIENT TABLE //////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		pTable.setEditable(true);
		TableColumn<Patient, String> lastName = new TableColumn<Patient, String>("Last Name");
		TableColumn<Patient, String> firstName = new TableColumn<Patient, String>("First Name");
		//TableColumn<Patient, String> date = new TableColumn<Patient, String>("Date of Last Visit");
		
		
		pTable.getColumns().setAll(lastName,firstName);
		pTable.setPrefWidth(10);
		pTable.setPrefHeight(620);
		
		lastName.setMinWidth(columnWidth);
		lastName.setCellValueFactory(new PropertyValueFactory<Patient,String>("lastName"));
		
		firstName.setMinWidth(columnWidth);
		firstName.setCellValueFactory(new PropertyValueFactory<Patient,String>("firstName"));
		
		//date.setMinWidth(columnWidth);
		//date.setCellValueFactory(new PropertyValueFactory<Patient,String>("visitDate"));
		
		pTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		data = getData(db);
		pTable.setItems(data);

		pTable.setRowFactory( tv -> {
		    TableRow<Patient> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
		            Patient rowData = row.getItem();
		            patientID.setText( rowData.getID());
		            firstData.setText(rowData.getFirstName());
		            lastData.setText(rowData.getLastName());
		            dateData.setText(rowData.getVisitDate());
		        }
		    });
		    return row ;
		});
	
		menu.setPadding(new Insets(10,10,10,10));
		
		pData.setPadding(new Insets(10,10,10,10));
		pData.getColumnConstraints().add(new ColumnConstraints(100));
		pData.getColumnConstraints().add(new ColumnConstraints(100));
		
		
		
		Button toggleView = new Button("View Sessions...");
		Button returnPView = new Button("View Patients...");
		
		Button newPatient = new Button("New...");
		Button editPatient = new Button("Edit...");
		Button deletePatient = new Button ("Delete");
		
		Button helpAfter = new Button("?");
		Button logout = new Button("Log Out");
		
		Button freePlay = new Button("Free Play");
		Button gamemode1 = new Button("Game Mode 1");
		Button gamemode2 = new Button("Game Mode 2");
	
		helpAfter.setMaxSize(150, Double.MAX_VALUE);
		
		logout.setMaxSize(150, Double.MAX_VALUE);	
		
		freePlay.setMaxSize(150, Double.MAX_VALUE);
		gamemode1.setMaxSize(150, Double.MAX_VALUE);
		gamemode2.setMaxSize(150, Double.MAX_VALUE);
		
		pData.add(patientData, 0, 0);

		pData.add(patientFirst, 0, 1);
		pData.add(patientLast, 0, 2);
		pData.add(patientDate, 0, 3);

		pData.add(firstData, 1, 1);
		pData.add(lastData, 1, 2);
		pData.add(dateData, 1, 3);
		
		toggleView.setOnAction(e -> {
			newPatient.setDisable(true);
			deletePatient.setDisable(true);
			editPatient.setDisable(true);
		});
		
		
		freePlay.setOnAction(e->{
			//QUESTIONNAIRE TEST//
			if(!firstData.getText().equals(blank)){
				Questionnaire.display(firstData.getText(), lastData.getText(),"Pre-Session Questionnaire", "Fill this out before completing beginning your session.");
			}
		});
		
		//////HI OPENS GRAPH DONT DELETE ANDREW//////
		/*
		freePlay.setOnAction(e -> {
           // Graph.create();
		   // maing.show();
		    if(maing != null) {
		        maing.show();
		    }
		    else{
		        maing = new MainGraph();
		        maing.show();
		    }
        });*/
		
		helpAfter.setOnAction(e -> Help.display("Music to Movement Help", "This is the help window."));
		
		logout.setOnAction(e -> {
			primaryStage.setScene(loginScreen);
		});
		
		newPatient.setOnAction(e -> {
			AddPatient.display(this, db, pTable);	
		});
		
		deletePatient.setOnAction(e -> {
			try {
				db.deletePatient(patientID.getText());
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			firstData.setText(blank);
			lastData.setText(blank);
			dateData.setText(blank);
			
			pTable.setItems(getData(db));
			
			
		});
		menu.getChildren().addAll(freePlay, gamemode1, gamemode2, helpAfter, logout);
		patientOption.getChildren().addAll(newPatient, editPatient, deletePatient);
		changeView.getChildren().addAll(toggleView);
		display.getChildren().addAll(pData, patientOption, changeView);
		
		
		layout.setTop(menu);
		layout.setCenter(pTable);
		layout.setRight(display);
		
		landing = new Scene(layout, 1000 ,620);	
		loginScreen = new Scene(grid, 1000, 620);
		
		//loginScreen.getStylesheets().add(Main.class.getResource("theme.css").toExternalForm());
		//menuScreen.getStylesheets().add(Main.class.getResource("theme.css").toExternalForm());
		
		
		primaryStage.setScene(loginScreen);
		primaryStage.show();
	}
	
	
	ObservableList<Patient> getData(SQLiteSync db){
		List<Patient> list = new ArrayList<Patient>();
		
        ResultSet rs;
        
            
        try {
            rs = db.displayUsers();
            while(rs.next()) {
                System.out.println(rs.getString("fname") + " " + rs.getString("lname"));
                System.out.println(rs.getString("id"));
                list.add(new Patient(rs.getString("id"), rs.getString("lname"), rs.getString("fname"), rs.getString("sname")));
            }
        } catch (ClassNotFoundException e) {
           JOptionPane.showMessageDialog(null, "oops");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Woops");
        }
        
		ObservableList<Patient> data = FXCollections.observableList(list);
		return data;

	}
	
	//ObservableList<Sessions> getData(){
	
	//}
}
