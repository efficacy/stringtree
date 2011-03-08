package tests.juicer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.stringtree.Repository;
import org.stringtree.Tract;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.juicer.JuicerConvertHelper;
import org.stringtree.juicer.JuicerLockHelper;
import org.stringtree.juicer.Rewindable;
import org.stringtree.juicer.convert.StringTractFunnel;
import org.stringtree.juicer.convert.TractStringFunnel;
import org.stringtree.juicer.string.StringSource;
import org.stringtree.juicer.string.StringStringSource;
import org.stringtree.juicer.tract.ExternalTractPipeline;
import org.stringtree.juicer.tract.FileTractSource;
import org.stringtree.juicer.tract.InitialisingTractPipeline;
import org.stringtree.juicer.tract.MapReplaceTractFilter;
import org.stringtree.juicer.tract.PassTractFilter;
import org.stringtree.juicer.tract.PoliteFilter;
import org.stringtree.juicer.tract.SequenceTractSource;
import org.stringtree.juicer.tract.StringTractSource;
import org.stringtree.juicer.tract.TokenFinderTractFilter;
import org.stringtree.juicer.tract.TokenHelper;
import org.stringtree.juicer.tract.TractFilter;
import org.stringtree.juicer.tract.TractPipeline;
import org.stringtree.juicer.tract.TractSource;
import org.stringtree.tract.MapTract;

public class TractFilterTest extends TestCase {
    
	Repository context;
	TractSource source;
	TractFilter filter;
	Tract t;

	public void setUp() {
		source = new FileTractSource("testfiles/tf1.txt");
		context = new MapFetcher();
	}

	public void testPass() {
		filter = new PassTractFilter();
		filter.connectSource(source);

		assertEquals("Pass, whole file", "line 1\r\nline 2\r\nthird and @final@ line", JuicerConvertHelper.nextString(filter));
	}

	public void testStringNarrow() {
		source = new StringTractSource("hello");
		TractStringFunnel funnel = new TractStringFunnel();
		funnel.connectSource(source);
		
		assertEquals("string narrow", "hello", funnel.nextString());
		assertEquals("string narrow after", null, funnel.nextString());
	}

	public void testStringWiden() {
		StringSource ss = new StringStringSource("hello");
		StringTractFunnel funnel = new StringTractFunnel();
		funnel.connectSource(ss);
		
		assertEquals("string narrow", "hello", JuicerConvertHelper.nextString(funnel));
		assertEquals("string narrow after", null, JuicerConvertHelper.nextString(funnel));
	}

	public void testTokenFinder() {
		filter = new TokenFinderTractFilter();
		filter.connectSource(source);

		t = filter.nextTract();
		assertEquals("TokenFinder before", "line 1\r\nline 2\r\nthird and ", t.getContent());
		assertFalse("TokenFinder before token?", TokenHelper.isToken(t));

		t = filter.nextTract();
		assertEquals("TokenFinder during", "final", t.getContent());
		assertTrue("TokenFinder during token?", TokenHelper.isToken(t));

		t = filter.nextTract();
		assertEquals("TokenFinder after", " line", t.getContent());
		assertFalse("TokenFinder after token?", TokenHelper.isToken(t));
		
		filter.connectSource(new StringTractSource("@hello@"));
		t = filter.nextTract();
		assertEquals("TokenFinder single token", "hello", t.getContent());
		assertTrue("TokenFinder single token?", TokenHelper.isToken(t));
	}

	public void testReplace() {
		source = new SequenceTractSource(new Tract[] {
			new MapTract() { { setContent("hello"); } },
			new MapTract() { { setContent("hello"); JuicerLockHelper.lock(this); } },
			new MapTract() { { setContent("hello"); } }
		});

		Map<String, String> map = new HashMap<String, String>();
		map.put("hello", "there");
		filter = new MapReplaceTractFilter(map, false);
		filter.connectSource(source);

		assertEquals("String", "there", JuicerConvertHelper.nextString(filter));
		assertEquals("String", "there", JuicerConvertHelper.nextString(filter));
		assertEquals("String", "there", JuicerConvertHelper.nextString(filter));
		assertEquals("String, at end", null, filter.nextTract());

		((Rewindable)source).rewind();
		filter = new PoliteFilter(new MapReplaceTractFilter(map, false));
		filter.connectSource(source);
		assertEquals("String", "there", JuicerConvertHelper.nextString(filter));
		assertEquals("String", "hello", JuicerConvertHelper.nextString(filter));
		assertEquals("String", "there", JuicerConvertHelper.nextString(filter));
		assertEquals("String, at end", null, filter.nextTract());

		((Rewindable)source).rewind();
		filter = new MapReplaceTractFilter(map);
		filter.connectSource(source);
		assertEquals("String", "there", JuicerConvertHelper.nextString(filter));
		assertEquals("String", "hello", JuicerConvertHelper.nextString(filter));
		assertEquals("String", "there", JuicerConvertHelper.nextString(filter));
		assertEquals("String, at end", null, filter.nextTract());
	}
	
	public void testPipeline() {
		TractPipeline filter = new TractPipeline();
		List<TractFilter> list = new ArrayList<TractFilter>();
		list.add(new PassTractFilter());
		filter.setList(list);
		filter.connectSource(source);

		assertEquals("Pipeline Pass, whole file", "line 1\r\nline 2\r\nthird and @final@ line", JuicerConvertHelper.nextString(filter));

		((Rewindable)source).rewind();
		list.add(new TokenFinderTractFilter());
		filter.relink();

		t = filter.nextTract();
		assertEquals("Pipeline TokenFinder before", "line 1\r\nline 2\r\nthird and ", t.getContent());
		assertFalse("Pipeline TokenFinder before token?", TokenHelper.isToken(t));

		t = filter.nextTract();
		assertEquals("Pipeline TokenFinder during", "final", t.getContent());
		assertTrue("Pipeline TokenFinder during token?", TokenHelper.isToken(t));

		t = filter.nextTract();
		assertEquals("Pipeline TokenFinder after", " line", t.getContent());
		assertFalse("Pipeline TokenFinder after token?", TokenHelper.isToken(t));
	}
	
	public void testInitialisingPipeline() {
		InitialisingTractPipeline filter = new InitialisingTractPipeline(
			new TractFilter[] {
				new TokenFinderTractFilter(),
				new InitialisableTractFilter()
			},
			context);
		
		context.put("final", "the utter, utter, end");
		context.put("line 1", "more stuff");
		filter.connectSource(source);

		assertEquals("split tract lines, 1", "line 1\r\nline 2\r\nthird and ", JuicerConvertHelper.nextString(filter));
		assertEquals("split tract lines, 2", "the utter, utter, end", JuicerConvertHelper.nextString(filter));
		assertEquals("split tract lines, 3", " line", JuicerConvertHelper.nextString(filter));
		assertEquals("split tract lines, 4", null, JuicerConvertHelper.nextString(filter));
	}
	
	public void testExternalPipeline() {
		ExternalTractPipeline filter = new ExternalTractPipeline(
			"# escape rogue HTML\n" +
			"Replace !1!one!\n" +
			"Replace /line 2/blob/\n",
			context);
		
		context.put("final", "the utter, utter, end");
		context.put("line 1", "more stuff");
		filter.connectSource(source);

		assertEquals("external pipeline, 1", "line one\r\nblob\r\nthird and @final@ line", JuicerConvertHelper.nextString(filter));
		assertEquals("external pipeline, 2", null, JuicerConvertHelper.nextString(filter));
	}
	
	public void testExternalInclude() {
		ExternalTractPipeline filter = new ExternalTractPipeline(
			"# escape rogue HTML\n" +
			"Replace !1!one!\n" +
			"Include testfiles/extra.filter",
			context);
		
		context.put("final", "the utter, utter, end");
		context.put("line 1", "more stuff");
		filter.connectSource(source);

		assertEquals("external include, 1", "line one\r\nblob\r\nthird and @final@ line", JuicerConvertHelper.nextString(filter));
		assertEquals("external include, 2", null, JuicerConvertHelper.nextString(filter));
	}
	
	public void testNonSplittingReplace1() {
		filter = new ExternalTractPipeline(
			"FilterReplace tests.ToWord !(^|\\s|\\n|\\r)([1-9])($|\\s|\\n|\\r)!&1&~2&3!\n",
			context);

		filter.connectSource(source);

		assertEquals("external include, 1", "line one\r\nline two\r\nthird and @final@ line", JuicerConvertHelper.nextString(filter));
	}
	
	public void testNonSplittingReplace2() {
		filter = new ExternalTractPipeline(
			"FilterReplace tests.ToWord ![1-9]!&~0!\n",
			context);

		filter.connectSource(source);

		assertEquals("external include, 1", "line one\r\nline two\r\nthird and @final@ line", JuicerConvertHelper.nextString(filter));
	}
}
