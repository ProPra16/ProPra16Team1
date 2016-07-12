package logic;

import java.util.Collection;

import vk.core.api.CompileError;

public class TrackingInfo {

	private int time;
	private String stage;
	private Collection<CompileError> errors;
	
	public TrackingInfo(int time,String stage){
		this.time = time;
		this.stage = stage;
	}
	
	public void addErrors(Collection<CompileError> errors){
		this.errors = errors;
	}
	@Override
	public String toString(){
		String errorsString = "";
		for(CompileError error : errors){
			errorsString += "\n" + error.getMessage();
		}
		return time + " " + stage + " " + errorsString;
	}
	
	public Collection<CompileError> getErrors(){
		return errors;
	}
}
