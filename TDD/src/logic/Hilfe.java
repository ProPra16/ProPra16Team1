package logic;

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
	
	public static void displayExtension(){
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Erlaueterung");
		window.setWidth(400);
		window.setHeight(300);
		
		Label help = new Label();
		Label test = new Label();
		help.setText("Baby Steps:"
				+ "\nSie haben limitiert Zeit fuer die einzelnen Phasen (RED und GREEN). "
				+ "\nLaeuft die Zeit ab, wird der Code geloescht und Sie werden "
				+ "\nzur vorherigen Phasen zurueckgefuehrt.");
		test.setText("Tracking:"
				+ "\nDiese Funktion zeichnet ihre Aktivit�ten auf. Wie vie Zeit in einer "
				+ "\nPhase ben�tigt wurde und welche Fehler aufgetreten worden sind "
				+ "\nwerden mittels einer Chart dargestellt");
		Button close = new Button("Close");
		close.setOnAction(e -> window.close());
		VBox root = new VBox(15);
		root.getChildren().addAll(help,test,close);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root);
		window.setScene(scene);
		window.showAndWait();
	}
}
