package tests.juicer;

import java.util.ArrayList;
import java.util.List;

import org.stringtree.Repository;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.juicer.string.AddPrefixSuffixStringFilter;
import org.stringtree.juicer.string.DosToUnixStringFilter;
import org.stringtree.juicer.string.FileStringSource;
import org.stringtree.juicer.string.InitialisingStringPipeline;
import org.stringtree.juicer.string.PassStringFilter;
import org.stringtree.juicer.string.RegexSplitStringFilter;
import org.stringtree.juicer.string.SetOfCharactersStringFilter;
import org.stringtree.juicer.string.SplitLinesStringFilter;
import org.stringtree.juicer.string.StringFilter;
import org.stringtree.juicer.string.StringPipeline;
import org.stringtree.juicer.string.StringStringSource;
import org.stringtree.juicer.string.UnixToDosStringFilter;

public class StringFilterTest extends JuicerStringTestCase {
    
	StringFilter filter;

	public void setUp() {
		source = new FileStringSource("testfiles/sf1.txt");
	}

	protected void check(String name, String expected, String result, boolean log) {
		if (log) {
			System.out.println(name + ":\nexpected '" + expected + "'\n     got '" + result + "'");
		}
		assertEquals(name, expected, result);
	}

	protected void check(String name, String expected, String source) {
		check(name, expected, source, false);
	}

	public void testPass() {
		filter = new PassStringFilter();
		filter.connectSource(source);

		check("Pass, whole file", "line 1\nline 2\nthird and final line", filter.nextString());
	}

	public void testStripAndAddCR() {
		filter = new DosToUnixStringFilter();
		filter.connectSource(source);

		String stripped = filter.nextString();
		check("StripCR, whole file", "line 1\nline 2\nthird and final line", stripped);

		source = new StringStringSource(stripped);
		filter = new UnixToDosStringFilter();
		filter.connectSource(source);

		check("Add CR, whole file", "line 1\r\nline 2\r\nthird and final line", filter.nextString());
	}

	public void testSplitLines() {
		filter = new SplitLinesStringFilter();
		filter.connectSource(source);

		check("split lines, 1", "line 1", filter.nextString());
		check("split lines, 2", "line 2", filter.nextString());
		check("split lines, 3", "third and final line", filter.nextString());
		check("split lines, 4", null, filter.nextString());

		filter.connectSource(new StringStringSource("line 1\nline 2\n"));
		check("split lines, 5", "line 1", filter.nextString());
		check("split lines, 6", "line 2", filter.nextString());
		check("split lines, 7", null, filter.nextString());
	}

	public void testRegexSplitString() {
		filter = new RegexSplitStringFilter("(^|\n)\\|");
		filter.connectSource(new StringStringSource("|cell 1|cell 2|\n|cell 3|cell 4|"));

		check("regex split, 1", "cell 1|cell 2|", filter.nextString());
		check("regex split, 2", "cell 3|cell 4|", filter.nextString());
		check("regex split, 3", null, filter.nextString());

		filter.connectSource(new StringStringSource("|\n<pre>cell 1\nmore\n</pre>\n|cell 3|cell 4|"));

		check("regex split, 3", "\n<pre>cell 1\nmore\n</pre>", filter.nextString());
		check("regex split, 4", "cell 3|cell 4|", filter.nextString());
		check("regex split, 5", null, filter.nextString());
	}

	public void testSplitLinesAcrossMultiple() {
		filter = new SplitLinesStringFilter();
		filter.connectSource(source);

		check("split lines, 1", "line 1", filter.nextString());
		rewind();

		check("split lines multi, 2", "line 2", filter.nextString());
		check("split lines multi, 3", "third and final line", filter.nextString());
		check("split lines multi, 4", "line 1", filter.nextString());
		check("split lines multi, 5", "line 2", filter.nextString());
		check("split lines multi, 6", "third and final line", filter.nextString());
		check("split lines multi, 7", null, filter.nextString());
	}

	public void testPrefixSuffix() {
		filter = new AddPrefixSuffixStringFilter("[", "]");
		filter.connectSource(source);

		check("PrefixSuffix, whole file", "[line 1\nline 2\nthird and final line]", filter.nextString());

		rewind();
		StringFilter split = new SplitLinesStringFilter();
		split.connectSource(source);
		filter = new AddPrefixSuffixStringFilter("[", "]");
		filter.connectSource(split);

		check("PrefixSuffix, split lines, 1", "[line 1]", filter.nextString());
		check("PrefixSuffix, split lines, 2", "[line 2]", filter.nextString());
		check("PrefixSuffix, split lines, 3", "[third and final line]", filter.nextString());
		check("PrefixSuffix, split lines, 4", null, filter.nextString());
	}

	public void testSetOfCharacters() {
		filter = new SetOfCharactersStringFilter("0123456789X");
		filter.connectSource(new StringStringSource("45-564-192-X"));

		check("SetOfCharacters, strip non-numerics", "45564192X", filter.nextString());
	}
	
	public void testPipeline() {
		StringPipeline filter = new StringPipeline();
		List<StringFilter> list = new ArrayList<StringFilter>();
		list.add(new PassStringFilter());
		filter.setList(list);
		filter.connectSource(source);

		check("Pass, whole file", "line 1\nline 2\nthird and final line", filter.nextString());

		rewind();
		list.add(new SplitLinesStringFilter());
		filter.relink();

		check("split lines, 1", "line 1", filter.nextString());
		check("split lines, 2", "line 2", filter.nextString());
		check("split lines, 3", "third and final line", filter.nextString());
		check("split lines, 4", null, filter.nextString());
	}
	
	public void testInitialisingPipeline() {
		Repository context = new MapFetcher();
		InitialisingStringPipeline filter = new InitialisingStringPipeline(
			new StringFilter[] {
				new SplitLinesStringFilter(),
				new InitialisableStringFilter()
			},
		context);
		
		context.put("line 1", "hoople");
		context.put("line2", "more stuff");
		filter.connectSource(source);

		check("split lines, 1", "hoople", filter.nextString());
		check("split lines, 2", "line 2", filter.nextString());
		check("split lines, 3", "third and final line", filter.nextString());
		check("split lines, 4", null, filter.nextString());
	}
}
