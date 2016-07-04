package de.hhu.TDD;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Timer implements Runnable{

	private int seconds = 180;
	private boolean running = true;
	private Label label;
	private TextArea codeArea;
	
	public Timer(Label label,TextArea codeArea){
		this.label = label;
		this.codeArea = codeArea;
	}
	@Override
	public void run() {
		
		try{
			while(running){
				if (seconds==1){
					running = false;
					codeArea.setText(restoreTest()); // when time is over , restores to starting point
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
	
	public void stop(){
		this.running = false;
		seconds = 180;
		label.setText("Sie haben noch " + seconds + " Sekunde,"
				+"\num Ihre Lösung zu implementieren.");
	}
}