package de.hhu.TDD;


import java.util.ArrayList;

import vk.core.api.*;

import javafx.application.Application;
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
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vk.core.api.CompilerFactory;

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
 bt_training.setText("Übung auswählen");
 bt_continue.setText("Übung fortsetzen");
 Text tx_Welcome = new Text("Willkommen!");

 GridPane mainMenu_layout = new GridPane();
 mainMenu_layout.setAlignment(Pos.CENTER);
 mainMenu_layout.setHgap(10);
 mainMenu_layout.setVgap(10);
 mainMenu_layout.add(tx_Welcome,0,0);
 mainMenu_layout.add(bt_training,0,1);
 mainMenu_layout.add(bt_continue,0,2); 

 Scene sc_mainmenu = new Scene(mainMenu_layout,500,500);

 mainStage.setScene(sc_mainmenu);
 mainStage.setTitle("Hauptmenü");
 mainStage.show();
 mainMenu = mainStage;

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
   exc_comboBox.setPromptText("Übungen");

   Text tx_auswahl = new Text("Wählen Sie eine Übung aus:");  
   TextArea txF_beschr = new TextArea("Aufgabenbeschreibung");
   txF_beschr.setPrefWidth(300);
   txF_beschr.setPrefHeight(100);
   txF_beschr.setEditable(false);
   txF_beschr. setWrapText(true); 

   Button bt_back = new Button();
   bt_back.setText("Zurück");

   Button bt_select = new Button();
   bt_select.setText("Übung beginnen");

   Label noSelection = new Label("Bitte wählen Sie eine Übung aus!");
   noSelection.setVisible(false);
 
   Label warning = new Label("WARNUNG:Bestehende Übung wird gelöscht!");

   GridPane exc_layout = new GridPane();
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

   Scene sc_choose = new Scene(exc_layout,500,500);
   st_exc_selection.setScene(sc_choose);  
   st_exc_selection.setTitle("Auswahl");
   st_exc_selection.show();


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
	Button bt_toGreen = new Button("Wechsle zu Green");
	Button bt_toRed = new Button("Zrück zu Red");
	Button bt_Refactor = new Button("Refactor");
	Button bt_help = new Button("Hilfe");
	Button bt_RfctrDone = new Button("Refactoren beendet");
	
	bt_Refactor.setVisible(false);
    bt_toRed.setVisible(false);
    bt_RfctrDone.setVisible(false);
	
	non_static_af.saveNew(exc_auswahl);
	
	GridPane root = new GridPane();
	root.setConstraints(instruction, 1, 1);
	root.setConstraints(codeArea, 1, 2);
	root.setConstraints(bt_toGreen, 1, 3);
	root.setConstraints(bt_help, 1, 5);
	root.setConstraints(bt_toRed,1,3);
	root.setConstraints(bt_Refactor,1,4);
	root.setConstraints(bt_RfctrDone,1,5);
	root.getChildren().addAll(instruction,codeArea,bt_toGreen,bt_help,bt_Refactor,bt_RfctrDone,bt_toRed);
	
	Scene scene = new Scene(root,500,500);
	editor.setScene(scene);
	editor.setTitle("RED");
	editor.show();

	codeArea.setWrapText(true);
	
	String classCode = non_static_af.loadCurrentData("currentTest");
	codeArea.setText(classCode);
	
	//Function to Button toGreen
	bt_toGreen.setOnAction(new EventHandler<ActionEvent>() {
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
	
	bt_toRed.setOnAction(new EventHandler<ActionEvent>() {
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

	  bt_Refactor.setOnAction(new EventHandler<ActionEvent>() {
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
       bt_toRed.setVisible(true);
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
	
	  bt_RfctrDone.setOnAction(new EventHandler<ActionEvent>() {
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
   
  


 }


}
