// MIT License, check LICENSE.txt in the src folder for full text

package main.java.logic;

import java.util.ArrayList;

import vk.core.api.CompileError;

public class TrackingStore {

	private ArrayList<TrackingInfo> store = new ArrayList<>();
	
	public void add(TrackingInfo tracking){
		store.add(tracking);
	}
	
	public ArrayList<TrackingInfo> getStoredItems(){
		return store;
	}
	
	@Override
	public String toString(){
		String text = "";
		for(TrackingInfo trInfo : store){
			text += "\n" + trInfo.toString();
		}
		return text;
	}
	
}
