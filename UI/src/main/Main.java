package main;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Main.Patient;
import main.SQLiteSync;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import com.fazecast.jSerialComm.SerialPort;

public class Main extends Application {
	SerialPort chosenPort;
	static Scene loginScreen;
	static Scene landing;

	@Override
	public void start(Stage primaryStage) throws Exception {
		double columnWidth = 200;

		String user = "m2m";
		String pw = "UBCBEST";
		String blank = "";

		// Login Screen
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));

		primaryStage.setTitle("Music to Movement");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("m2m_light.png")));

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
			if (passwordField.getText().equals(pw) && userNameField.getText().equals(user)) {
				// message.setText(blank);

				try {
					SerialPort[] portNames = SerialPort.getCommPorts();
					chosenPort = SerialPort.getCommPort(portNames[0].toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
				} catch (Exception b) {

					JOptionPane.showMessageDialog(null, "Cannot connect to device");
				}
				primaryStage.setScene(landing);
			} else {
				LoginFail.display("Login Failed", "Incorrect Username and/or Password");

			}
			passwordField.setText(blank);

		});
		quit.setOnAction(e -> primaryStage.close());

		// Main Menu
		BorderPane layout = new BorderPane();
		// HBox menu = new HBox(10);
		AnchorPane display = new AnchorPane();
		HBox gamemode = new HBox(10);
		HBox editFunctions = new HBox(10);
		GridPane pData = new GridPane();
		SQLiteSync db = new SQLiteSync();

		AnchorPane.setTopAnchor(pData, 20.0);
		AnchorPane.setLeftAnchor(gamemode, 2.0);
		AnchorPane.setBottomAnchor(gamemode, null);
		AnchorPane.setRightAnchor(editFunctions, 5.0);
		AnchorPane.setBottomAnchor(editFunctions, 8.0);
		// gamemode.setAlignment(Pos.CENTER);

		Label patientData = new Label("Patient Data:");
		Label patientFirst = new Label("First Name:");
		Label patientLast = new Label("Last Name:");
		Label firstData = new Label("No Data Available");
		Label lastData = new Label("No Data Available");
		Label dateData = new Label("No Data Available");
		Label testLabel = new Label("should appear when row selected");

		TableView<Patient> pTable = new TableView<Patient>();
		ObservableList<Patient> data;

		final Pane leftSpacer = new Pane();
		HBox.setHgrow(leftSpacer, Priority.SOMETIMES);

		Button helpAfter = new Button("?");
		Button logout = new Button("Log Out");

		Label welcomeMessage = new Label("Welcome to Music 2 Movement! Select a patient, or  ");
		Button newPatient = new Button("Add New Patient...");

		final HBox menu = new HBox(welcomeMessage, newPatient, leftSpacer, helpAfter, logout);
		menu.setAlignment(Pos.CENTER);
		display.getChildren().addAll(menu);

		// Patient Table
		TableColumn<Patient, String> lastName = new TableColumn<Patient, String>("Last Name");
		TableColumn<Patient, String> firstName = new TableColumn<Patient, String>("First Name");
		TableColumn<Patient, String> date = new TableColumn<Patient, String>("Date of Last Visit");

		pTable.getColumns().setAll(lastName, firstName, date);
		pTable.setPrefWidth(20);
		pTable.setPrefHeight(620);

		lastName.setMinWidth(columnWidth);
		lastName.setCellValueFactory(new PropertyValueFactory<Patient, String>("lastName"));

		firstName.setMinWidth(columnWidth);
		firstName.setCellValueFactory(new PropertyValueFactory<Patient, String>("firstName"));

		date.setMinWidth(columnWidth);
		date.setCellValueFactory(new PropertyValueFactory<Patient, String>("visitDate"));

		pTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		data = getData(db);
		pTable.setItems(data);

		//HBox.setMargin(pTable,new Insets(100,100,100,100));
		menu.setPadding(new Insets(10, 10, 20, 10));
		pData.setPadding(new Insets(30, 10, 0, 20));
		gamemode.setPadding(new Insets(0, 0, 0, 20));

		layout.setTop(menu);
		layout.setCenter(pTable);
		layout.setRight(display);

		Button editPatient = new Button("Edit Patient...");
		Button deletePatient = new Button("Delete Patient");

		Button newSession = new Button("New Session...");
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
		pData.add(firstData, 1, 1);
		pData.add(lastData, 1, 2);
		pData.add(dateData, 1, 3);
		helpAfter.setOnAction(e -> Help.display("Music to Movement Help", "This is the help window."));

		logout.setOnAction(e -> {
			primaryStage.setScene(loginScreen);
		});

		newPatient.setOnAction(e -> {
			AddPatient.display(this, db, pTable);
			// data = getData(db);
			// pTable.setItems(getData(db));
		});

		editPatient.setOnAction(e -> {
			EditPatient.display(this, db, pTable);
		});

		newSession.setOnAction(e -> {
			gamemode.getChildren().addAll(freePlay, gamemode1, gamemode2);
			AnchorPane.setRightAnchor(gamemode, 5.0);
			newSession.setManaged(false);
			newSession.setVisible(false);
		});

		gamemode.getChildren().addAll(newSession);
		editFunctions.getChildren().addAll(editPatient); // delete patient not
															// added
		// trying to make it appear on select only

		pTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		pTable.requestFocus();
		pTable.getFocusModel().focus(0);

		String row = String.valueOf(pTable.getRowFactory());

		if (row != null) {
			display.getChildren().addAll(pData, gamemode, editFunctions);
		}

		landing = new Scene(layout, 1000, 620);
		loginScreen = new Scene(grid, 1000, 620);

		// loginScreen.getStylesheets().add(Main.class.getResource("theme.css").toExternalForm());

		primaryStage.setScene(loginScreen);
		primaryStage.show();

	}

	private void printf(double width) {
		// TODO Auto-generated method stub

	}

	public class Patient {
		private SimpleStringProperty lastName;
		private SimpleStringProperty firstName;
		private SimpleStringProperty visitDate;

		public Patient(String lName, String fName, String vDate) {
			this.lastName = new SimpleStringProperty(lName);
			this.firstName = new SimpleStringProperty(fName);
			this.visitDate = new SimpleStringProperty(vDate);
		}

		public String getLastName() {
			return lastName.get();
		}

		public void setLastName(String lName) {
			lastName.set(lName);
		}

		public String getFirstName() {
			return firstName.get();
		}

		public void setFirstName(String fName) {
			lastName.set(fName);
		}

		public String getVisitDate() {
			return visitDate.get();
		}

		public void setVisitDate(String vDate) {
			lastName.set(vDate);
		}
	}

	ObservableList<Patient> getData(SQLiteSync db) {
		List<Patient> list = new ArrayList<Patient>();

		ResultSet rs;

		try {
			rs = db.displayUsers();
			while (rs.next()) {
				// System.out.println(rs.getString("fname") + " " +
				// rs.getString("lname"));
				list.add(new Patient(rs.getString("lname"), rs.getString("fname"), rs.getString("sname")));
			}
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "oops");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Woops");
		}

		ObservableList<Patient> data = FXCollections.observableList(list);
		return data;

	}

}
