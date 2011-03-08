package org.stringtree.util.testing;

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import junit.framework.Assert;

public class XMLTestUtils {
    
	private static DocumentBuilder db = null; 
	private static XPath xp = XPathFactory.newInstance().newXPath();
	
	static synchronized DocumentBuilder db() {
	    if (null != db) return db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
        return db;
	}

	public static void assertContains(String xml, String text) {
		Assert.assertTrue(xml.contains(text));
	}

    public static void assertMatches(String xml, Pattern pattern) {
    	Matcher matcher = pattern.matcher(xml);
    	Assert.assertTrue(matcher.matches());
	}

    public static void assertMatches(String xml, String pattern) {
    	Assert.assertTrue(xml.matches(pattern));
	}

    public static void assertElement(String xml, String element, String value) {
    	assertXPath(xml, "//" + element, value);
	}

    public static void assertAttribute(String xml, String element, String name, String value) {
    	assertXPath(xml, "//" + element + "/@" + name, value);
	}
	
    public static void assertNoElement(String xml, String element, String value) {
		Assert.assertFalse(xml.contains("<" + element + ">" + value + "</" + element + ">"));
	}
    
    public static void assertXPath(String xml, String pattern, String value) {
		String ret = null;
		try {
			ret = xp.evaluate(pattern, new InputSource(new StringReader(xml)));
		} catch (XPathExpressionException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(value, ret);
    }

    public static Document parse(String xml) {
		Document dom = null;
		
		try {
			dom = db.parse(new InputSource(new StringReader(xml)));
		}catch(Exception e) {
			Assert.fail(e.getMessage());
		}
		
		return dom;
	}
}
