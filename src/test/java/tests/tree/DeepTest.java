package tests.tree;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.stringtree.util.FileReadingUtils;

@SuppressWarnings("unchecked")
public class DeepTest extends XMLTestCase {

    public void setUp() {
        super.setUp();
        strict = true;
    }
    
    public void testEmpty() {
        parse("");
        assertMapSize(0);
    }
    
    public void testSingleField() {
        parse("<ugh>thing</ugh>");
        assertMapSize(1);
        assertContents("thing", "ugh");
    }
    
    public void testTrimming() {
        parse("trimming", "<ugh>  thing\n \n\t</ugh>", true);
        assertMapSize(1);
        assertContents("thing", "ugh");
    }
    
    public void testNotTrimming() {
        parse("trimming", "<ugh>  thing\n \n\t</ugh>", false);
        assertMapSize(1);
        assertContents("  thing\n \n\t", "ugh");
    }
    
    public void testTwoFields() {
        parse("<ugh>thing</ugh><colour>blue</colour>");
        assertMapSize(2);
        assertContents("thing", "ugh");
        assertContents("blue", "colour");
    }

    public void testMixedContent() {
        parse("mixed", "<ugh><colour>blue</colour>whatever</ugh>");
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertType(String.class, "ugh/colour");
        assertContents("blue", "ugh/colour");
        assertContents("whatever", "ugh/text()");
    }

    public void testSequencedFields() {
        parse("sequenced", "<ugh><colour>blue</colour><colour>green</colour></ugh>");
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertType(Collection.class, "ugh/colour");
        assertContents("blue", "ugh/colour", 0);
        assertContents("green", "ugh/colour", 1);
    }

    public void testNestedFields() {
        parse("nested", "<ugh><colour>blue</colour></ugh>");
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertType(String.class, "ugh/colour");
        assertContents("blue", "ugh/colour");
    }

    public void testSequencedMixedContent() {
        parse("sequenced mixed", "<ugh>\nbefore\n<colour>blue</colour>\nbetween\n<colour>green</colour>after</ugh>");
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertType(Collection.class, "ugh/colour");
        assertContents("blue", "ugh/colour", 0);
        assertContents("green", "ugh/colour", 1);
    }

    public void testAttributes() {
        parse("attributes", "<ugh colour=\"blue\" size='small'></ugh>");
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertType(String.class, "ugh/@colour");
        assertContents("blue", "ugh/@colour");
        assertType(String.class, "ugh/@size");
        assertContents("small", "ugh/@size");
    }

    public void testAttributesWithoutPrefix() {
        reader.setAttributePrefix("");
        parse("attributes", "<ugh colour=\"blue\" size='small'></ugh>");
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertType(String.class, "ugh/colour");
        assertContents("blue", "ugh/colour");
        assertType(String.class, "ugh/size");
        assertContents("small", "ugh/size");
    }

    public void testSingletonTag() {
        parse("attributes", "<ugh colour=\"blue\" size='small'/>");
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertType(String.class, "ugh/@colour");
        assertContents("blue", "ugh/@colour");
        assertType(String.class, "ugh/@size");
        assertContents("small", "ugh/@size");
    }

    public void testAttributesAndNesting() {
        parse("attributes and nesting", "<ugh colour=\"blue\" size='small'><name>Frank</name><name>Carver</name></ugh>");
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertType(String.class, "ugh/@colour");
        assertContents("blue", "ugh/@colour");
        assertType(String.class, "ugh/@size");
        assertContents("small", "ugh/@size");
        assertType(Collection.class, "ugh/name");
        assertContents("Frank", "ugh/name", 0);
        assertContents("Carver", "ugh/name", 1);
    }
    
    public void testProcessingDirective() {
        String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ugh>thing</ugh>";
        reader.setIncludeProcessingDirectives(false);
        Map map = (Map)reader.read(text);
        assertEquals(1, map.size());
        assertEquals("thing", map.get("ugh"));

        reader.setIncludeProcessingDirectives(true);
        map = (Map)reader.read(text);
        assertEquals(2, map.size());
        assertEquals("thing", map.get("ugh"));
        assertTrue(Map.class.isInstance(map.get("?xml")));

        Map xml = (Map)map.get("?xml");
        assertEquals(2, xml.size());
        assertEquals("1.0", xml.get("@version"));
        assertEquals("UTF-8", xml.get("@encoding"));
    }

    public void testSimpleIgnoreRoot() {
        reader.setIgnoreRoot(true);
        parse("simple ignore", "<ugh><colour>blue</colour></ugh>");
        assertMapSize(1);
        assertType(String.class, "colour");
        assertContents("blue", "colour");
    }

    public void testSequencedIgnoreRoot() {
        reader.setIgnoreRoot(true);
        
        parse("sequenced ignore", "<ugh><colour>blue</colour><colour>green</colour></ugh>");
        assertMapSize(1);
        assertType(Collection.class, "colour");
        assertContents("blue", "colour", 0);
        assertContents("green", "colour", 1);
    }

    public void testSequenceWithoutSinglesOrRoot() {
        reader.setAllowSingles(false);
        reader.setIgnoreRoot(true);
        
        parse("sequenced", "<ugh><colour>blue</colour><colour>green</colour></ugh>");
        assertListSize(1);
        List colour = (List)((Map)list1.get(0)).get("colour"); 
        assertEquals("blue", colour.get(0));
        assertEquals("green", colour.get(1));
    }

    public void testMixedWithoutSingles() {
        reader.setAllowSingles(false);

        parse("mixed", "<ugh><colour>blue</colour>whatever</ugh>");
        assertListSize(1);
        Map root = (Map)list1.get(0);
        List ugh = (List)root.get("ugh");
        List colour = (List)((Map)ugh.get(0)).get("colour");
        assertEquals("blue", colour.get(0));
    }

    public void testMixedWithoutSinglesOrRoot() {
        reader.setAllowSingles(false);
        reader.setIgnoreRoot(true);

        parse("mixed", "<ugh><colour>blue</colour>whatever</ugh>");
        assertListSize(1);
        List colour = (List)((Map)list1.get(0)).get("colour");
        assertEquals("blue", colour.get(0));
    }
    
    public void testWithDTD() {
        parse("dtd", FileReadingUtils.readFile("testfiles/xml/withdtd.xml"));
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertType(Collection.class, "ugh/colour");
        assertContents("blue", "ugh/colour", 0);
        assertContents("green", "ugh/colour", 1);
    }
    
    public void testWithExclamations() {
        parse("exclamations", "<ugh type=\"!sky\">blue!&!;</ugh>");
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertContents("blue!", "ugh/text()");
        assertContents("!sky", "ugh/@type");
    }
    
    public void testWithEntityAndRogueSemicolons() {
        parse("exclamations", "<ugh type=\"sky&apos;s;\">blue &lt; green;</ugh>");
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertContents("blue < green;", "ugh/text()");
        assertContents("sky's;", "ugh/@type");
    }
    
    public void testWithNamespaces() {
        parse("exclamations", "<xx:ugh thing:type=\"!sky\">blue!</xx:ugh>");
        assertMapSize(1);
        assertType(Map.class, "xx:ugh");
        assertContents("blue!", "xx:ugh/text()");
        assertContents("!sky", "xx:ugh/@thing:type");
    }
    
    public void testStripNamespaces() {
        parse("exclamations", "<xx:ugh thing:type=\"!sky\">blue!</xx:ugh>", true, true);
        assertMapSize(1);
        assertType(Map.class, "ugh");
        assertContents("blue!", "ugh/text()");
        assertContents("!sky", "ugh/@type");
    }
    
    public void testCDATA() {
        parse("cdata", "<a><![CDATA[xx]]></a>");
        assertMapSize(1);
        assertContents("xx", "a");
    }
    
    public void testCDATAWithMarkup() {
        parse("cdata", "<a><![CDATA[<em>markup</em>]]></a>");
        assertMapSize(1);
        assertContents("<em>markup</em>", "a");
    }
    
    public void testUTF8LiteralHex() {
        parse("utf8", "<a>eggs and chips &#xa3;9.95</a>");
        assertMapSize(1);
        assertContents("eggs and chips \u00a39.95", "a");
    }
    
    public void testUTF8LiteralDecimal() {
        parse("utf8", "<a>eggs and chips &#163;9.95</a>");
        assertMapSize(1);
        assertContents("eggs and chips \u00a39.95", "a");
    }
    
    public void testUnicodeInline() {
        parse("utf8", "<a>eggs and chips \u00a39.95</a>");
        assertMapSize(1);
        assertContents("eggs and chips \u00a39.95", "a");
    }
}
