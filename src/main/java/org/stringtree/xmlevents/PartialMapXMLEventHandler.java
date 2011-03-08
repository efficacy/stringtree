package org.stringtree.xmlevents;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PartialMapXMLEventHandler implements XMLEventHandler, XMLCharacterHandler {

    private Map<String, StanzaMatcher> matchers;
    private boolean gatherInner; 

    private Map<String, Object> map;
	private Map<String, Object> contents;
	
	public PartialMapXMLEventHandler(Map<String, StanzaMatcher> matchers, boolean gatherInner) {
        this.matchers = matchers;
        this.gatherInner = gatherInner;
		map = new HashMap<String, Object>();
		contents = new HashMap<String, Object>();
	}
	
	public Object handle(XMLEvent event, History<String> history, Map<String,String> args, Object context, int line, int column) {
		String path = history.toPath();
		if (event.equals(XMLEvent.XML)) {
            //System.err.println(" got xml event and ignored it args=" + args);
		} else if (event.equals(XMLEvent.COMMENT)) {
            //System.err.println(" got comment and ignored it args=" + args);
		} else if (event.equals(XMLEvent.TEXT)) {
			map.put(path, args.get(XMLEvent.KEY_VALUE));
		} else if (event.equals(XMLEvent.OPEN)) {
		    for (Map.Entry<String, String> entry : args.entrySet()) {
		        map.put(path + "@" + entry.getKey(), entry.getValue());
		    }
			contents.put(path, new StringBuffer());
		} else if (event.equals(XMLEvent.CLOSE)) {
		    if (gatherInner && history.depth() > 0) {
		        StringBuffer buf = (StringBuffer) contents.get(path);
                String inner = buf.toString();
                String leaf = XMLEventReaderHandler.peek(history);
                String close = "</" + leaf + ">";
                if (inner.endsWith(close)) { // TODO should really account for unlikely optional spaces
                    inner = inner.substring(0, inner.lastIndexOf("<"));
                }
		        map.put(path + ".inner", inner);
		    }
			StanzaMatcher matcher = matchers.get(path);
			if (null != matcher) {
				context = matcher.match(path, map, context);
				removeStartingWith(path);
			}
		}
		return context;
	}

    public void handle(History<String> history, char c, int line, int column) {
        if (!gatherInner) return;
        
        if (history.depth() > 0) {
            StringBuffer buf = new StringBuffer();
            Iterator<String> it = history.history().iterator();
            while (it.hasNext()) {
                String entry = (String)it.next();
                buf.append('/');
                buf.append(entry);
                String label = buf.toString();
                ((StringBuffer)contents.get(label)).append(c);
            }
        }
    }

	protected void removeStartingWith(String prefix) {
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
			if (((String)entry.getKey()).startsWith(prefix)) {
				it.remove();
			}
		}
	}
}
