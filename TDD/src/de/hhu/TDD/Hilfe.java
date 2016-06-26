package de.hhu.TDD;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Hilfe {
	
	public static void displayRED(){
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Hilfe");
		window.setWidth(400);
		window.setHeight(200);
		
		Label help = new Label();
		help.setText("Sie müssen einen fehlschlagenden Test implementieren"
				+ "\n Wenn Sie fertig sind,klicken Sie bitte auf Button zuGreen,"
				+ "\n um in nächsten Schritt weitergeleitet zu werden");
		Button close = new Button("Close");
		close.setOnAction(e -> window.close());
		VBox root = new VBox(15);
		root.getChildren().addAll(help,close);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root);
		window.setScene(scene);
		window.showAndWait();
	}
}
