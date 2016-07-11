package logic;

import java.util.Collection;

import vk.core.api.CompileError;

public class TrackingInfo {

	private double time;
	private String stage;
	private Collection<CompileError> errors;
	
	public TrackingInfo(double time,String stage , Collection<CompileError> errors){
		this.time = time;
		this.stage = stage;
		this.errors = errors;
	}
	@Override
	public String toString(){
		String errorsString = "";
		for(CompileError error : errors){
			errorsString += error.getMessage();
		}
		return time + " " + stage + " " + errorsString;
	}
}
