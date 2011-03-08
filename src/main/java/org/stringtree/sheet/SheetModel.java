package org.stringtree.sheet;

public interface SheetModel {
	void put(String r, String c, String value);
	String get(String r, String c);
}
