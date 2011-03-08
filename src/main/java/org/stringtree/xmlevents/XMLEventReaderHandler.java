package org.stringtree.xmlevents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.stringtree.util.StringUtils;

public class XMLEventReaderHandler implements XMLEventHandler {

    private Map<String, Object> parameters;
    private Stack<Object> stack;
    private Object result;

    public XMLEventReaderHandler(Map<String, Object> parameters) {
        this.parameters = parameters;
        stack = new Stack<Object>();
    }

    @SuppressWarnings("unchecked")
    public Object handle(XMLEvent event, History<String> history, Map<String, String> args, Object context, int line, int column) {
        Map<String, Object> map = (Map<String, Object>)context;
System.err.println("handle event=" + event + " history=" + history.history() + " args=" + args + " map=" + map);

        String leaf = peek(history);
        Object obj = null != map ? map.get(leaf) : null;
        Map<String, Object> child = map;

        if (event.equals(XMLEvent.OPEN)) {
            if (obj instanceof Map) {
                child = (Map<String, Object>)obj;
            } else if (null != obj) {
                child = new HashMap<String, Object>();
                child.put("text()", obj);
                map.put(leaf, child);
            }
            for (Map.Entry<String, String> entry : args.entrySet()) {
                add(child, (String)parameters.get(XMLEventReader.KEY_ATTR_PREFIX) + entry.getKey(), entry.getValue());
            }
            stack.push(context);
            context = child;
        } else if (event.equals(XMLEvent.TEXT)) {
            String value = trim(args.get(XMLEvent.KEY_VALUE));
System.err.println("text key=" + leaf + " value=" + value);

            if (!StringUtils.isBlank(value)) {
                add(map, leaf, value);
            }
        } else if (event.equals(XMLEvent.CLOSE)) {
            if (allow(history)) {
                result = context;
                context = stack.isEmpty() ? null : stack.pop();
            }
        } else if (event.equals(XMLEvent.END)) {
            context = result;
System.err.println("result " + result);
        }
        
        return null == context ? new HashMap() : context;
    }

    public static String peek(History<String> history) {
        Stack<String> s = history.history();
        return (s.isEmpty())
            ? null
            : (String)s.peek();
    }

    private String trim(String value) {
        if (null != value && Boolean.TRUE.equals(parameters.get(XMLEventReader.KEY_TRIM_CDATA))) {
            value = value.trim();
        }
        return value;
    }

    private boolean allow(History<String> history) {
        boolean ret = history.depth() > 1 || !Boolean.TRUE.equals(parameters.get(XMLEventReader.KEY_IGNORE_ROOT));
//System.err.println("allow (" + ret + ") history=" + history.history() + " params=" + parameters);
        return ret;
        
    }

    @SuppressWarnings("unchecked")
    private void add(Map<String, Object> map, String name, Object value) {
        Object child = map.get(name);
        if (null == child) {
            if (!(value instanceof Collection) && !Boolean.TRUE.equals(parameters.get(XMLEventReader.KEY_ALLOW_SINGLES))) {
                Collection<Object> collection = new ArrayList<Object>();
                collection.add(value);
                value = collection;
            }
System.err.println("add (null==child) put(" + name + "," + value + ")");
            map.put(name, value);
        } else if (child instanceof Collection) {
            ((Collection<Object>)child).add(value);
System.err.println("add (collection=" + child + ") add(" + value + ")");
        } else {
            Collection<Object> collection = new ArrayList<Object>();
            collection.add(child);
            collection.add(value);
            map.put(name, collection);
System.err.println("add (else) put(" + name + "," + collection + ")");
        }
    }
}
