package tests.template;

import java.io.File;

import junit.framework.TestCase;

import org.stringtree.finder.MapStringKeeper;
import org.stringtree.finder.StringKeeper;
import org.stringtree.template.TemplaterHelper;
import org.stringtree.template.TreeTemplater;

public class TreeTemplaterTest extends TestCase {
	StringKeeper context;
	TreeTemplater tt;
	
	public void setUp() {
		context = new MapStringKeeper();
		context.put("name", "Frank");
		tt = new TreeTemplater("testfiles/tt1/");
	}

	private String exp(String templateName) {
		return TemplaterHelper.expand(tt, context, templateName);
	}
	
	public void testConstructionWithMissingDirThrows() {
		try {
			tt = new TreeTemplater(new File("testfiles/missing/"));
			fail("TreeTemplater constructor for missing dir should throw");
		} catch(IllegalArgumentException iae) {
			// nothing to see here
		}
	}
	
	public void testMissingTemplateGivesEmpty() {
		assertEquals("", exp("missing"));
	}
	
	public void testTopLevelTemplateJustExpands() {
		assertEquals("hello Frank", exp("simple"));
	}
	
	public void testSubLevelTemplateWithNoWrappersJustExpands() {
		assertEquals("Frank, it works!", exp("plain.plain_and_simple"));
	}
	
	public void testSubLevelTemplateCanReferToTopLevel() {
		assertEquals("hello Frank, it works!", exp("plain.ref"));
	}
	
	public void testSubLevelTemplateCanReferToSubLevel() {
		assertEquals("hi Frank", exp("plain.sref"));
	}
	
	public void testPrologueOnly() {
		assertEquals("<Frank", exp("p.simple"));
	}
	
	public void testEpilogueOnly() {
		assertEquals("Frank>", exp("e.simple"));
	}
	
	public void testPrologueEpilogue() {
		assertEquals("<Frank>", exp("pe.simple"));
	}
	
	public void testDoubleDepth() {
		assertEquals("{<Frank>}", exp("dd.twig.simple"));
	}
	
	public void testPrologueEpilogueReference() {
		assertEquals("<_hello_ <Frank>>", exp("pe.refex"));
	}
	
	public void testTract() {
		assertEquals("<Margaret>", exp("rename"));
	}
	
	public void testTractPrologueEpilogue() {
		assertEquals("<head><title>It Works</title></head><body>hello Frank</body>", exp("pt.simple"));
	}
	
	public void testClasspathURL() {
		tt = new TreeTemplater("classpath:tt2/");
		assertEquals("Frank from the classpath", exp("simple"));
	}
}
