package tests.json;

import java.util.ArrayList;
import java.util.List;

import org.stringtree.util.IntegerNumberUtils;

public class JSONParserArrayTest extends JSONReaderArrayTest {
    
    JSONParserHelper helper;
    
    public void setUp() {
        helper = new JSONParserHelper();
    }
    
    protected Object read(String text) {
        helper.parse(text);
        List<Object> ret = new ArrayList<Object>();
        int n = IntegerNumberUtils.intValue(helper.get("[#]"));
        for (int i = 0; i < n; ++i) {
            ret.add(i, helper.get("[" + i + "]"));
        }
        return ret;
    }

    public void testNestedArray() {
        helper.parse("[123,[\"hello\",17.52]]");
        assertEquals(2, helper.context.get("[#]"));
        assertInteger(123, helper.context.get("[0]"));
        assertEquals(2, helper.context.get("[1][#]"));
        assertEquals("hello", helper.context.get("[1][0]"));
        assertDouble(17.52, helper.context.get("[1][1]"));
    }

    public void testNestedMixedArray() {
        helper.parse("{ \"stuff\" : [\"Name\",{ \"key\" : \"value\" }]}");
        assertEquals(2, helper.context.get("/stuff[#]"));
        assertEquals("Name", helper.context.get("/stuff[0]"));
        assertEquals("value", helper.context.get("/stuff[1]/key"));
    }
}
