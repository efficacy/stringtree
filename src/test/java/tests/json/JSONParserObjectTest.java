package tests.json;

import java.util.HashMap;
import java.util.Map;

public class JSONParserObjectTest extends JSONReaderObjectTest {
    
    JSONParserHelper helper;
    
    public void setUp() {
        helper = new JSONParserHelper();
    }
    
    protected Object read(String text) {
        helper.parse(text);
        Map<String,Object> ret = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : helper.context.entrySet()) {
            if (entry.getKey().startsWith("/")) {
                ret.put(entry.getKey().substring(1), entry.getValue());
            }
        }
        return ret;
    }

    public void testNestedArray() {
        helper.parse("{\"first\":[123,345]}");
        assertEquals(2, helper.get("/first[#]"));
        assertEquals(123, ((Number)helper.get("/first[0]")).intValue());
        assertEquals(345, ((Number)helper.get("/first[1]")).intValue());
    }

    public void testNestedEmptyArray() {
        helper.parse("{\"first\":[]}");
        assertEquals(0, helper.get("/first[#]"));
    }

    public void testNestedMaps() {
        helper.parse("{  \"first\"  :  { \"abc\":\"def\", \"123\":\"789x\" } ,  \"second\"  :  \"456x\"  }");
        assertEquals("def", helper.get("/first/abc"));
        assertEquals("789x", helper.get("/first/123"));
        assertEquals("456x", helper.get("/second"));
    }

}
