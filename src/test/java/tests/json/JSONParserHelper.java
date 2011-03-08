package tests.json;

import java.util.HashMap;
import java.util.Map;

import org.stringtree.json.events.JSONParser;

public class JSONParserHelper {
    public RecordingJSONParserMapListener listener;
    public Map<String, Object> context;
    public JSONParser parser;
    
    public JSONParserHelper() {
        context = new HashMap<String, Object>();
        listener = new RecordingJSONParserMapListener(context);
        parser = new JSONParser(listener, context);
    }
    
    public void parse(String text) {
        parser.parse(text);
    }
    
    public Object get(String key) {
        return context.get(key);
    }
}
