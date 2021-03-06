package main;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/*
 * Class: LoginFail
 * This class creates a pop-up presented to the user whenever an action they try to peform fails
 */

public class LoginFail {
	
	public static void display(String title, String message){
		Stage failedLogin = new Stage();
		
		
		
		failedLogin.initModality(Modality.APPLICATION_MODAL);
		failedLogin.setTitle(title);
		failedLogin.setMinWidth(324);
		failedLogin.setMinHeight(200);
		
		Label failText = new Label();
		failText.setText(message);
		
		Button close = new Button("Close");
		close.setDefaultButton(true);
		close.setOnAction(e -> failedLogin.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(failText,close);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		failedLogin.setScene(scene);
		failedLogin.show();
	}

}