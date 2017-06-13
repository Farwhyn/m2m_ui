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
		final ToggleGroup mot = new ToggleGroup();
		final ToggleGroup ov = new ToggleGroup();
		final ToggleGroup ben = new ToggleGroup();
		final ToggleGroup imp = new ToggleGroup();
		
		qWindow.initModality(Modality.APPLICATION_MODAL);
		qWindow.setTitle(title);
		qWindow.setMinWidth(450);
		qWindow.setMinHeight(750);
		
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
		
		RadioButton m6 = new RadioButton("1");
		m6.setToggleGroup(mot);
		RadioButton m7 = new RadioButton("2");
		m7.setToggleGroup(mot);
		RadioButton m8 = new RadioButton("3");
		m8.setToggleGroup(mot);
		RadioButton m9 = new RadioButton("4");
		m9.setToggleGroup(mot);
		RadioButton m10 = new RadioButton("5");
		m10.setToggleGroup(mot);
		
		RadioButton m11 = new RadioButton("1");
		m11.setToggleGroup(ov);
		RadioButton m12 = new RadioButton("2");
		m12.setToggleGroup(ov);
		RadioButton m13 = new RadioButton("3");
		m13.setToggleGroup(ov);
		RadioButton m14 = new RadioButton("4");
		m14.setToggleGroup(ov);
		RadioButton m15 = new RadioButton("5");
		m15.setToggleGroup(ov);
		
		RadioButton m16 = new RadioButton("1");
		m16.setToggleGroup(ben);
		RadioButton m17 = new RadioButton("2");
		m17.setToggleGroup(ben);
		RadioButton m18 = new RadioButton("3");
		m18.setToggleGroup(ben);
		RadioButton m19 = new RadioButton("4");
		m19.setToggleGroup(ben);
		RadioButton m20 = new RadioButton("5");
		m20.setToggleGroup(ben);
		
		RadioButton m21 = new RadioButton("1");
		m21.setToggleGroup(imp);
		RadioButton m22 = new RadioButton("2");
		m22.setToggleGroup(imp);
		RadioButton m23 = new RadioButton("3");
		m23.setToggleGroup(imp);
		RadioButton m24 = new RadioButton("4");
		m24.setToggleGroup(imp);
		RadioButton m25 = new RadioButton("5");
		m25.setToggleGroup(imp);
		
		//Label Questi = new Label();
		//helpText.setText(message);
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e->{
			qWindow.close();
		});
		Button next = new Button("Continue");
		next.setDefaultButton(true);
		next.setOnAction(e -> qWindow.close());
		HBox buttons = new HBox(6, next, cancel);
		GridPane.setConstraints(buttons, 0, 16);
		GridPane qlayout = new GridPane();
		qlayout.setPadding(new Insets(5,5,5,5));
	    qlayout.setVgap(6);
	    qlayout.setHgap(4);
		
	    Label patient = new Label("Patient: " + lastName + ", "+ firstName);
	    GridPane.setConstraints(patient, 0, 0);
	    
	    Label question = new Label("Please answer the following on a scale of 1-5, with 1 representing �Inaccurate� and 5 indicating �Extremely Accurate�:");
	    GridPane.setConstraints(question, 0, 1);
	    
	    Label moodQuestion = new Label("I feel cheerful and in good spirits.");
	    GridPane.setConstraints(moodQuestion, 0, 2);
	    
	    Label motivation = new Label("I am motivated to participate in music therapy.");
	    GridPane.setConstraints(motivation, 0, 4);
	    
	    Label overwhelmed = new Label("I feel overwhelmed using M2M.");
	    GridPane.setConstraints(overwhelmed, 0, 6);
	    
	    Label beneficial = new Label("Participating with M2M is beneficial for me.");
	    GridPane.setConstraints(beneficial, 0, 8);
	    
	    Label improvement = new Label("I am improving my ability to interact with M2M.");
	    GridPane.setConstraints(improvement, 0, 10);
	    
		HBox moodOptions = new HBox(6, m1, m2, m3, m4, m5);
		GridPane.setConstraints(moodOptions, 0, 3);
		
		HBox motivationOptions = new HBox(6,m6,m7,m8,m9,m10);
		GridPane.setConstraints(motivationOptions, 0, 5);

		HBox overOptions = new HBox(6,m11,m12,m13,m14,m15);
		GridPane.setConstraints(overOptions, 0, 7);
		
		HBox beneficialOptions = new HBox(6,m16,m17,m18,m19,m20);
		GridPane.setConstraints(beneficialOptions, 0, 9);
		
		HBox improvementOptions = new HBox(6,m21,m22,m23,m24,m25);
		GridPane.setConstraints(improvementOptions, 0, 11);

		Label comments = new Label("You are encouraged to elaborate on any of your previous answers:");
		GridPane.setConstraints(comments,0,12);
		
		TextArea commentBox = new TextArea();
		GridPane.setConstraints(commentBox,0,13);
		
		Label goals=  new Label("Please describe some goals for this session:");
		GridPane.setConstraints(goals, 0, 14);
		
		TextArea goalBox = new TextArea();
		GridPane.setConstraints(goalBox, 0, 15);
		
		qlayout.getChildren().addAll(goals, goalBox, patient ,question,moodOptions, comments, commentBox, buttons, moodQuestion, motivation, overwhelmed, beneficial, improvement, motivationOptions, overOptions, beneficialOptions, improvementOptions);

		
		
		Scene scene = new Scene(qlayout);
		qWindow.setScene(scene);
		qWindow.show();
	}

}
