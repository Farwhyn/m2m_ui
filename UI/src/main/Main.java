package main;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;




public class Main extends Application{

	
	Scene loginScreen, landing;
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		double columnWidth = 200;
		
		String user = "m2m";
		String pw = "UBCBEST";
		String blank = "";
		
		//Login Screen
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(10,10,10,10));
		
		primaryStage.setTitle("Music to Movement");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("M2M.png")));
		
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
				primaryStage.setScene(landing);
			}
			else{
				LoginFail.display("Login Failed", "Incorrect Username and/or Password");
				
			}
			passwordField.setText(blank);

		});
		quit.setOnAction(e -> primaryStage.close());
		
		//Main Menu
		BorderPane layout = new BorderPane();
		VBox menu = new VBox(10);
		GridPane gamemode = new GridPane();
		
		gamemode.setAlignment(Pos.CENTER);
		
		Label landingPage  = new Label ("Welcome to Music to Movement");
		
		TableView pTable = new TableView();
		ObservableList data;
		
		//Patient Table
		//pTable.setEditable(true);
		TableColumn lastName = new TableColumn("Last Name");
		TableColumn firstName = new TableColumn("First Name");
		TableColumn date = new TableColumn("Date of Last Visit");
		
		
		pTable.getColumns().setAll(lastName,firstName,date);
		pTable.setPrefWidth(10);
		pTable.setPrefHeight(620);
		
		lastName.setMinWidth(columnWidth);
		lastName.setCellValueFactory(new PropertyValueFactory<Patient,String>("lastName"));
		
		firstName.setMinWidth(columnWidth);
		firstName.setCellValueFactory(new PropertyValueFactory<Patient,String>("firstName"));
		
		date.setMinWidth(columnWidth);
		date.setCellValueFactory(new PropertyValueFactory<Patient,String>("visitDate"));
		
		pTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		data = getData();
		pTable.setItems(data);

		
		menu.setPadding(new Insets(10,10,10,10));
		
		
		layout.setLeft(menu);
		layout.setCenter(landingPage);

		//All Landing Buttons
		Button home = new Button("Home");
		Button newSession = new Button("New Session");
		Button viewPatients = new Button("Patient List");
		Button logout = new Button("Log Out");
		Button helpAfter = new Button("Help");
		
		home.setMaxSize(150, Double.MAX_VALUE);
		newSession.setMaxSize(150, Double.MAX_VALUE);
		viewPatients.setMaxSize(150, Double.MAX_VALUE);
		helpAfter.setMaxSize(150, Double.MAX_VALUE);
		
		logout.setMaxSize(150, Double.MAX_VALUE);
		
		Button freePlay = new Button("Free Play");
		Button gamemode1 = new Button("Game Mode 1");
		Button gamemode2 = new Button("Game Mode 2");
		
		freePlay.setMaxSize(150, Double.MAX_VALUE);
		gamemode1.setMaxSize(150, Double.MAX_VALUE);
		gamemode2.setMaxSize(150, Double.MAX_VALUE);
		
		gamemode.add(freePlay, 0, 0);
		gamemode.add(gamemode1, 0, 1);
		gamemode.add(gamemode2, 0, 2);
		
		home.setOnAction(e -> {
			layout.setCenter(landingPage);
		});
		
		newSession.setOnAction(e -> {
			layout.setCenter(gamemode);
		});
		
		viewPatients.setOnAction(e -> {
			layout.setCenter(pTable);
		});
		
		helpAfter.setOnAction(e -> Help.display("Music to Movement Help", "This is the help window."));
		
		logout.setOnAction(e -> {
			primaryStage.setScene(loginScreen);
		});
		
		
		menu.getChildren().addAll(home,newSession, viewPatients, helpAfter, logout);

		
		landing = new Scene(layout, 1000 ,620);
		
		
		loginScreen = new Scene(grid, 1000, 620);
		
		//loginScreen.getStylesheets().add(Main.class.getResource("theme.css").toExternalForm());
		//menuScreen.getStylesheets().add(Main.class.getResource("theme.css").toExternalForm());
		
		
		primaryStage.setScene(loginScreen);
		primaryStage.show();
	}
	
	private void printf(double width) {
		// TODO Auto-generated method stub
		
	}

	public class Patient{
		private SimpleStringProperty lastName;
		private SimpleStringProperty firstName;
		private SimpleStringProperty visitDate;
		
		public Patient(String lName, String fName, String vDate ){
			this.lastName = new SimpleStringProperty(lName);
			this.firstName = new SimpleStringProperty(fName);
			this.visitDate = new SimpleStringProperty(vDate);
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
	private ObservableList getData(){
		List list = new ArrayList();
		list.add(new Patient("Yan", "Andrew","20160830"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		list.add(new Patient("BEST","UBC","20170930"));
		ObservableList data = FXCollections.observableList(list);
		return data;

	}


}
