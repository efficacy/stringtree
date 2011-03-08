package org.stringtree.json.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

enum Modes { raw, array, object, closing }

class ParserState {
    public final String prefix;
    public Modes mode;
    public int index;
    public String key;
    
    public ParserState(String prefix, Modes mode) {
        this.prefix = prefix;
        this.mode = mode;
        this.index = 0;
        this.key = "";
    }
    
    public String toString() {
    	return "ParserState[prefix=" + prefix + ",mode=" + mode + ",index=" + index + ",key=" + key + "]";
    }
}

class InitialParserState extends ParserState {
    public InitialParserState() {
        super("", Modes.raw);
    }
}

class ArrayParserState extends ParserState {
    public ArrayParserState() {
        super("", Modes.array);
    }
}

class ObjectParserState extends ParserState {
    public ObjectParserState() {
        super("/", Modes.object);
    }
}

public class JSONParserMapListener implements JSONParserListener {

    protected Map<String, Object> map;
    private Stack<ParserState> states;

    public JSONParserMapListener(Map<String, Object> map) {
        this.map = map;
        states = new Stack<ParserState>();
        states.push(new InitialParserState());
    }

    public JSONParserMapListener() {
        this.map = new HashMap<String, Object>();
    }
    
    public Map<String, Object> getMap() {
        return map;
    }
    
    public Object get(String key) {
        return map.get(key);
    }

    protected String prefix() {
        StringBuffer ret = new StringBuffer();
        for (ParserState state : states) {
            ret.append(state.prefix);
            if (Modes.array == state.mode) addArrayPrefix(ret, state.index);
            if (Modes.object == state.mode) addObjectPrefix(ret, state.key);
        }
        return ret.toString();
    }
    
    protected void inc() {
        if (Modes.array == states.peek().mode) {
            states.peek().index++;
        }
    }

    public void start(int start) {
        // do nothing
    }
    
    public void finish() {
        // do nothing
    }

    public void number(Number value) {
        map.put(prefix(), value);
        inc();
    }

    public void string(String value) {
        map.put(prefix(), value);
        inc();
    }

    public void direct(Object value) {
        map.put(prefix(), value);
        inc();
    }

    public void startArray() {
        states.push(new ArrayParserState());
    }

    public void finishArray() {
        int count = states.peek().index;
        states.pop();
        addArrayCount(count);
        inc();
    }

    public void startObject() {
        states.push(new ObjectParserState());
    }

    public void finishObject() {
        states.pop();
        inc();
    }

    public void key(String token) {
        states.peek().key = token;
    }

    protected void addArrayCount(int count) {
        map.put(prefix() + "[#]", count);
    }

    protected void addArrayPrefix(StringBuffer ret, int index) {
        ret.append("[");
        ret.append(index);
        ret.append("]");
    }

    protected void addObjectPrefix(StringBuffer ret, String key) {
        ret.append(key);
    }
}
