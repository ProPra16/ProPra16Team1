package logic;

public class Tracking {

	private long startTime;
	private long endTime;
	
	public void start(){
		startTime = System.currentTimeMillis();
	}
	
	public void stop(){
		endTime = System.currentTimeMillis();
	}
	
	public double getTime(){
		return (endTime - startTime) / 1000.0;
	}
}
