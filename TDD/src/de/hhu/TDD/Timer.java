package de.hhu.TDD;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Timer implements Runnable{

	private int seconds = 180;
	private boolean running = true;
	private Label label;
	private TextArea codeArea;
	private Button toRed;
	private boolean goBack;
	
	public Timer(Label label,TextArea codeArea,Button bt_toRed){
		this.label = label;
		this.codeArea = codeArea;
		this.toRed = bt_toRed;
		goBack = false;
	}
	@Override
	public void run() {
		
		try{
			while(running){
				if (seconds==1 && goBack){
					seconds = 180;
					codeArea.setText(restoreTest()); // when time is over , restores to starting point
					toRed.fire();
					
				}	
				if (seconds == 1){
					seconds = 180;
					codeArea.setText(restoreTest());
				}
				Platform.runLater(new Runnable(){

					@Override
					public void run() {
						label.setText("Sie haben noch " + seconds + " Sekunde,"
								+"\num Ihre Lösung zu implementieren.");
						
					}
					
				});
				seconds--;
				Thread.sleep(1000);
			}
		} catch (Exception e) { }
	}
	
	public void start(){
		running = true;
		seconds = 180;
	}
	
	private String restoreTest(){
		String test = "import static org.junit.Assert.*;"
					+"\nimport org.junit.Test;"
					+"\npublic class RomanNumberConverterTest {"
					+"\n@Test"
					+"\npublic void testSomething() {"
					+"\n}"
					+"\n}";
		return test;
	}

	public int getTime(){
		return seconds;
	}
	
	public void goBackOn(){
		goBack = true;
	}
	
	public void goBackOff(){
		goBack = false;
	}
	public void stop(){
		this.running = false;
		seconds = 180;
		label.setText("Sie haben noch " + seconds + " Sekunde,"
				+"\num Ihre Lösung zu implementieren.");
	}
}