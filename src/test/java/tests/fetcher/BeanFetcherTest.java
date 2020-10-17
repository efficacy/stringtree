package tests.fetcher;

import junit.framework.TestCase;

import org.stringtree.Fetcher;
import org.stringtree.Repository;
import org.stringtree.Tract;
import org.stringtree.fetcher.BeanFetcher;
import org.stringtree.fetcher.MapFetcher;

import tests.TestBean;

public class BeanFetcherTest extends TestCase {

	Repository context;
    Fetcher sf;

    @Override public void setUp() {
    	context = new MapFetcher();
        sf = new BeanFetcher(new SimpleBean(), context);
    }

    public void testBeanStringFetcher() {
        sf = new BeanFetcher(new TestBean());
        assertEquals("get missing property", null, sf.getObject("hello"));
        assertEquals("get object property", "world", sf.getObject("ms"));
        assertEquals("get primitive property", Integer.valueOf(321), sf.getObject("mi"));
        assertEquals("get this", "A Test Bean", sf.getObject(Tract.CONTENT).toString());
    }

    public void testBeanBooleans() {
        assertEquals("get boolean bean property", Boolean.TRUE, sf.getObject("boolD"));
        assertEquals("get Boolean bean property", Boolean.FALSE, sf.getObject("boolE"));
    }

    public void testRawBooleans() {
        assertEquals("get boolean bean property", Boolean.TRUE, sf.getObject("isBoolD"));
        assertEquals("get boolean bean property", Boolean.FALSE, sf.getObject("getBoolD"));
        assertEquals("get Boolean bean property", Boolean.TRUE, sf.getObject("isBoolE"));
        assertEquals("get Boolean bean property", Boolean.FALSE, sf.getObject("getBoolE"));
    }

    public void testMemberShadowing() {
        assertEquals("get method shadowing public field", "method", sf.getObject("stone"));
        assertEquals("is method shadowing public field", Boolean.FALSE, sf.getObject("blood"));
    }

    public void testMethodWithNoParameter() {
        assertEquals("get method with single parameter", "property", sf.getObject("noArgs"));
        assertEquals("get method with single parameter", "method", sf.getObject("noArgs()"));
    }

    public void testMethodWithSymbolParameter() {
    	context.put("a1", "xx");
        assertEquals("get method with symbol parameter", "single[xx]", sf.getObject("onestring(a1)"));
    }

    public void testMethodWithLiteralParameter() {
        assertEquals("get method with literal parameter", "single[yy]", sf.getObject("onestring('yy')"));
    }

    public void testMethodWithTwoSymbolParameters() {
    	context.put("a1", "xx");
    	context.put("a2", "yy");
        assertEquals("get method with two symbol parameters", "double[xx][yy]", sf.getObject("two(a1,a2)"));
    }

    public void testMethodWithMixedParameters() {
    	context.put("a1", "xx");
    	context.put("a2", 12);
        assertEquals("get method with two symbol parameters", "si[xx][12]", sf.getObject("two(a1,a2)"));
    }

    public void testMethodWithNonStringParameters() {
    	context.put("a1", 3);
    	context.put("a2", 12);
        assertEquals("get method with two symbol parameters", "ii[3][12]", sf.getObject("two(a1,a2)"));
    }

    public void testMethodReturn() {
        assertTrue("conditional method return", (Boolean)sf.getObject("test('Frank')"));
        assertFalse("conditional method return", (Boolean)sf.getObject("test('Margaret')"));
        context.put("name", "frank");
        assertTrue("conditional method return", (Boolean)sf.getObject("test(name)"));
    }
}
