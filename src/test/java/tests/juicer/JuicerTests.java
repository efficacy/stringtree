package tests.juicer;

import org.stringtree.regex.Pattern;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JuicerTests extends TestCase {
    
	static boolean verbose = false;
	
	public JuicerTests(String name) {
		super(name);
	}

	public static TestSuite suite() {
		if (verbose) System.out.println("Juicer Tests using regex '" + Pattern.processor + "'");
		TestSuite ret = new TestSuite();

		ret.addTest(new TestSuite(UserLevelTest.class));

		ret.addTest(new TestSuite(ResourceExternalWikiFilterTest.class));
		ret.addTest(new TestSuite(FileExternalWikiFilterTest.class));
		ret.addTest(new TestSuite(StringExternalWikiFilterTest.class));

		ret.addTest(new TestSuite(WikiFilterTest.class));
		ret.addTest(new TestSuite(RegexFilterTest.class));

		ret.addTest(new TestSuite(TractFilterTest.class));
		ret.addTest(new TestSuite(TractSourceTest.class));

		ret.addTest(new TestSuite(StringFilterTest.class));
		ret.addTest(new TestSuite(StringSourceTest.class));

		return ret;
	}
}
