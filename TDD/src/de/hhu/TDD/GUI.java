package de.hhu.TDD;


import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;

public class GUI extends Application{

private Stage mainMenu;
private int exc_auswahl = -1;        //Falls keine Wahl getroffen wurde -1
private boolean firstStart = true;
private CompilationUnit compileClass;
private CompilationUnit compileTest;
private JavaStringCompiler compiler;

public static void main(String[] args) {
launch(args);
}

 @Override
 public void start(Stage mainStage){

 Button bt_training = new Button();
 Button bt_continue = new Button();
 bt_training.setText("Uebung auswaehlen");
 bt_continue.setText("Uebung fortsetzen");
 Text tx_Welcome = new Text("Willkommen!");
 tx_Welcome.setFill(Color.WHITE);
 tx_Welcome.setFont(Font.font("",20));

 GridPane mainMenu_layout = new GridPane();
 mainMenu_layout.setId("main");
 mainMenu_layout.setAlignment(Pos.CENTER);
 mainMenu_layout.setHgap(10);
 mainMenu_layout.setVgap(10);
 mainMenu_layout.add(tx_Welcome,0,0);
 mainMenu_layout.add(bt_training,0,1);
 mainMenu_layout.add(bt_continue,0,2); 

 mainStage.getIcons().add(new Image(getClass().getResourceAsStream("TDDLogo.png")));
 
 Scene sc_mainmenu = new Scene(mainMenu_layout,700,600);
 sc_mainmenu.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
 mainStage.setScene(sc_mainmenu);
 mainStage.setTitle("Hauptmenue");
 mainStage.show();
 mainMenu = mainStage;
 //closes the running thread 
 mainStage.setOnCloseRequest( e -> {
			Platform.exit();
			System.exit(0);
 });

 
 
  bt_training.setOnAction(new EventHandler<ActionEvent>() {
  @Override public void handle(ActionEvent e) {
    mainStage.hide();
    Stage st_exc_selection = new Stage();
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
   txF_beschr.setPrefWidth(300);
   txF_beschr.setPrefHeight(100);
   txF_beschr.setEditable(false);
   txF_beschr. setWrapText(true); 
   
   // Sets icon
   st_exc_selection.getIcons().add(new Image(getClass().getResourceAsStream("TDDLogo.png")));
   
   Button bt_back = new Button();
   bt_back.setText("Zurueck");

   Button bt_select = new Button();
   bt_select.setText("Uebung beginnen");
   
   Button bt_ext_help = new Button();
   bt_ext_help.setText("Erlaeuterung");

   Label noSelection = new Label("Bitte waehlen Sie eine Uebung aus!");
   noSelection.setId("lbl_noSelection");
   noSelection.setTextFill(Color.RED);
   noSelection.setVisible(false);
 
   Label warning = new Label("WARNUNG: Bestehende Uebung wird geloescht!");
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

   GridPane exc_layout = new GridPane();
   exc_layout.setId("excGrid");
   exc_layout.setAlignment(Pos.CENTER);
   exc_layout.setHgap(10);
   exc_layout.setVgap(10);
   exc_layout.add(exc_comboBox,1,0);
   exc_layout.add(tx_auswahl,0,0);
   exc_layout.add(txF_beschr,0,4);
   exc_layout.add(bt_back,1,30);
   exc_layout.add(bt_select,0,5);
   exc_layout.add(warning,0,6);
   exc_layout.add(noSelection,0,7);
   exc_layout.add(extensionRadio, 0, 8);
   exc_layout.add(radioButtonControls, 0, 9);
   exc_layout.add(bt_ext_help, 1, 8);
   
 //Function to Erlaeuterung Help
 	bt_ext_help.setOnAction(new EventHandler<ActionEvent>() {
 		   @Override public void handle(ActionEvent e) {
 		   Hilfe.displayExtension();
 	}});

   
   Scene sc_choose = new Scene(exc_layout,700,600);
   sc_choose.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
   st_exc_selection.setScene(sc_choose);  
   st_exc_selection.setTitle("Auswahl");
   st_exc_selection.show();
   
   //closes the running thread
   st_exc_selection.setOnCloseRequest( event -> {
 			Platform.exit();
 			System.exit(0);
 	});
  
   exc_comboBox.valueProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue ov, String old_value, String new_value) {
         exc_auswahl = Aufgaben_Namen.indexOf(new_value);
         String desc = non_static_af.Aufgaben_Verwaltung.get(exc_auswahl).getBeschreibung();
         txF_beschr.setText(desc);
         noSelection.setVisible(false);
        }    
    });

   bt_back.setOnAction(new EventHandler<ActionEvent>() {
   @Override public void handle(ActionEvent e) {
   mainMenu.show();
   st_exc_selection.hide();
    }});

   bt_select.setOnAction(new EventHandler<ActionEvent>() {
   @Override public void handle(ActionEvent e) {
    if(exc_auswahl == -1){
     noSelection.setVisible(true);
     return;
     }
    st_exc_selection.hide();
    Stage editor = new Stage();
	Label instruction = new Label("//implementieren Sie den Code hier");
	TextArea codeArea = new TextArea();
	
	Button bt_toGreen = new Button("Wechsel zu Green");
	bt_toGreen.setId("bt_toGreen");
	Button bt_toRed = new Button("Zurueck zu Red");
	bt_toRed.setId("bt_toRed");
	Button bt_Refactor = new Button("Refactor");
	Button bt_help = new Button("Hilfe");
	Button bt_RfctrDone = new Button("Refactoren beendet");
	Button bt_backExc =  new Button("Zurueck zum Auswahlmenue");
	Label timeRemaining = new Label();
	// Thread that makes the timer for Babysteps
	Thread thread = new Thread(new Runnable(){

		private int seconds = 180; // User hat 180 seconds ( 3 Minuten )um den Test zu schreiben.
		private boolean running = true;
		@Override
		public void run() {
			try{
				while(running){
					if (seconds == 1) running = false;
					Platform.runLater(new Runnable(){
						@Override
						public void run(){
							timeRemaining.setText("Sie haben noch : " + seconds + " seconds"
									+"\n,um Ihre Lösung zu implementieren.");
						}
					});
					seconds--;
					Thread.sleep(1000);
				}
			} catch (Exception e) { }
		}
		
	});
	thread.start();
	
	//closes the running thread
	editor.setOnCloseRequest( event -> {
		Platform.exit();
		System.exit(0);
	});
	 
	
	editor.getIcons().add(new Image(getClass().getResourceAsStream("TDDLogo.png")));
	bt_backExc.setOnAction(new EventHandler<ActionEvent>() {
		   @Override public void handle(ActionEvent e) {
		   st_exc_selection.show();
		   editor.hide();
		   firstStart=true;
		    }});
	
	bt_Refactor.setVisible(false);
    bt_toRed.setVisible(false);
    bt_RfctrDone.setVisible(false);
	
	non_static_af.saveNew(exc_auswahl);
	
	// war vorher setConstraints, habs nur zeitweilig zu add geaendert
	GridPane root = new GridPane();
	root.add(instruction, 1, 1);
	root.add(codeArea, 1, 2);
	root.add(bt_toGreen, 1, 3);
	root.add(timeRemaining, 1, 4);
	root.add(bt_help, 1, 5);
	root.add(bt_toRed,1,3);
	root.add(bt_Refactor,1,4);
	root.add(bt_RfctrDone,1,5);
	root.add(bt_backExc, 1, 30);
	//root.getChildren().addAll(instruction,codeArea,bt_toGreen,bt_help,bt_Refactor,bt_RfctrDone,bt_toRed,bt_backExc);
	
	Scene scene = new Scene(root,700,600);
	scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
	editor.setScene(scene);
	editor.setTitle("RED");
	editor.show();

	codeArea.setWrapText(true);
	
	String classCode = non_static_af.loadCurrentData("currentTest");
	codeArea.setText(classCode);
	
	//Function to Button toGreen
	bt_toGreen.setOnAction(new EventHandler<ActionEvent>() { //Wechsel von RED zu GREEN
		   @Override public void handle(ActionEvent e) {     
			String  testCode = codeArea.getText(); // here is the test from user
            non_static_af.save("currentTest",testCode);
            
            String testName  = non_static_af.Aufgaben_Verwaltung.get(exc_auswahl).testName();
            CompilationUnit tmp_compileTest = new CompilationUnit(testName,testCode,true);
		    compileTest = tmp_compileTest;
		    
		   if(firstStart==false){
		     try{	 
			 compiler = CompilerFactory.getCompiler(compileTest,compileClass);
			 compiler.compileAndRunTests();
			 TestResult testResult = compiler.getTestResult();
			 int failTest = testResult.getNumberOfFailedTests();
			 if(failTest == 1){
				 bt_help.setVisible(false);
				 bt_RfctrDone.setVisible(false);
				 bt_toGreen.setVisible(false);
				 bt_toRed.setVisible(true);
				 bt_Refactor.setVisible(true);
				 String classCode = non_static_af.loadCurrentData("currentClass");
				 codeArea.setText(classCode);	 
			  }
		     }
		     catch(NullPointerException k){
		    	 bt_help.setVisible(false);
				 bt_RfctrDone.setVisible(false);
				 bt_toGreen.setVisible(false);
				 bt_toRed.setVisible(true);
				 bt_Refactor.setVisible(true);
				 String classCode = non_static_af.loadCurrentData("currentClass");
				 codeArea.setText(classCode);
				 editor.setTitle("GREEN");
				 System.out.println("Fehler beim Kompilieren, bitte beheben!");
			 }
		   }	
      
		    if(firstStart==true){
			bt_help.setVisible(false);
			bt_RfctrDone.setVisible(false);
			bt_toGreen.setVisible(false);
			bt_toRed.setVisible(true);
			bt_Refactor.setVisible(true);
			String classCode = non_static_af.loadCurrentData("currentClass");
			codeArea.setText(classCode);
			editor.setTitle("GREEN");
			firstStart=false;
		    }
		    
	}});
	
	//Function to Button Help
	bt_help.setOnAction(new EventHandler<ActionEvent>() {
		   @Override public void handle(ActionEvent e) {
		   Hilfe.displayRED();
	}});
	
	bt_toRed.setOnAction(new EventHandler<ActionEvent>() {   //Wechsel von GREEN zu RED
	  @Override public void handle(ActionEvent e) {
		  bt_toGreen.setVisible(true);
		  bt_help.setVisible(true);
		  bt_toRed.setVisible(false);
		  bt_Refactor.setVisible(false);
		  editor.setTitle("RED");
		  
		  String classCode = codeArea.getText();		  
		  String className = non_static_af.Aufgaben_Verwaltung.get(exc_auswahl).className();
		  CompilationUnit tmp_compileClass = new CompilationUnit(className,classCode,false); 
		  compileClass = tmp_compileClass;
		  
		  classCode = non_static_af.loadCurrentData("currentTest");
		  codeArea.setText(classCode);	  
	  }}) ;

	  bt_Refactor.setOnAction(new EventHandler<ActionEvent>() {   //Wechsel von GREEN zu Refactor
	  @Override public void handle(ActionEvent e) {	
	  try{	  
	   String classCode = codeArea.getText();		  
	   String className = non_static_af.Aufgaben_Verwaltung.get(exc_auswahl).className();
	   CompilationUnit tmp_compileClass = new CompilationUnit(className,classCode,false); 
	   compileClass = tmp_compileClass;
	   compiler = CompilerFactory.getCompiler(compileTest,compileClass);
	   compiler.compileAndRunTests();
	   TestResult testResult = compiler.getTestResult();
	   boolean compileErrors = compiler.getCompilerResult().hasCompileErrors();
	   int failTest = testResult.getNumberOfFailedTests();
	  
	  if(failTest == 0 && compileErrors == false){
	   bt_toGreen.setVisible(false);
	   bt_help.setVisible(false);
       bt_toRed.setVisible(false);
	   bt_Refactor.setVisible(false);
	   bt_RfctrDone.setVisible(true);
	   editor.setTitle("REFACTOR");			    
	  }
	  }
	  catch(NullPointerException s){
	  System.out.println("Kompillierungsschwierigkeiten, beheben Sie diese"
	  		+ " vor dem Refactoren!");	  
	  }
		  
		  }});
	
	  bt_RfctrDone.setOnAction(new EventHandler<ActionEvent>() { //Vom fertigen Refactor zu RED
		  @Override public void handle(ActionEvent e) {	
		   String classCode = codeArea.getText();		  
		   String className = non_static_af.Aufgaben_Verwaltung.get(exc_auswahl).className();
		   CompilationUnit tmp_compileClass = new CompilationUnit(className,classCode,false); 
		   compileClass = tmp_compileClass;
		   compiler = CompilerFactory.getCompiler(compileClass,compileTest);
		   compiler.compileAndRunTests();
		   TestResult testResult = compiler.getTestResult();
		   int failTest = testResult.getNumberOfFailedTests();
		   boolean compileErrors = compiler.getCompilerResult().hasCompileErrors();
	       if(failTest == 0 && compileErrors == false){  
	    	   bt_toGreen.setVisible(true);
	    	   bt_toRed.setVisible(false);
	    	   bt_help.setVisible(true);
	    	   bt_RfctrDone.setVisible(false);
	    	   editor.setTitle("RED");	
	    	   
	    	   non_static_af.save("currentClass", classCode);
	    	   classCode = non_static_af.loadCurrentData("currentTest");
	    	   codeArea.setText(classCode);
	       }	  
		  }});
		  		  
    }}) ;
   }});
   
  		
  
//  URL stylesheet = getClass().getResource("style.css");
//  sc_mainmenu.getStylesheets().add(stylesheet.toExternalForm());
//  sc_choose.getStylesheets().add(stylesheet.toExternalForm());
 }


}
