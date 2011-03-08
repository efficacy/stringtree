package org.stringtree.xmlevents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordingXMLEventHandler implements XMLEventHandler {

	public List<XMLEventRecord> events;
	
	public RecordingXMLEventHandler() {
		events = new ArrayList<XMLEventRecord>();
	}
	
	public Object handle(XMLEvent event, History<String> history, Map<String, String> args, Object context, int line, int col) {
		events.add(new XMLEventRecord(event, history, args, line, col));
		return null;
	}
	
	public void dump() {
		int n = events.size();
		for (int i = 0; i < n; ++i) {
			XMLEventRecord record = events.get(i);
			System.out.println("event(" + i + "@" + record.getLine() + ":" + record.getColumn() +") " + record.getEvent() + " history=" + record.getHistory().history() + " args=" + record.getArgs());
		}
	}
}
