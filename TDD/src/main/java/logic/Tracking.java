// MIT License, check LICENSE.txt in the src folder for full text

package main.java.logic;

public class Tracking {

	private long startTime;
	private long endTime;
	
	public void start(){
		startTime = System.currentTimeMillis();
	}
	
	public void stop(){
		endTime = System.currentTimeMillis();
	}
	
	public int getTime(){
		return (int) ((endTime - startTime) / 1000.0);
	}
}
