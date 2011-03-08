package org.stringtree.sheet;

import java.util.HashMap;
import java.util.Map;

public class MultipleMapSheetModel implements SheetModel {

	private Map<String, Map<String, String>> rows;
	
	public MultipleMapSheetModel() {
		rows = new HashMap<String, Map<String,String>>();
	}
	
	public String get(String r, String c) {
		Map<String, String> row = rows.get(r);
		if (null == row) return null;
		return row.get(c);
	}

	public void put(String r, String c, String value) {
		Map<String, String> row = rows.get(r);
		if (null == row) {
			row = new HashMap<String, String>();
			rows.put(r, row);
		}
		row.put(c, value);
	}
}
