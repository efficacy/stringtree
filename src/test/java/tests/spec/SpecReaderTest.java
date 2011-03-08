package tests.spec;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.stringtree.fetcher.MapFetcher;
import org.stringtree.finder.FetcherStringFinder;
import org.stringtree.finder.MapStringFinder;
import org.stringtree.finder.StringFinder;
import org.stringtree.util.spec.SpecReader;
import org.stringtree.util.testing.Checklist;

@SuppressWarnings("unchecked")
public class SpecReaderTest extends TestCase {

    static final String BASEDIR = "testfiles/specdir/";
    
    MapFetcher repos;
    StringFinder context;
    DummyWithoutInit dummy1;
    DummyWithInit dummy2;
    DummyWithOtherInit dummy3;
    DummyWithConstructor dummy4;
    DummyWithBoth dummy5;

    public void setUp() {
        repos = new MapFetcher();
        context = new FetcherStringFinder(repos);

        dummy1 = new DummyWithoutInit();
        dummy2 = new DummyWithInit();
        dummy2.value = "ugh";
        dummy3 = new DummyWithOtherInit();
        dummy3.value = "whatever";
        dummy4 = new DummyWithConstructor("hello there");
        dummy5 = new DummyWithBoth("other.value");
        dummy5.value = "whatever";
    }

    public void testBasic() throws IOException {
        SpecReader.load(context, BASEDIR + "basic.spec");
        assertEquals("value1", context.getObject("colon"));
        assertEquals("value2", context.getObject("colon space"));
        assertEquals("value3", context.getObject("colon 2 space"));
        assertEquals("value4", context.getObject("equal"));
        assertEquals("value5", context.getObject("equal space"));
        assertEquals("value6", context.getObject("equal 2 space"));
    }

    public void testQuoted() throws IOException {
        SpecReader.load(context, BASEDIR + "quoted.spec");

        assertEquals("value1", context.getObject("colon"));
        assertEquals("value2", context.getObject("colon space"));
        assertEquals("value3", context.getObject("colon 2 space"));
        assertEquals("value4", context.getObject("equal"));
        assertEquals("value5", context.getObject("equal space"));
        assertEquals("value6", context.getObject("equal 2 space"));
    }

    public void testArray() throws IOException {
        SpecReader.load(context, BASEDIR + "array.spec");

        assertTrue(new Checklist<String>( "one" ).check(
                (List) context.getObject("single")));

        assertTrue(new Checklist<String>( "one", "two" ).check(
                (List) context.getObject("double")));

        assertTrue(new Checklist<String>( "one", "two" ).check(
                (List) context.getObject("spaces")));

        assertTrue(new Checklist<String>( "one", "two", "three" ).check(
                (List) context.getObject("trailing")));

        assertTrue(new Checklist<String>( 
                "AL","AK","AS","AZ","AR","CA","CO","CT","DE","DC","FM","FL","GA","GU","HI","ID","IL","IN","IA","KS","KY","LA","ME","MH","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","MP","OH","OK","OR","PW","PA","PR","RI","SC","SD","TN","TX","UT","VT","VI","VA","WA","WV","WI","WY"
            ).check((List) context.getObject("states")));
    }

    public void testCSV() throws IOException {
        SpecReader.load(context, BASEDIR + "csv.spec");

        assertTrue(new Checklist<String>( "one" ).check(
                (List) context.getObject("single")));

        assertTrue(new Checklist<String>( "one", "two" ).check(
                (List) context.getObject("double")));

        assertTrue(new Checklist<String>( "one", "two" ).check(
                (List) context.getObject("spaces")));

        assertTrue(new Checklist<String>( "one", "two", "three" ).check(
                (List) context.getObject("trailing")));

        assertTrue(new Checklist<String>( "one two",  " three " ).check(
                (List) context.getObject("quotes")));
    }

    public void testCreateWithoutInit() throws IOException {
        SpecReader.load(context, BASEDIR + "create.spec");
        DummyClass d = (DummyClass) context.getObject("one");
        assertEquals(dummy1, d);
        assertEquals(0, d.requests);
    }        

    public void testCreateWithInit() throws IOException {
        SpecReader.load(context, BASEDIR + "create.spec");
        DummyClass d = (DummyClass) context.getObject("two");
        assertEquals(dummy2, d); 
        assertEquals(1, d.requests);
    }
    
    public void testCreateWithOtherInit() throws IOException {
        SpecReader.load(context, BASEDIR + "create.spec");
        DummyClass d = (DummyClass) context.getObject("three");
        assertEquals(dummy3, d); 
        assertEquals(1, d.requests);
    }
    
    public void testCreateWithConstructor() throws IOException {
        SpecReader.load(context, BASEDIR + "create.spec");
        DummyClass d = (DummyClass) context.getObject("four");
        assertEquals(dummy4, d); 
        assertEquals(0, d.requests);
    }
    
    public void testCreateWithBoth() throws IOException {
        SpecReader.load(context, BASEDIR + "create.spec");
        DummyClass d = (DummyClass) context.getObject("five");
        assertEquals(dummy5, d);
        assertEquals(1, d.requests);
    }

    public void testLink() throws IOException {
        SpecReader.load(context, BASEDIR + "link.spec");

        assertEquals("original", context.getObject("source"));
        assertEquals("original", context.get("link"));
        repos.put("source", "changed");
        assertEquals("changed", context.getObject("source"));
        assertEquals("changed", context.get("link"));
    }

    public void testArrayCreate() throws IOException {
        SpecReader.load(context, BASEDIR + "arraycreate.spec");

        assertTrue(new Checklist<DummyClass>( dummy1, dummy2, dummy3 ).check(
                (List) context.getObject("array")));
    }

    public void testIndirect() throws IOException {
        SpecReader.load(context, BASEDIR + "indirect.spec");

        assertEquals(" hello there ", context.getObject("file"));
        assertEquals("more stuff", context.getObject("http"));
    }

    public void testResourceIndirect() throws IOException {
        SpecReader.load(context, BASEDIR + "rsindirect.spec");

        assertEquals("this is another file, plugh", context.get("fil"));
        assertEquals("this is a file, xyzzy", context.get("jar"));
        assertEquals("hi there", context.get("dir"));
        assertTrue(context.getObject("json") instanceof Map);
    }

    public void testArrayIndirect() throws IOException {
        SpecReader.load(context, BASEDIR + "arrayindirect.spec");

        assertTrue(new Checklist<String>( " hello there ", "more stuff" ).check(
                (List) context.getObject("array")));
    }

    public void testImportCreate() throws IOException {
        SpecReader.load(context, BASEDIR + "importcreate.spec");

        assertTrue(new Checklist<DummyClass>( dummy1, dummy2, dummy3 ).check(
                (List) context.getObject("array")));
    }

    public void testLookupValue() {
        SpecReader.load(context, new String[] { "aa=hello", "bb=goodbye",
                "CONTEXT=aa", "cc^=CONTEXT" });

        assertEquals("hello", context.get("cc"));
    }

    public void testAdd() {
        SpecReader.load(context, new String[] { 
                "aa[]= hello world",
                "aa += goodbye"
        });

        assertEquals("[hello, world, goodbye]", context.get("aa"));

        SpecReader.load(context, new String[] { "bb += goodbye", });

        assertEquals("[goodbye]", context.get("bb"));
    }

    public void testRemove() {
        SpecReader.load(context, new String[] { 
                "aa[]= hello world",
                "aa -= hello"
        });

        assertEquals("[world]", context.get("aa"));
    }

    public void testOptional() {
        repos.put("xx", "blah");
        repos.put("bb", "hello world");
        SpecReader.load(context, new String[] { 
                "cc=goodbye",
                "aa?=whatever",
                "bb?=thing",
                "cc?=huh"
        });

        assertEquals("blah", context.get("xx"));
        assertEquals("whatever", context.get("aa"));
        assertEquals("hello world", context.get("bb"));
        assertEquals("goodbye", context.get("cc"));
    }
    
    public void testFileUrlInclude() throws IOException {
        SpecReader.load(context, BASEDIR + "fileinclude.spec");

        assertEquals("from before include", context.get("aa"));
        assertEquals("from included spec", context.get("bb"));
        assertEquals("from after include", context.get("cc"));
    }
    
    public void testResourceInclude() throws IOException {
        SpecReader.load(context, BASEDIR + "rsinclude.spec");

        assertEquals("from before include", context.get("aa"));
        assertEquals("from included spec", context.get("bb"));
        assertEquals("from after include", context.get("cc"));
    }
    
    public void testMap() throws IOException {
        SpecReader.load(context, BASEDIR + "map.spec");

        Map<String, Object> map = (Map<String, Object>)context.getObject("m");
        StringFinder finder = new MapStringFinder(map);
        assertNotNull(map);
        assertEquals("some stuff", finder.get("a"));
        assertEquals("hello", finder.get("b"));
        assertEquals("tail", finder.get("longer name"));
    }
    
	public void testJSON() {
        SpecReader.load(context, new String[] { 
                "aa%=true",
                "bb%=123",
                "cc%=\"hello\"",
                "dd%={\"this\":\"that\"}",
                "ee%=[123,456]",
                "ff%@=file:testfiles/json/a.json",
                "fg%@=classpath:json/a.json",
        });

        assertEquals(Boolean.TRUE, context.getObject("aa"));
        assertEquals(Long.valueOf(123), context.getObject("bb"));
        assertEquals("hello", context.getObject("cc"));
        assertTrue(context.getObject("dd") instanceof Map);
        assertEquals("that", ((Map)context.getObject("dd")).get("this"));
        assertTrue(context.getObject("ee") instanceof List);
        assertEquals(Long.valueOf(123), ((List)context.getObject("ee")).get(0));
        assertEquals(Long.valueOf(456), ((List)context.getObject("ee")).get(1));
        assertEquals("hello", ((List)context.getObject("ff")).get(0));
        assertEquals("there", ((List)context.getObject("ff")).get(1));
        assertEquals("Frank", ((List)context.getObject("fg")).get(0));
        assertEquals("Margaret", ((List)context.getObject("fg")).get(1));
    }
    
    public void testCombinations() {
        SpecReader.load(context, new String[] { 
                "aa$=java.lang.String hello",
                "bb$\"=  \"java.lang.String hello\"",
                "cc$=java.util.ArrayList",
                "cc+=hello",
                "cc+=there",
                "cc+$=java.lang.String whatever"
        });
        
        assertEquals("hello", context.getObject("aa"));
        assertEquals("hello", context.getObject("bb"));
        Object cc = context.getObject("cc");
        
        assertTrue(cc instanceof List);
        List cl = (List)cc;
        assertEquals(3, cl.size());
        assertEquals("hello", cl.get(0));
        assertEquals("there", cl.get(1));
        assertEquals("whatever", cl.get(2));
    }
}
