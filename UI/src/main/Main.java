package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.*;




public class Main extends Application{

	
	Scene menuScreen, loginScreen;
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		//Login Screen
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
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
		login.setOnAction(e -> primaryStage.setScene(menuScreen));
		quit.setOnAction(e -> primaryStage.close());
		
		//Main Menu
		VBox menu = new VBox(10);
		menu.setAlignment(Pos.CENTER);
		
		Text menuText = new Text("Menu");
		menuText.setId("menu-text");
		
		Button freePlay = new Button("Free Play");
		Button logout = new Button("Log Out");
		Button gamemode1 = new Button("Game Mode 1");
		Button gamemode2 = new Button("Game Mode 2");
		
		freePlay.setMaxSize(150, Double.MAX_VALUE);
		logout.setMaxSize(150, Double.MAX_VALUE);
		gamemode1.setMaxSize(150, Double.MAX_VALUE);
		gamemode2.setMaxSize(150, Double.MAX_VALUE);
		
		logout.setOnAction(e -> primaryStage.setScene(loginScreen));
		
		menu.getChildren().addAll(menuText,freePlay, gamemode1, gamemode2, logout);

	
		menuScreen = new Scene(menu, 500 ,310);
		loginScreen = new Scene(grid, 500, 310);
		
		loginScreen.getStylesheets().add(Main.class.getResource("theme.css").toExternalForm());
		menuScreen.getStylesheets().add(Main.class.getResource("theme.css").toExternalForm());
		
		primaryStage.setScene(loginScreen);
		primaryStage.show();
	}

}
