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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Hilfe;
import logic.Loader;

public class MenuControls extends GridPane {
	private int exc_auswahl = -1;
	private Stage stage;
	private String css = this.getClass().getResource("/layout/style.css").toExternalForm();
	private boolean isBabystepSet = false;
	
	MenuControls(Stage stage) {
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
		
		final ListView<String> exerciseList = new ListView<String>(options);
		exerciseList.setMaxHeight(180);
		
		Label tx_auswahl = new Label("Übung auswählen");
		tx_auswahl.setId("auswahl");
		tx_auswahl.minWidth(100);
		tx_auswahl.setMinHeight(30);

		Text tx_name = new Text("");
		
		Text tx_beschr = new Text("");
		tx_beschr.setId("description");
		tx_beschr.setWrappingWidth(300);
		
		Button bt_select = new Button();
		bt_select.setText("Uebung beginnen");
	   
		Button bt_ext_help = new Button();
		bt_ext_help.setText("Erlaeuterung");

		Label noSelection = new Label("Bitte waehlen Sie eine Uebung aus!");
		noSelection.setId("lbl_noSelection");
		noSelection.setTextFill(Color.RED);
		noSelection.setVisible(false);
		
		Label extensionRadio = new Label("Erweiterung wählen");
		extensionRadio.setId("auswahl2");
		
		ToggleGroup radioButtonGroup = new ToggleGroup();
		RadioButton rb_1 = new RadioButton("Baby Steps ");
		rb_1.setToggleGroup(radioButtonGroup);
		RadioButton rb_2 = new RadioButton("Tracking");
		rb_2.setToggleGroup(radioButtonGroup);
		HBox radioButtonControls = new HBox();
		radioButtonControls.getChildren().addAll(rb_1,rb_2);
		rb_1.setSelected(true);
		rb_1.requestFocus();

		Label babystepsText = new Label("Waehlen Sie die Zeit für Babysteps");
		ToggleGroup babystepsGroup = new ToggleGroup();
		RadioButton difficulty1 = new RadioButton("2 Minuten");
		RadioButton difficulty2 = new RadioButton("3 Minuten");
		difficulty1.setToggleGroup(babystepsGroup);
		difficulty2.setToggleGroup(babystepsGroup);
		HBox difficultyButtonControls = new HBox();
		difficultyButtonControls.getChildren().addAll(difficulty1,difficulty2);
		Button set = new Button("Set");
		
		babystepsText.setVisible(false);
		difficulty1.setVisible(false);
		difficulty2.setVisible(false);
		
		set.setOnAction( e -> {
				if(rb_1.isSelected()){
					babystepsText.setVisible(true);
					difficulty1.setVisible(true);
					difficulty2.setVisible(true);
					isBabystepSet = true;
				}else{
					babystepsText.setVisible(false);
					difficulty1.setVisible(false);
					difficulty2.setVisible(false);
					isBabystepSet = false;
				}
		});
		
		this.setId("excGrid");
		this.setAlignment(Pos.TOP_CENTER);
		this.setHgap(30);
		this.setVgap(15);
		this.add(tx_auswahl, 1, 4);
		this.add(exerciseList, 1, 5, 1, 10);
		this.add(tx_name, 2, 5);
		this.add(tx_beschr, 2, 5, 2, 6);
		this.add(bt_select,2,12);
		this.add(noSelection,1,16);
		this.add(extensionRadio, 1, 17);
		this.add(radioButtonControls, 1, 18);
		this.add(set, 2, 19);
		this.add(bt_ext_help, 2, 20);
		this.add(babystepsText, 1, 21);
		this.add(difficultyButtonControls, 1, 22);
		
		//Function to Erlaeuterung Help
		bt_ext_help.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Hilfe.displayExtension();
			}
		});
		
		exerciseList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String old_value, String new_value) {
				exc_auswahl = Aufgaben_Namen.indexOf(new_value);
				String desc = non_static_af.Aufgaben_Verwaltung.get(exc_auswahl).getBeschreibung();
				String name = non_static_af.Aufgaben_Verwaltung.get(exc_auswahl).getName();
				tx_name.setText(name);
				tx_beschr.setText(desc);
				noSelection.setVisible(false);
	        }  
		});
		
		bt_select.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				if(exc_auswahl == -1) {
					noSelection.setVisible(true);
					return;
				}
				if(rb_1.isSelected()){
					if(difficulty1.isSelected()){
						non_static_af.saveNew(exc_auswahl);
						Scene scene = new Scene(new ExerciseWindow(stage, non_static_af, exc_auswahl,true,120), GUI.WIDTH, GUI.HEIGHT);
						scene.getStylesheets().addAll(css);
						stage.setScene(scene);
					}if(difficulty2.isSelected()){
						non_static_af.saveNew(exc_auswahl);
						Scene scene = new Scene(new ExerciseWindow(stage, non_static_af, exc_auswahl,true,180), GUI.WIDTH, GUI.HEIGHT);
						scene.getStylesheets().addAll(css);
						stage.setScene(scene);
					}
					return;
				}
				non_static_af.saveNew(exc_auswahl);
				Scene scene = new Scene(new ExerciseWindow(stage, non_static_af, exc_auswahl,false,0), GUI.WIDTH, GUI.HEIGHT);
				scene.getStylesheets().addAll(css);
				stage.setScene(scene);
			}
		});
	}
	
}