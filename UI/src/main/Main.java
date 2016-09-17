package main;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
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
		
		primaryStage.setTitle("Music 2 Movement");
		
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		
		Label userName = new Label("Username:");
		grid.add(userName, 0, 1);
		
		TextField userNameField = new TextField();
		grid.add(userNameField, 1, 1);
		
		Label password = new Label("Password:");
		grid.add(password, 0, 2);
		
		TextField passwordField = new TextField();
		grid.add(passwordField, 1, 2);
		
		Button login = new Button("Log In");
		grid.add(login, 1, 3);
		GridPane.setHalignment(login, HPos.RIGHT);
		
		Button help = new Button("?");
		grid.add(help, 1, 0);
		GridPane.setHalignment(help, HPos.RIGHT);
		
		help.setOnAction(e -> Help.display("Music 2 Movement Help", "This is the help window."));
		
		login.setOnAction(e -> primaryStage.setScene(menuScreen));
		
		
		//Main Menu
		VBox menu = new VBox(10);
		menu.setAlignment(Pos.CENTER);
		
		Button freePlay = new Button("Free Play");
		Button logout = new Button("Log Out");
		Button gamemode1 = new Button("Game Mode 1");
		Button gamemode2 = new Button("Game Mode 2");
		
		freePlay.setMaxSize(150, Double.MAX_VALUE);
		logout.setMaxSize(150, Double.MAX_VALUE);
		gamemode1.setMaxSize(150, Double.MAX_VALUE);
		gamemode2.setMaxSize(150, Double.MAX_VALUE);
		
		logout.setOnAction(e -> primaryStage.setScene(loginScreen));
		
		menu.getChildren().addAll(freePlay, gamemode1, gamemode2, logout);

		menuScreen = new Scene(menu, 200 ,200);
		loginScreen = new Scene(grid, 300, 275);
		primaryStage.setScene(loginScreen);
		primaryStage.show();
	}

}
