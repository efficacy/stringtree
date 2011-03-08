package org.stringtree.xmlevents;

import java.util.Map;

public class XMLEventRecord {

	private XMLEvent event;
	private History<String> history;
	private Map<String,String> args;
	private int line;
	private int column;

	public XMLEventRecord(XMLEvent event, History<String> history, Map<String,String> args, int line, int column) {
		this.event = event;
		this.history = history;
		this.args = args;
		this.line = line;
		this.column = column;
	}

	public XMLEvent getEvent() {
		return event;
	}

	public History<String> getHistory() {
		return history;
	}

	public Map<String,String> getArgs() {
		return args;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

}
