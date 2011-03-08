package tests.json;

import java.util.ArrayList;
import java.util.List;

public class JSONWriterArrayTest extends JSONTestCase {

    public void testEmpty() {
        assertEquals("[]", write(new Object[] {}));
    }

    public void testSingle() {
        assertEquals("[\"hello\"]", write(new Object[] { "hello" }));
    }

    public void testSequence() {
        assertEquals("[\"hello\",\"there\"]", write(new Object[] {
                "hello", "there" }));
    }

    public void testNested() {
        assertEquals("[\"hello\",[\"123\",\"456\"]]", 
                write(new Object[] { "hello", new Object[] { "123", "456" } }));
    }

    public void testList() {
        List<Object> list = new ArrayList<Object>();
        list.add("hello");
        list.add(Integer.valueOf(123));
        assertEquals("[\"hello\",123]", write(list));
    }

    public void testTyped() {
        assertEquals("[\"hello\"]", write(new String[] { "hello" }));
    }

    public void testPrmitive() {
        assertEquals("[123,456]", write(new int[] { 123, 456 }));
    }
}
