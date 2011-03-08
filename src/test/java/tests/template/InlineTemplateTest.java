/*
 * Copyright (c) iO 2006
 * 
 * Created on 13-Oct-2006
 */
package tests.template;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.stringtree.finder.MapStringFinder;
import org.stringtree.template.InlineTemplater;
import org.stringtree.util.ObjectToString;
import org.stringtree.util.URLUtils;

public class InlineTemplateTest extends TestCase {
	
	InlineTemplater templater;
	Map<String, Object> values;
	
	public void setUp() {
		values = new HashMap<String, Object>();
		templater = new InlineTemplater(new MapStringFinder(values));
	}

	public void testEmpty() {
		assertEquals("", templater.expand(""));
	}

	public void testConstant() {
		assertEquals("hello", templater.expand("hello"));
	}

	public void testMissingValue() {
		assertEquals("hello ", templater.expand("hello ${name}"));
	}

	public void testPresentValue() {
		values.put("name", "Frank");
		assertEquals("hello Frank", templater.expand("hello ${name}"));
	}

	public void testConditionalPresentValue() {
		values.put("name", "Frank");
		assertEquals("hello Me", templater.expand("hello ${name?'Me':'Nobody'}"));
	}

	public void testConditionalMissingValue() {
		assertEquals("hello Nobody", templater.expand("hello ${name?'Me':'Nobody'}"));
	}

	public void testConditionalPresentDefaultValue() {
		values.put("name", "Frank");
		assertEquals("hello Frank", templater.expand("hello ${name?name:'Nobody'}"));
	}

	public void testConditionalMissingDefaultValue() {
		assertEquals("hello Nobody", templater.expand("hello ${name?name:'Nobody'}"));
	}

	public void testWithoutEscaping() {
		values.put("name", "a?b c");
		assertEquals("hello a?b c", templater.expand("hello ${name}"));
	}

	public void testWithEscaping() {
		templater.setStringConverter(new ObjectToString() {
		    public String convert(Object value) {
		    	if (null == value) return null;
		        return URLUtils.escape(value.toString());
		    }
		});
		values.put("name", "a?b c");
		assertEquals("hello a%3Fb+c", templater.expand("hello ${name}"));
	}
    
    public void testPipeExactMatch() {
        values.put("name", new FullName("Frank", "Carver"));
        values.put("string", "whatever");
        values.put("buf", new StringBuffer("thing"));
        values.put("example", new Example());
        
        assertEquals("hello '*whatever*'", templater.expand("hello '${string|example.bold}'"));
        assertEquals("hello '*thing*'", templater.expand("hello '${buf|example.bold}'"));
        assertEquals("hello '*Frank*'", templater.expand("hello '${name|example.bold}'"));
        
        assertEquals("hello '_whatever_'", templater.expand("hello '${string|example.underline}'"));
        assertEquals("hello '_thing_'", templater.expand("hello '${buf|example.underline}'"));
        assertEquals("hello '_Frank Carver_'", templater.expand("hello '${name|example.underline}'"));

        assertEquals("hello 'f*whatever*'", templater.expand("hello '${string|example.fbold}'"));
        assertEquals("hello 'f*thing*'", templater.expand("hello '${buf|example.fbold}'"));
        assertEquals("hello 'f*Frank*'", templater.expand("hello '${name.forename|example.fbold}'"));
        
        //assertEquals("hello 'k*whatever*'", templater.expand("hello '${string|example.kbold}'"));
        //assertEquals("hello 'k*thing*'", templater.expand("hello '${buf|example.kbold}'"));
        //assertEquals("hello 'k*Frank*'", templater.expand("hello '${name|example.kbold}'"));
    }
    
    public void testImplicitGet() {
        values.put("getter", new GetterObject());
        assertEquals("hello '[ugh]'", templater.expand("hello '${getter.ugh}'"));
    }
    
    public void testImplicitGetWithContext() {
        values.put("getter", new ContextGetterObject());
        values.put("ugh", "whatever");
        assertEquals("hello '[whatever]'", templater.expand("hello '${getter.ugh}'"));
    }
}
