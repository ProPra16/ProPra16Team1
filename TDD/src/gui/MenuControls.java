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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
		bt_select.setDisable(true);
	   
		Button bt_ext_help = new Button();
		bt_ext_help.setText("Erlaeuterung");

		Label extensionRadio = new Label("Erweiterung wählen");
		extensionRadio.setId("auswahl2");
		
		ToggleGroup radioButtonGroup = new ToggleGroup();
		RadioButton rb_babysteps = new RadioButton("Baby Steps ");
		rb_babysteps.setToggleGroup(radioButtonGroup);
		RadioButton rb_tracking = new RadioButton("Tracking");
		rb_tracking.setToggleGroup(radioButtonGroup);
		HBox radioButtonControls = new HBox();
		//radioButtonControls.getChildren().addAll(rb_babysteps, rb_tracking);

		Label babystepsText = new Label("Waehlen Sie die Zeit für Babysteps");
		ToggleGroup babystepsGroup = new ToggleGroup();
		RadioButton difficulty1 = new RadioButton("2 Minuten");
		RadioButton difficulty2 = new RadioButton("3 Minuten");
		difficulty1.setToggleGroup(babystepsGroup);
		difficulty2.setToggleGroup(babystepsGroup);
		GridPane difficulty = new GridPane();
		difficulty.setVgap(15);
		difficulty.setHgap(30);
		difficulty.add(difficulty1, 1, 0);
		difficulty.add(difficulty2, 1, 1);
		
		babystepsText.setVisible(false);
		difficulty1.setVisible(false);
		difficulty2.setVisible(false);
		
		this.setId("excGrid");
		this.setAlignment(Pos.TOP_CENTER);
		this.setHgap(30);
		this.setVgap(15);
		this.add(tx_auswahl, 1, 4);
		this.add(exerciseList, 1, 5, 1, 10);
		this.add(tx_name, 2, 5, 4, 5);
		this.add(tx_beschr, 2, 6, 2, 7);
		this.add(extensionRadio, 1, 17);
		this.add(radioButtonControls, 1, 18);
		this.add(rb_tracking, 1, 19);
		this.add(rb_babysteps, 1, 20);
		this.add(difficulty, 1, 21);
		this.add(bt_select, 3, 22);

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
				enable(bt_select, radioButtonGroup.getSelectedToggle() != null);
	        }  
		});
		
		radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(rb_babysteps.isSelected()){
					babystepsText.setVisible(true);
					difficulty1.setVisible(true);
					difficulty2.setVisible(true);
					isBabystepSet = true;
					enable(bt_select, (exerciseList.getSelectionModel().getSelectedItem() != null) && (babystepsGroup.getSelectedToggle() != null));
				}else{
					babystepsText.setVisible(false);
					difficulty1.setVisible(false);
					difficulty2.setVisible(false);
					isBabystepSet = false;
					enable(bt_select, exerciseList.getSelectionModel().getSelectedItem() != null);
				}
			}
		});
		
		babystepsGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				enable(bt_select, exerciseList.getSelectionModel().getSelectedItem() != null);
			}
		});
		
		bt_select.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				if(rb_babysteps.isSelected()){
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
	
	void enable(Button button, Boolean boo) {
		if(!boo) {
			button.setDisable(true);
			return;
		}
		button.setDisable(false);
	}
}
