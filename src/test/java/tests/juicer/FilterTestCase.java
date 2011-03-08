package tests.juicer;

import junit.framework.TestCase;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.finder.StringFinder;

public abstract class FilterTestCase extends TestCase {
    
	protected StringFinder fac;
	protected Fetcher context;

	public void setUp() {
		context = new MapFetcher();
		fac = createFormatter();
	}

	protected abstract StringFinder createFormatter();

	protected void check(String name, String expected, String source, boolean log) {
		Object result = fac.get(source);
		if (log) {
			System.out.println(name + ":\n  '" + source + "'->'" + result + "' expected (" + expected + ")");
		}
		assertEquals(name, expected, result);
	}

	protected void check(String name, String expected, String source) {
		check(name, expected, source, false);
	}
}


