package tests.template;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;

import org.stringtree.Repository;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.template.EasyTemplater;
import org.stringtree.util.URLReadingUtils;

public class EasyTemplaterTest extends TestCase {
    Repository templates;
    EasyTemplater templater;
    
    public void setUp() {
        templates = new MapFetcher();
        templater = new EasyTemplater(templates);
    }
    
    public void testUnknownTemplateReturnsEmptyString() {
        assertEquals("", templater.toString("tpl1"));
    }
    
    public void testConstantTextTemplateReturnsContents() {
        templates.put("tpl1", "ugh");
        assertEquals("ugh", templater.toString("tpl1"));
    }
    
    public void testSimpleTokenTemplateExpandsValues() {
        templates.put("tpl1", "hello ${person}");
        templater.put("person", "Frank");
        assertEquals("hello Frank", templater.toString("tpl1"));
    }
    
    public void testMoreComplexTemplateExpandsValues() {
        templates.put("dfl", "Nobody");
        templates.put("tpl1", "hello ${person?:*dfl}");
        assertEquals("hello Nobody", templater.toString("tpl1"));
        templater.put("person", "Frank");
        assertEquals("hello Frank", templater.toString("tpl1"));
    }
    
    public void testIteration() {
        templates.put("person", "[${this}]");
        templates.put("ugh", "hello there, ${family*person/','}!");
        templater.put("family", Arrays.asList("Frank", "Margaret", "Elizabeth", "Katherine"));
        assertEquals("hello there, [Frank],[Margaret],[Elizabeth],[Katherine]!", templater.toString("ugh"));
    }
    
    public void testGetAsByteArray() {
        templates.put("tpl1", "ugh");
        assertTrue(Arrays.equals(new byte[] { 'u', 'g', 'h'}, templater.toBytes("tpl1")));
    }
    
    public void testStoredValuesCanBeReadBack() {
        templater.put("a", "xxx");
        assertEquals("xxx", templater.getObject("a"));
    }
    
    public void testTemplatesCanBeReadFromTheFileSystem() {
        templater = new EasyTemplater(new File("testfiles/easytemplater/"));
        templater.put("a", "world");
        assertEquals("hello, world", templater.toString("present"));
    }
    
    public void testTemplatesCanBeReadFromAURL() throws IOException {
        templater = new EasyTemplater(URLReadingUtils.findURL("classpath:easytemplater/"));
        templater.put("a", "farewell");
        assertEquals("goodbye, farewell", templater.toString("px"));
    }
    
    public void testTemplatesCanBeReadFromAnywhere() {
        templater = new EasyTemplater("testfiles/easytemplater/");
        templater.put("a", "world");
        assertEquals("hello, world", templater.toString("present"));

        templater = new EasyTemplater("classpath:easytemplater/");
        templater.put("a", "farewell");
        assertEquals("goodbye, farewell", templater.toString("px"));
    }
}
