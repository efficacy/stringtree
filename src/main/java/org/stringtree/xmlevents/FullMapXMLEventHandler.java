package org.stringtree.xmlevents;

import java.util.HashMap;
import java.util.Map;

import org.stringtree.util.IntegerNumberUtils;

public class FullMapXMLEventHandler extends HashMap<String, String> implements XMLEventHandler {

	public static final String COUNT = ".count";

	public Object handle(XMLEvent event, History<String> history, Map<String,String> args, Object context, int line, int column) {
		String path = history.toPath();
		int count = IntegerNumberUtils.intValue(get(path + COUNT), 0);
		
        if (event.equals(XMLEvent.XML)) {
            // System.err.println(" got xml event and ignored it args=" + args);
        } else if (event.equals(XMLEvent.COMMENT)) {
            // System.err.println(" got comment and ignored it args=" + args);
        } else if (event.equals(XMLEvent.TEXT)) {
			append(path, count, "", args.get(XMLEvent.KEY_VALUE));
		} else if (event.equals(XMLEvent.OPEN)) {
		    for (Map.Entry<String, String> entry : args.entrySet()) {
				append(path, count, "/@" + entry.getKey(), entry.getValue());
			}
		} else if (event.equals(XMLEvent.CLOSE)) {
			put(path + COUNT, Integer.toString(count+1));
		}
		return context;
	}

	private void append(String path, int count, String dest, String value) {
		if (0 == count) append(path + dest, value);
		append(path + "[" + count + "]" + dest, value);
	}

	private void append(String path, String value) {
		String old = get(path);
		if (null != old) {
			value = old + value;
		}
		put(path, value);
	}

}
