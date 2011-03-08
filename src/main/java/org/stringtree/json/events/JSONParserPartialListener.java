package org.stringtree.json.events;

import java.util.HashMap;
import java.util.Map;


public class JSONParserPartialListener extends JSONParserMapListener {

    private Map<String, PrefixMatchHandler> handlers;

    public JSONParserPartialListener(Map<String, Object> context) {
        super(context);
        handlers = new HashMap<String, PrefixMatchHandler>();
    }

    protected void addArrayCount(int count) {
        // do nothing, allow overwrites
    }

    protected void addArrayPrefix(StringBuffer ret, int index) {
        // do nothing, allow overwrites
    }

    private void checkHandler(String prefix) {
        PrefixMatchHandler handler = handlers.get(prefix);
        if (null != handler) handler.handle(prefix, map);
    }

    public void direct(Object value) {
        super.direct(value);
        checkHandler(prefix());
    }

    public void number(Number value) {
        super.number(value);
        checkHandler(prefix());
    }

    public void string(String value) {
        super.string(value);
        checkHandler(prefix());
    }

    public void addHandler(String prefix, PrefixMatchHandler handler) {
        handlers.put(prefix, handler);
    }

}
