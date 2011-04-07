package tests.juicer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.finder.StringFinder;
import org.stringtree.juicer.JuicerConvertHelper;
import org.stringtree.juicer.formatter.ExternalFormatter;
import org.stringtree.juicer.string.IgnoreCommentLineStringFilter;
import org.stringtree.juicer.string.JoinLinesStringFilter;
import org.stringtree.juicer.string.SplitLinesStringFilter;
import org.stringtree.juicer.string.StringFilter;
import org.stringtree.juicer.string.StringPipeline;
import org.stringtree.juicer.string.StringStringSource;
import org.stringtree.juicer.tract.JoinTractFilter;
import org.stringtree.juicer.tract.ReplaceTokenTractFilter;
import org.stringtree.juicer.tract.StringTractSource;
import org.stringtree.juicer.tract.TokenFinderTractFilter;
import org.stringtree.juicer.tract.TractFilter;
import org.stringtree.juicer.tract.TractFilterFetcher;
import org.stringtree.juicer.tract.TractPipeline;
import org.stringtree.tract.MapTract;
import org.stringtree.util.FileReadingUtils;
import org.stringtree.util.FileWritingUtils;
import org.stringtree.util.StringUtils;
import org.stringtree.wiki.WikiFormatter;
import org.stringtree.wiki.WikiFormatterContext;


public class UserLevelTest extends TestCase {
    
	public void testIgnoreComments() {
		StringPipeline tube = new StringPipeline(new StringFilter[] {
			new SplitLinesStringFilter(),
			new IgnoreCommentLineStringFilter(),
			new JoinLinesStringFilter()
		});
		tube.connectSource(new StringStringSource("hello\n#there\n \nworld\n"));

		assertEquals("strip comment lines", "hello\nworld\n", tube.nextString());
	}

	private TractPipeline helloWorldPipeline() {
		Map<String, String>map = new HashMap<String, String>();
		map.put("hello", "world");
		
		TractPipeline tube = new TractPipeline(new TractFilter[] {
			new TokenFinderTractFilter(),
			new ReplaceTokenTractFilter(map),
			new JoinTractFilter()
		});
		return tube;
	}
	
	public void testReplaceTokens() {
		TractPipeline tube = helloWorldPipeline();

		tube.connectSource(new StringTractSource(" @hello@\n#there\n \nworld\n"));

		assertEquals("replace tokens", " world\n#there\n \nworld\n", JuicerConvertHelper.nextString(tube));
	}
	
	public void testPipelineStringFactory() {
		TractPipeline tube = helloWorldPipeline();

        TractFilterFetcher fac = new TractFilterFetcher(tube);
		String result = fac.get(" @hello@\n#there\n \nworld\n");

		assertEquals("pipeline", " world\n#there\n \nworld\n", result);
	}
	
	public void testWikiTransform() throws IOException {
		MapFetcher pages = new MapFetcher();
		pages.put("FrontPage", new MapTract("xx"));
		pages.put("MonospaceIndentationProblem", new MapTract("yy"));
		
		MapFetcher remotes = new MapFetcher();
		remotes.put("Wiki", "http://c2.com/cgi/wiki?");
		remotes.put("MeatBall", "http://www.usemod.com/cgi-bin/mb.pl?");
		
		Fetcher context = new WikiFormatterContext(pages, remotes); 

		compareWiki(new WikiFormatter(context), "internal");
        String tx = FileReadingUtils.readFile("testfiles/wiki.transform");
		compareWiki(new ExternalFormatter(tx, context), "file");
	}
	
	private void compareWiki(StringFinder wiki, String title) throws IOException {
		String input = FileReadingUtils.readFile("testfiles/wiki-before.txt");
		assertFalse("wiki example page input is blank", StringUtils.isBlank(input));
		String expected = FileReadingUtils.readFile("testfiles/wiki-after.html");
		assertFalse("wiki example page expected output is blank", StringUtils.isBlank(expected));
		String actual = wiki.get(input);
		assertFalse("wiki example page actual output is blank", StringUtils.isBlank(actual));

FileWritingUtils.writeFile("testfiles/" + title + "-actual.html", actual);
		assertEquals("wiki page example", normaliseLines(expected), normaliseLines(actual));
	}

	private String normaliseLines(String s) {
		return null==s ? null : s.replace("\r", "");
	}
}
