package settings;

import java.util.*;

public class Settings {
	
	private ArrayList<Setting> settings;
	
	public Settings(){
		settings = new ArrayList<Setting>();
	}
	
	public void add(Setting s){
		settings.add(s);
	}
}
