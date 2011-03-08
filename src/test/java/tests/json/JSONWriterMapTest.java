package tests.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.stringtree.json.JSONValidatingWriter;

public class JSONWriterMapTest extends JSONTestCase {
    
    Map<String, Object> map = new HashMap<String, Object>();

    private void assertMap(String expected) {
        assertEquals(expected, write(map));
    }

    public void testEmpty() {
        assertMap("{}");
    }

    public void testSimple() {
        map.put("hello", "world");
        assertMap("{\"hello\":\"world\"}");
    }

    public void testMultiple() {
        map.put("hello", "world");
        map.put("goodbye", "cruel");
        assertMap("{\"hello\":\"world\",\"goodbye\":\"cruel\"}");
    }

    public void testMixed() {
        map.put("hello", "world");
        map.put("goodbye", Integer.valueOf(123));
        assertMap("{\"hello\":\"world\",\"goodbye\":123}");
    }

    public void testNestedMap() {
        Map<String, Object> child = new HashMap<String, Object>();
        child.put("whetever", "abc");
        child.put("X Y", Boolean.FALSE);
        map.put("hello", "world");
        map.put("goodbye", child);
        assertMap("{\"hello\":\"world\",\"goodbye\":{\"X Y\":false,\"whetever\":\"abc\"}}");
    }

    public void testNestedArray() {
        int[] child = new int[] { 123, 456 };
        map.put("hello", "world");
        map.put("goodbye", child);
        assertMap("{\"hello\":\"world\",\"goodbye\":[123,456]}");
    }

    public void testNestedEmptyArray() {
        int[] child = new int[] { };
        map.put("hello", "world");
        map.put("goodbye", child);
        assertMap("{\"hello\":\"world\",\"goodbye\":[]}");
    }

    public void testBeanProperties() {
        assertEquals("{\"class\":\"class tests.json.SizeBean\",\"size\":13}",
                writer.write(new SizeBean()));
    }

    public void testOpaqueObject() {
        assertEquals("{\"class\":\"class java.lang.Object\"}", writer
                .write(new Object()));
    }

    public void testPublicMembers() {
        assertEquals(
                "{\"class\":\"class tests.json.InvisibleBean\",\"visible\":false}",
                writer.write(new InvisibleBean()));
    }

    public void testMembersAndAccessors() {
        assertEquals(
                "{\"class\":\"class tests.json.InvisibleSizeBean\",\"size\":13,\"visible\":false}",
                writer.write(new InvisibleSizeBean()));
    }
    
    public void testSelfReferentialObject() {
        assertEquals("{\"class\":\"class tests.json.SelfReferential\",\"first\":\"one\",\"second\":null}", write(new SelfReferential()));
    }
    
    public void testMapWithoutClassName() {
        writer = new JSONValidatingWriter(false);
        assertEquals("{\"first\":\"one\",\"second\":null}", write(new SelfReferential()));
    }
    
    public void testBeanCyclicReference() {
        CyclicReference ref1 = new CyclicReference();
        CyclicReference ref2 = new CyclicReference();
        CyclicReference ref3 = new CyclicReference();
        
        ref1.setOther(ref2);
        ref2.setOther(ref3);
        ref3.setOther(ref1);

        writer = new JSONValidatingWriter(false);
        assertEquals("{\"other\":{\"other\":{\"other\":null}}}", write(ref1));
    }
    
    public void testMapCyclicReference() {
        Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        Map<String, Object> map3 = new HashMap<String, Object>();
        
        map1.put("link", map2);
        map2.put("link", map3);
        map3.put("link", map1);

        writer = new JSONValidatingWriter(false);
        assertEquals("{\"link\":{\"link\":{\"link\":null}}}", write(map1));
    }
    
    public void testArrayCyclicReference() {
        Collection<Object> c1 = new ArrayList<Object>();
        Collection<Object> c2 = new ArrayList<Object>();
        Collection<Object> c3 = new ArrayList<Object>();
        
        c1.add("c1");c1.add(c2);
        c2.add("c2");c2.add(c3);
        c3.add("c3");c3.add(c1);

        writer = new JSONValidatingWriter(false);
        assertEquals("[\"c1\",[\"c2\",[\"c3\",null]]]", write(c1));
    }
}
