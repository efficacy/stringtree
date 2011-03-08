package tests.json;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.stringtree.json.events.JSONParser;
import org.stringtree.json.events.JSONParserPartialListener;

public class JSONPartialParserTests extends TestCase {
    JSONParser parser;
    Map<String, Object> context;
    JSONParserPartialListener listener;
    MockPrefixMatchHandler handler;
    
    public void setUp() {
        context = new HashMap<String, Object>();
        listener = new JSONParserPartialListener(context);
        parser = new JSONParser(listener, context);
        handler = new MockPrefixMatchHandler();
    }
    
    public void testEmpty() {
        listener.addHandler("/a", handler);
        parser.parse("");
        handler.assertNotCalled("handle");
    }
    
    public void testNotMatching() {
        listener.addHandler("/a", handler);
        parser.parse("{\"b\":123}");
        handler.assertNotCalled("handle");
    }
    
    public void testMatching() {
        listener.addHandler("/a", handler);
        parser.parse("{\"a\":123}");
        handler.assertCalled("handle");
    }
    
    public void testMatchingArray() {
        listener.addHandler("/a", handler);
        parser.parse("{\"a\":[ \"b\", \"c\", \"d\" ]}");
        handler.assertCalled(3, "handle");
    }
    
    public void testHeaderBody() {
        listener.addHandler("/target", handler);
        parser.parse("{\"from\":123, \"to\":456, \"target\":[ \"b\", \"c\", \"d\" ]}");
        handler.assertCalled(3, "handle");
    }
    
    public void testNestedArray() {
        listener.addHandler("/c/d", handler);
        parser.parse("{\"c\":[ { \"a\":false, \"d\": [1,2,3] }, { \"a\":true, \"d\":[5,6,7] }]");
        handler.assertCalled(6, "handle");
    }
}
