package org.stringtree.sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListSheetModel implements SheetModel {
	private List<List<String>> cells;
	private Map<String, Integer> colNames;
	private Map<String, Integer> rowNames;
	private int rx;
	private int cx;

	public ListSheetModel() {
		cells = new ArrayList<List<String>>();
		colNames = new HashMap<String, Integer>();
		rowNames = new HashMap<String, Integer>();
		rx = 0;
		cx = 0;
	}

	public String get(String r, String c) {
		Integer rownumber = rowNames.get(r);
		if (null == rownumber) return null;
		List<String> row = cells.get(rownumber.intValue());
		if (null== row) return null;
		
		Integer colnumber = colNames.get(c);
		if (null == colnumber) return null;
		return row.get(colnumber.intValue());
	}

	public void put(String r, String c, String value) {
		if (!rowNames.containsKey(r)) {
			rowNames.put(r, Integer.valueOf(rx++));
			cells.add(new ArrayList<String>(colNames.keySet()));
		}
		Integer rownumber = rowNames.get(r);
        if (null == rownumber) return;
		List<String> row = cells.get(rownumber.intValue());

		if (!colNames.containsKey(c)) {
			colNames.put(c, Integer.valueOf(cx++));
			row.add("");
		}
		
		Integer colnumber = colNames.get(c);
		if (null == colnumber) return;
		row.set(colnumber.intValue(), value);
	}
}
	