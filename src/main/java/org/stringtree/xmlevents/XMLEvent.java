package org.stringtree.xmlevents;

public class XMLEvent {
	private String name;

	private XMLEvent(String name) {
		this.name = name;
	}

    public static final XMLEvent XML = new XMLEvent("xml");
	public static final XMLEvent START = new XMLEvent("start");
	public static final XMLEvent DOCTYPE = new XMLEvent("doctype");
	public static final XMLEvent OPEN = new XMLEvent("open");
	public static final XMLEvent TEXT = new XMLEvent("text");
    public static final XMLEvent ENTITY = new XMLEvent("entity");
	public static final XMLEvent COMMENT = new XMLEvent("comment");
	public static final XMLEvent CLOSE = new XMLEvent("close");
	public static final XMLEvent END = new XMLEvent("end");
	
	public static final String KEY_VALUE = "~VALUE";
	public static final String KEY_SINGLETON = "~SINGLETON";
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Object obj) {
		return obj instanceof XMLEvent && name.equals(((XMLEvent)obj).name);
	}
	
	public int hashCode() {
		return - name.hashCode();
	}
}
