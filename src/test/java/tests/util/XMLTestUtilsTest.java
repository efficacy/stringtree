package tests.util;

import org.stringtree.util.testing.XMLTestUtils;

import junit.framework.TestCase;

public class XMLTestUtilsTest extends TestCase {
	
	public void testPatternMatching1() {
        XMLTestUtils.assertMatches("<hello/>", "<hello/>");
	}
	
	public void testPatternMatching2() {
        XMLTestUtils.assertAttribute("<price grossRetailPrice=\"200.0\"/>", 
        		"price", "grossRetailPrice", "200.0");
	}

	public void testPatternMatching3() {
        XMLTestUtils.assertAttribute("<price ugh=\"x\" grossRetailPrice=\"200.0\"/>",
        		"price", "grossRetailPrice", "200.0");
	}

	public void testPatternMatching4() {
        XMLTestUtils.assertAttribute("<wrap><price ugh=\"x\" grossRetailPrice=\"200.0\"/></wrap>",
        		"price", "grossRetailPrice", "200.0");
	}
	
	public void testXpathMatching() { 
        XMLTestUtils.assertXPath("<wrap>hello</wrap>",
        		"/wrap", "hello");
        XMLTestUtils.assertXPath("<wrap>hello</wrap>",
        		"wrap", "hello");
        XMLTestUtils.assertXPath("<wrap><price ugh=\"x\" grossRetailPrice=\"200.0\">cost</price></wrap>",
        		"//wrap/price", "cost");
        XMLTestUtils.assertXPath("<wrap><price ugh=\"x\" grossRetailPrice=\"200.0\">cost</price></wrap>",
        		"//price", "cost");
        XMLTestUtils.assertXPath("<wrap><price ugh=\"x\" grossRetailPrice=\"200.0\">cost</price></wrap>",
        		"//wrap/price/@grossRetailPrice", "200.0");
	}
}
