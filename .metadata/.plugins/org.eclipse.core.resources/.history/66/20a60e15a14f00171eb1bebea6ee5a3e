package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Questionnaire {
	
	public static void display(String firstName, String lastName, String title, String message){
		Stage qWindow = new Stage();
		final ToggleGroup mood = new ToggleGroup();
		
		qWindow.initModality(Modality.APPLICATION_MODAL);
		qWindow.setTitle(title);
		qWindow.setMinWidth(450);
		qWindow.setMinHeight(720);
		
		RadioButton m1 = new RadioButton("1");
		m1.setToggleGroup(mood);
		RadioButton m2 = new RadioButton("2");
		m2.setToggleGroup(mood);
		RadioButton m3 = new RadioButton("3");
		m3.setToggleGroup(mood);
		RadioButton m4 = new RadioButton("4");
		m4.setToggleGroup(mood);
		RadioButton m5 = new RadioButton("5");
		m5.setToggleGroup(mood);
		
		//Label Questi = new Label();
		//helpText.setText(message);
		
		Button next = new Button("Continue");
		GridPane.setConstraints(next, 0, 5);
		next.setDefaultButton(true);
		next.setOnAction(e -> qWindow.close());
		
		
		GridPane qlayout = new GridPane();
		qlayout.setPadding(new Insets(5,5,5,5));
	    qlayout.setVgap(6);
	    qlayout.setHgap(4);
		
	    Label patient = new Label("Patient: " + lastName + ", "+ firstName);
	    GridPane.setConstraints(patient, 0, 0);
	    
	    Label moodQuestion = new Label("On a scale from 1-5 indicate how the patient is feeling today (5 being great).");
	    GridPane.setConstraints(moodQuestion, 0, 1);
	    
		HBox moodOptions = new HBox(6, m1, m2, m3, m4, m5);
		GridPane.setConstraints(moodOptions, 0, 2);

		Label comments = new Label("Additional Comments:");
		GridPane.setConstraints(comments,0,3);
		
		TextArea commentBox = new TextArea();
		GridPane.setConstraints(commentBox,0,4);
		qlayout.getChildren().addAll(patient ,moodQuestion,moodOptions, comments, commentBox, next);

		
		
		Scene scene = new Scene(qlayout);
		qWindow.setScene(scene);
		qWindow.show();
	}

}
