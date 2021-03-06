package main;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/*
 * Class: Help
 * This class creates a pop-up whenever the user clicks the "?" button.
 * The content of this pop-up is intended to assist the user if they are having issues operating
 * the program.
 */

public class Help {
	
	public static void display(String title, String message){
		Stage helpWindow = new Stage();
		
		helpWindow.initModality(Modality.APPLICATION_MODAL);
		helpWindow.setTitle(title);
		helpWindow.setMinWidth(450);
		helpWindow.setMinHeight(720);
		
		Label helpText = new Label();
		helpText.setText(message);
		
		Button close = new Button("Close");
		close.setDefaultButton(true);
		close.setOnAction(e -> helpWindow.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(helpText,close);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		helpWindow.setScene(scene);
		helpWindow.show();
	}

}
