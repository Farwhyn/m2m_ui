package main;
import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class EditPatient {

	public static void display(Main main, SQLiteSync db, TableView table){
		Stage newPatient = new Stage();
		
		
		
		newPatient.initModality(Modality.APPLICATION_MODAL);
		newPatient.setTitle("Edit Patient");
		newPatient.setMinWidth(324);
		newPatient.setMinHeight(200);
		
		Label firstPrompt = new Label("First Name: ");
		Label lastPrompt = new Label("Last Name: ");
		Label datePrompt = new Label("Date: ");
		Label notePrompt = new Label("Additional Comments: ");
		
		
		
		TextField firstName = new TextField();
		TextField lastName = new TextField();
		TextField date = new TextField();
		TextField notes = new TextField();
		//failText.setText(message);
		
		Button finish = new Button("Finish");
		Button cancel = new Button ("Cancel");
		
		finish.setOnAction(e -> {
			
			try {
				db.addPatient(firstName.getText(), lastName.getText(), date.getText());
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			table.setItems(main.getData(db));
			newPatient.close();
		});
		cancel.setOnAction(e -> newPatient.close());
		
		GridPane layout = new GridPane();
		//layout.getChildren().addAll(failText,close);
		layout.add(firstPrompt, 0, 0);
		layout.add(lastPrompt, 0, 1);
		layout.add(datePrompt, 0, 2);
		layout.add(firstName, 1, 0);
		layout.add(lastName, 1, 1);
		layout.add(date, 1, 2);
		layout.add(finish	, 0	, 3);
		layout.add(cancel, 1, 3);
		
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		newPatient.setScene(scene);
		newPatient.show();
	}

}
