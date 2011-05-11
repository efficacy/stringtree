package tests.json;

import java.util.List;

@SuppressWarnings("rawtypes")
public class JSONReaderArrayTest extends JSONTestCase {
	List array;

    private List get(String string) {
        array = array(read(string));
        return array;
    }

    private List array(Object obj) {
        assertTrue(obj instanceof List);
        return (List) obj;
    }

    public void testEmptyArray() {
        assertEquals(0, get("[]").size());
    }

    public void testSingleNumber() {
        assertEquals(1, get("[123]").size());
        assertInteger(123, array.get(0));
    }

    public void testSingleNumberWithSpaces() {
        assertEquals(1, get("[ 123 ]").size());
        assertInteger(123, array.get(0));
    }

    public void testSingleString() {
        assertEquals(1, get("[\"123x\"]").size());
        assertEquals("123x", array.get(0));
    }

    public void testTwoNumbers() {
        assertEquals(2, get("[123,456]").size());
        assertInteger(123, array.get(0));
        assertInteger(456, array.get(1));
    }

    public void testTwoStrings() {
        assertEquals(2, get("[\"123x\",\"456y\"]").size());
        assertEquals("123x", array.get(0));
        assertEquals("456y", array.get(1));
    }

    public void testTwoNumbersWithSpaces() {
        assertEquals(2, get("[ 123 , 456 ]").size());
        assertInteger(123, array.get(0));
        assertInteger(456, array.get(1));
    }

    public void testNestedArray() {
        assertEquals(2, get("[123,[\"hello\",17.52]]").size());
        assertInteger(123, array.get(0));
        List child = array(array.get(1));
        assertEquals(2, child.size());
        assertEquals("hello", child.get(0));
        assertDouble(17.52, child.get(1));
    }

    public void testDirect() {
        List list = (List)read("[true,false,null]");
        assertEquals(3, list.size());
        assertEquals(Boolean.TRUE, list.get(0));
        assertEquals(Boolean.FALSE, list.get(1));
        assertEquals(null, list.get(2));
    }
}
