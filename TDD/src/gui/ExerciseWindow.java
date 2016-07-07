//Das hier ist das Fenster, in dem man seine Übungen tippt.

package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Hilfe;
import logic.Loader;
import logic.Timer;
import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;

public class ExerciseWindow extends GridPane {
	private String css = this.getClass().getResource("/layout/style.css").toExternalForm();
	private Stage stage;
	private Timer timer;
	private boolean firstStart = true;
	private CompilationUnit compileTest;
	private JavaStringCompiler compiler;
	private CompilationUnit compileClass;
	
	
	ExerciseWindow(Stage stage, Loader loader, int exc_auswahl,boolean isBabystepOn,int secondsBabystep) {
		this.stage = stage;
		
		Label instruction = new Label("//implementieren Sie den Code hier");
		TextArea codeArea = new TextArea();
		codeArea.setId("code_area");
		codeArea.setWrapText(true);
		String classCode = loader.loadCurrentData("currentTest");
		codeArea.setText(classCode);
		
		Button bt_toGreen = new Button("Wechsel zu Green");
		bt_toGreen.setId("bt_toGreen");
		Button bt_toRed = new Button("Zurueck zu Red");
		bt_toRed.setId("bt_toRed");
		Button bt_Refactor = new Button("Refactor");
		Button bt_help = new Button("Hilfe");
		Button bt_RfctrDone = new Button("Refactoren beendet");
		Button bt_backExc =  new Button("Zurueck zum Auswahlmenue");
		
		
		//proof if babyStep is chosen
		if(isBabystepOn){
		Label timeRemaining = new Label();
		this.add(timeRemaining, 1, 4);
		// Thread that makes the timer for Babysteps
		
		timer = new Timer(timeRemaining,codeArea,bt_toRed,secondsBabystep);
		Thread thread = new Thread(timer);
		thread.start();
		}
		bt_Refactor.setVisible(false);
		bt_toRed.setVisible(false);
		bt_RfctrDone.setVisible(false);
		
		// war vorher setConstraints, habs nur zeitweilig zu add geaendert
		this.setId("stage_red");
		this.add(instruction, 1, 1);
		this.add(codeArea, 1, 2);
		this.add(bt_toGreen, 1, 3);
		this.add(bt_help, 1, 5);
		this.add(bt_toRed,1,3);
		this.add(bt_Refactor,1,6);
		this.add(bt_RfctrDone,1,7);
		this.add(bt_backExc, 1, 30);
		//root.getChildren().addAll(instruction,codeArea,bt_toGreen,bt_help,bt_Refactor,bt_RfctrDone,bt_toRed,bt_backExc);
		
		bt_backExc.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				Scene scene = new Scene(new MenuControls(stage), 700, 600);
				
				scene.getStylesheets().addAll(css);
				stage.setScene(scene);
			}
		});
		
		//Function to Button toGreen
		bt_toGreen.setOnAction(new EventHandler<ActionEvent>() { //Wechsel von RED zu GREEN
			@Override public void handle(ActionEvent e) {
				stage.setTitle("GREEN");
				
				String testCode = codeArea.getText(); // here is the test from user
				loader.save("currentTest",testCode);
				if(isBabystepOn){
				//timer.stop(); // stop and reset the timer
				timer.goBackOn();
				timer.start();
				}
				
				String testName  = loader.Aufgaben_Verwaltung.get(exc_auswahl).testName();
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
							String classCode = loader.loadCurrentData("currentClass");
							codeArea.setText(classCode);	 
						}
					} catch(NullPointerException k){
						bt_help.setVisible(false);
						bt_RfctrDone.setVisible(false);
						bt_toGreen.setVisible(false);
						bt_toRed.setVisible(true);
						bt_Refactor.setVisible(true);
						String classCode = loader.loadCurrentData("currentClass");
						codeArea.setText(classCode);
						System.out.println("Fehler beim Kompilieren, bitte beheben!");
					}
				}
				
				if(firstStart==true){
					bt_help.setVisible(false);
					bt_RfctrDone.setVisible(false);
					bt_toGreen.setVisible(false);
					bt_toRed.setVisible(true);
					bt_Refactor.setVisible(true);
					String classCode = loader.loadCurrentData("currentClass");
					codeArea.setText(classCode);
					firstStart=false;
				}
			}
		});
		
		//Function to Button Help
		bt_help.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Hilfe.displayRED();
			}
		});
		
		bt_toRed.setOnAction(new EventHandler<ActionEvent>() {   //Wechsel von GREEN zu RED
			@Override
			public void handle(ActionEvent e) {
				bt_toGreen.setVisible(true);
				bt_help.setVisible(true);
				bt_toRed.setVisible(false);
				bt_Refactor.setVisible(false);
				
				stage.setTitle("RED");
				if(isBabystepOn){
				timer.goBackOff();
				timer.start();
				}
				String classCode = codeArea.getText();
				String className = loader.Aufgaben_Verwaltung.get(exc_auswahl).className();
				CompilationUnit tmp_compileClass = new CompilationUnit(className,classCode,false); 
				compileClass = tmp_compileClass;
				
				classCode = loader.loadCurrentData("currentTest");
				codeArea.setText(classCode);
			}
		});
		
		bt_Refactor.setOnAction(new EventHandler<ActionEvent>() {   //Wechsel von GREEN zu Refactor
			@Override
			public void handle(ActionEvent e) {
				try{	  
					String classCode = codeArea.getText();		  
					String className = loader.Aufgaben_Verwaltung.get(exc_auswahl).className();
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
					}
				} catch(NullPointerException s){
					System.out.println("Kompillierungsschwierigkeiten, beheben Sie diese" + " vor dem Refactoren!");
				}
			}
		});
		
		bt_RfctrDone.setOnAction(new EventHandler<ActionEvent>() { //Vom fertigen Refactor zu RED
			@Override public void handle(ActionEvent e) {	
				String classCode = codeArea.getText();		  
				String className = loader.Aufgaben_Verwaltung.get(exc_auswahl).className();
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
					
					loader.save("currentClass", classCode);
					classCode = loader.loadCurrentData("currentTest");
					codeArea.setText(classCode);
				}
			}
		});
	}
}
