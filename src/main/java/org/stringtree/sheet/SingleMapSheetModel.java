package org.stringtree.sheet;

import java.util.HashMap;
import java.util.Map;

public class SingleMapSheetModel implements SheetModel {

	private Map<String, String> map;
	
	public SingleMapSheetModel() {
		map = new HashMap<String, String>();
	}
	
	public String get(String r, String c) {
		return map.get(key(r,c));
	}

	public void put(String r, String c, String value) {
		map.put(key(r,c), value);
	}

	private String key(String r, String c) {
		return r + "~" + c;
	}

}
