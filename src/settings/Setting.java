package settings;

import java.util.*;
import java.io.*;

public class Setting {
	
	private HashMap<Integer, String> map;
	
	private String name;
	private int currentSettingKey;
	
	public Setting(String name){
		
		map = new HashMap<Integer, String>();
		
		this.name = name;
		currentSettingKey = 1;
		
	}
	
	public void add(String optionName){
		map.put(map.size()+1,optionName);
	}
	
	public String getName(){
		return name;
	}
	
	public String getValueName(){
		return map.get(currentSettingKey);
	}
	
}