package fr.yamas.model;

import java.util.Map;
import java.util.TreeMap;

public abstract class Factory {
	
	protected Map<Integer, ElementCreator> creators = new TreeMap<Integer, ElementCreator>();
	
	public abstract Factory getDefault();
	
	public void registre(int code, ElementCreator creator) {
		creators.put(code, creator);
	}
	
	public void unRegistre(int code) {
		creators.remove(code);
	}
	
	public Element create(int code) {
		ElementCreator creator = creators.get(code);
		if(creator != null) {
			return creator.create();
		}
		creator = creators.get(0);
		if (creator != null) {
			return creator.create();
		}
		throw new RuntimeException("Code non trouvé");
	}
}
