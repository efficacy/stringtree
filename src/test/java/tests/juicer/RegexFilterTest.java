package tests.juicer;

import junit.framework.TestCase;

import org.stringtree.Repository;
import org.stringtree.Tract;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.juicer.string.FactoryRegexReplaceStringFilter;
import org.stringtree.juicer.string.StaticRegexReplaceStringFilter;
import org.stringtree.juicer.string.StringFilter;
import org.stringtree.juicer.string.StringSource;
import org.stringtree.juicer.string.StringStringSource;
import org.stringtree.juicer.tract.RegexTokenFinderTractFilter;
import org.stringtree.juicer.tract.SequenceTractSource;
import org.stringtree.juicer.tract.StringTractSource;
import org.stringtree.juicer.tract.TokenHelper;
import org.stringtree.juicer.tract.TractFilter;
import org.stringtree.tract.MapTract;

public class RegexFilterTest extends TestCase {
    
	StringSource source;
	StringFilter sf;
	TractFilter tf;
	Tract t;

	private void createStringFilter(StringFilter newFilter, String input) {
		sf = newFilter;
		sf.connectSource(new StringStringSource(input));
	}

	private void createTractFilter(TractFilter newFilter, String input) {
		tf = newFilter;
		tf.connectSource(new StringTractSource(input));
	}
	
	public void testStaticStringReplace() {
		createStringFilter(new StaticRegexReplaceStringFilter("ab+","x"), "aba abba ababa");
		assertEquals("static Regex 1", "xa xa xxa", sf.nextString());
		assertEquals("static Regex, at end", null, sf.nextString());
	}
	
	public void testFactoryStringReplace() {
		Repository tokens = new MapFetcher();
		tokens.put("aba", "hello");
		tokens.put("abba", "wibble(bb)");
		tokens.put("ababa", "plug");
		
		createStringFilter(new FactoryRegexReplaceStringFilter("a(b+)a",tokens), "aba abba ababa");
		assertEquals("factory Regex 1", "hello wibble(bb) helloba", sf.nextString());
		assertEquals("factory Regex, at end", null, sf.nextString());
	}
	
	public void testFactoryStringReplace2() {
		Repository tokens = new MapFetcher();
		tokens.put("&0", "hello, world");
		tokens.put("&1", "hello");
		tokens.put("&2", "world");
		tokens.put("&&", "&");
		
		createStringFilter(new FactoryRegexReplaceStringFilter("&[0123456789&]+",tokens), "all(&0), first(&1) && last(&2) unknown(&12)");
		String nextString = sf.nextString();
        assertEquals("factory Regex 2", "all(hello, world), first(hello) & last(world) unknown()", nextString);
		assertEquals("factory Regex 2, at end", null, sf.nextString());
	}
	
	public void testPatternTokenFinder() {
		createTractFilter(new RegexTokenFinderTractFilter("([a-z]+)"), "hello");

		t = tf.nextTract();
		assertEquals("TokenFinder single word", "hello", t.getContent());
		assertTrue("TokenFinder single word token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder after", null, t);
	}

	public void testPatternTokenFinder2() {
		createTractFilter(new RegexTokenFinderTractFilter("([a-z]+)"), " hello, world!");

		t = tf.nextTract();
		assertEquals("TokenFinder before", " ", t.getContent());
		assertFalse("TokenFinder before token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder first word", "hello", t.getContent());
		assertTrue("TokenFinder first word token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder between", ", ", t.getContent());
		assertFalse("TokenFinder between token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder first word", "world", t.getContent());
		assertTrue("TokenFinder second word token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder after", "!", t.getContent());
		assertFalse("TokenFinder after token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder at end", null, t);
	}
	
	public void testPatternTokenFinder3() {
		tf = new RegexTokenFinderTractFilter("([a-z]+)");
		tf.connectSource(new SequenceTractSource(
		new Tract[] {
			new MapTract(" hello,"),
			new MapTract(" "),
			new MapTract("world!")
		}));

		t = tf.nextTract();
		assertEquals("TokenFinder before", " ", t.getContent());
		assertFalse("TokenFinder before token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder first word", "hello", t.getContent());
		assertTrue("TokenFinder first word token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder between", ",", t.getContent());
		assertFalse("TokenFinder between token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder between", " ", t.getContent());
		assertFalse("TokenFinder between token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder first word", "world", t.getContent());
		assertTrue("TokenFinder second word token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder after", "!", t.getContent());
		assertFalse("TokenFinder after token?", TokenHelper.isToken(t));

		t = tf.nextTract();
		assertEquals("TokenFinder at end", null, t);
	}
}

/* 		check("wiki block 8", "<pre>\nabc\n</pre>ugh<pre>\ndef\n</pre>",	"[\nabc\n]ugh[\ndef\n]");
 */
