// Das hier ist das Fenster, in dem man sich die gewünschte Übung aussucht

package gui;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Hilfe;
import logic.Loader;

public class Menu extends GridPane {
	private int exc_auswahl = -1;
	private Stage stage;
	private String css = this.getClass().getResource("/layout/style.css").toExternalForm();

	
	Menu(Stage stage) {
		this.stage = stage;
		
		Loader non_static_af = new Loader();          //dazu da um static Referenzprobleme zu behandeln
		non_static_af.read_exc();

		ArrayList<String> Aufgaben_Namen = new ArrayList<String>();
		for(int i=0;i<non_static_af.Aufgaben_Verwaltung.size();i++){
			Aufgaben_Namen.add(non_static_af.Aufgaben_Verwaltung.get(i).getName());
		}

		ObservableList<String> options = 
				FXCollections.observableArrayList(
						Aufgaben_Namen
				);
		final ComboBox<String> exc_comboBox = new ComboBox<String>(options);
		exc_comboBox.setPromptText("Uebungen");
				
		Text tx_auswahl = new Text("Schritt 1: Waehlen Sie eine Uebung aus:");  
		TextArea txF_beschr = new TextArea("Aufgabenbeschreibung");
		txF_beschr.setId("description");
		txF_beschr.setPrefWidth(300);
		txF_beschr.setPrefHeight(100);
		txF_beschr.setEditable(false);
		txF_beschr. setWrapText(true);
		
		Button bt_select = new Button();
		bt_select.setText("Uebung beginnen");
	   
		Button bt_ext_help = new Button();
		bt_ext_help.setText("Erlaeuterung");

		Label noSelection = new Label("Bitte waehlen Sie eine Uebung aus!");
		noSelection.setId("lbl_noSelection");
		noSelection.setTextFill(Color.RED);
		noSelection.setVisible(false);
		
		Label extensionRadio = new Label("Schritt 2: Waehlen Sie eine Erweiterung aus!");
		
		ToggleGroup radioButtonGroup = new ToggleGroup();
		RadioButton rb_1 = new RadioButton("Baby Steps ");
		rb_1.setToggleGroup(radioButtonGroup);
		RadioButton rb_2 = new RadioButton("Tracking");
		rb_2.setToggleGroup(radioButtonGroup);
		HBox radioButtonControls = new HBox();
		radioButtonControls.getChildren().addAll(rb_1,rb_2);
		rb_1.setSelected(true);
		rb_1.requestFocus();

		this.setId("excGrid");
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.add(exc_comboBox,1,0);
		this.add(tx_auswahl,0,0);
		this.add(txF_beschr,0,4);
		this.add(bt_select,0,5);
		this.add(noSelection,0,7);
		this.add(extensionRadio, 0, 8);
		this.add(radioButtonControls, 0, 9);
		this.add(bt_ext_help, 1, 8);
		
		//Function to Erlaeuterung Help
		bt_ext_help.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Hilfe.displayExtension();
			}
		});
		
		exc_comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String old_value, String new_value) {
				exc_auswahl = Aufgaben_Namen.indexOf(new_value);
				String desc = non_static_af.Aufgaben_Verwaltung.get(exc_auswahl).getBeschreibung();
				txF_beschr.setText(desc);
				noSelection.setVisible(false);
	        }    
	    });
		
		bt_select.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				if(exc_auswahl == -1) {
					noSelection.setVisible(true);
					return;
				}
				non_static_af.saveNew(exc_auswahl);
				Scene scene = new Scene(new ExerciseWindow(stage, non_static_af, exc_auswahl), GUI.WIDTH, GUI.HEIGHT);
				scene.getStylesheets().addAll(css);
				stage.setScene(scene);
			}
		});
	}
}