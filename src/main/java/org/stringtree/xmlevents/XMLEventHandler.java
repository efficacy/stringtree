package org.stringtree.xmlevents;

import java.util.Map;

public interface XMLEventHandler {
	Object handle(XMLEvent event, History<String> history, Map<String, String> args, Object context, int line, int column);
}
