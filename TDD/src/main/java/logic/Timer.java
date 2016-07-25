// MIT License, check LICENSE.txt in the src folder for full text

package main.java.logic;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Timer implements Runnable{

	private int seconds;
	private boolean running = true;
	private Label label;
	private TextArea codeArea;
	private Button toRed;
	private boolean goBack;
	private int secondsRemain;
	
	public Timer(Label label,TextArea codeArea,Button bt_toRed,int seconds){
		this.label = label;
		this.codeArea = codeArea;
		this.toRed = bt_toRed;
		goBack = false;
		this.seconds = seconds;
	}
	@Override
	public void run() {
		
		try{
			while(running){
				if (secondsRemain==0 && goBack){
					secondsRemain = seconds;
					codeArea.setText(restoreTest()); // when time is over , restores to starting point
					Platform.runLater(toRed::fire);
					
				}	
				else if (secondsRemain == 0){
					secondsRemain = seconds;
					codeArea.setText(restoreTest());
				}else {
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							
							label.setText("Sie haben noch " + time(secondsRemain) 
									+"\num Ihre Lösung zu implementieren.");
						}
					
					});
				secondsRemain--;
				Thread.sleep(1000);
				}
			}
		} catch (Exception e) { }
	}
	
	public void start(){
		running = true;
		secondsRemain = seconds;
	}
	
	public static String time(int secondsToConvert){
		String minutes = String.valueOf(secondsToConvert / 60);
		String sec = String.valueOf(secondsToConvert % 60);
		if(secondsToConvert == 60){
			sec = "0";
		}
		String time;
		if(secondsToConvert / 60 == 0){
			time = sec + " Sekunde";
		}
		else if(secondsToConvert / 60 == 1){
			time = minutes + " Minute und " + sec + " Sekunde";
		}else time = minutes + " Minuten und "  + sec + " Sekunde";
		return time;
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

	public void goBackOn(){
		goBack = true;
	}
	
	public void goBackOff(){
		goBack = false;
	}
	public void stop(){
		this.running = false;
		secondsRemain = seconds;
		label.setText("Sie haben noch " + time(secondsRemain) 
			+"\num Ihre Lösung zu implementieren.");
	}
}