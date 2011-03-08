package tests.juicer;

import org.stringtree.Fetcher;
import org.stringtree.Repository;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.StorerHelper;
import org.stringtree.finder.StringFinder;
import org.stringtree.tract.MapTract;

import org.stringtree.wiki.WikiFormatter;

public class WikiFilterTest extends FilterTestCase {
    
	protected Repository pages;
	public String type = "WikiFilterTest";

	protected StringFinder createFormatter() {
		pages = new MapFetcher();

		Repository remote = new MapFetcher();
		remote.put("ALife", "http://news.alife.org/wiki/index.php?");
		remote.put("Wiki", "http://c2.com/cgi/wiki?");

		StorerHelper.put(context, "wiki.pages", pages);
		StorerHelper.put(context, "wiki.remoteNames", remote);
		return createFormatter(context);
	}
	
	protected StringFinder createFormatter(Fetcher context) {
		return new WikiFormatter(context);
	}

	public void testWikiText() {
		check("wiki empty", null, "");
		check("wiki plain text", "hello, world", "hello, world");
		check("wiki plain text 2", "hello, world ", "hello, world ");
		check("wiki complex text", "then run c:\\bin\\rsync.exe", "then run c:\\bin\\rsync.exe");
	}

	public void testWikiSixTicks() {
		check("wiki sixticks 1", "<b></b>", "''''''");
		check("wiki sixticks 2", "aa<b></b>bb", "aa''''''bb");
	}

	public void testWikiNewline() {
		check("wiki newline 1", "\n", "\n");
		check("wiki newline 2", "\n", "\r\n");
		check("wiki newline 3", "\n", "\n\r");
		check("wiki newline 4", "\n", "\r");
		check("wiki newline 1", "\n<p/>\n", "\n\n");
		check("wiki newline 1", "\n<p/>\n", "\n  \n");
		check("wiki newline 1", "\n<p/>\n", "\r\n  \r\n");
	}

	public void testWikiTabs() {
		check("wiki one tab", "\t", "\t");
		check("wiki 8 spaces", "\t", "        ");
		check("wiki 8 spaces and tab", "\t\t", "        \t");
		check("wiki tab and 8 spaces", "\t\t", "\t        ");
		check("wiki 16 spaces", "\t\t", "                ");
		check("wiki two tabs", "\t\t", "\t\t");
	}

	public void testWikiBoldItalic() {
		check("wiki italic", "<i>hello</i>", "''hello''");
		check("wiki bold", "<b>hello</b>", "'''hello'''");
		check("wiki bold italic", "<i><b>hello</b></i>", "'''''hello'''''");
		check("wiki bold and italic 1", "<b>hello <i>Frank</i> mate</b>", "'''hello ''Frank'' mate'''");
		check("wiki bold and italic 2", "<i>hello <b>Frank</b> mate</i>", "''hello '''Frank''' mate''");
		check("wiki bold and italic 3", "<b>hello <i>Frank</i> </b>", "'''hello ''Frank'' '''");
		check("wiki bold and italic 4", "<i>hello <b>Frank</b> </i>", "''hello '''Frank''' ''");
		check("wiki bold 2", "<b>tab</b> <b>*</b> (or 8 spaces and <b>*</b>), for first level", 
			"'''tab''' '''*''' (or 8 spaces and '''*'''), for first level");
	}

	public void testWikiRuler() {
		check("wiki ruler 1", "\n<hr/>", "----");
		check("wiki ruler 2", "---", "---");
		check("wiki ruler 3", "hello ----", "hello ----");
		check("wiki ruler 4", "\n<hr/>", "-----");
		check("wiki ruler 5", "\n<hr/>", "------");
	}

	public void testWikiImage() {
		check("wiki inline image 1",
			"<img src='http://www.efsol.com/images/efsol-logo.gif'/>",
			"http://www.efsol.com/images/efsol-logo.gif");

		check("wiki inline image 2",
			"<img src='http://www.efsol.com/images/efsol-logo.jpg'/>",
			"http://www.efsol.com/images/efsol-logo.jpg");

		check("wiki inline image 3",
			"<img src='http://www.efsol.com/images/efsol-logo.jpeg'/>",
			"http://www.efsol.com/images/efsol-logo.jpeg");

		check("wiki inline image 4",
			"<img src='http://www.efsol.com/images/efsol-logo.jpe'/>",
			"http://www.efsol.com/images/efsol-logo.jpe");

		check("wiki inline image 4",
			"<img src='http://www.efsol.com/images/efsol-logo.png'/>",
			"http://www.efsol.com/images/efsol-logo.png");

		check("wiki inline image 5",
			"<img src='http://www.efsol.com/images/efsol-logo.gif?ugh=x'/>",
			"http://www.efsol.com/images/efsol-logo.gif?ugh=x");

		check("wiki inline image 6",
			"<img src='http://www.efsol.com/images/efsol-logo.GIF'/>",
			"http://www.efsol.com/images/efsol-logo.GIF");

		check("wiki inline image 7",
			"<img src='http://www.efsol.com/images/efsol-logo.JPG'/>",
			"http://www.efsol.com/images/efsol-logo.JPG");

		check("wiki inline image 8",
			"<img src='http://www.efsol.com/images/efsol-logo.JPEG'/>",
			"http://www.efsol.com/images/efsol-logo.JPEG");

		check("wiki inline image 9",
			"<img src='http://www.efsol.com/images/efsol-logo.JPE'/>",
			"http://www.efsol.com/images/efsol-logo.JPE");

		check("wiki inline image 10",
			"<img src='http://www.efsol.com/images/efsol-logo.PNG'/>",
			"http://www.efsol.com/images/efsol-logo.PNG");
	}

	public void testWikiExternalLink() {
		check("wiki ext link 1",
			"<a href='http://www.efsol.com/'>http://www.efsol.com/</a>",
			"http://www.efsol.com/");

		check("wiki ext link 2",
			"<a href='ftp://www.efsol.com/src/ugh.java'>ftp://www.efsol.com/src/ugh.java</a>",
			"ftp://www.efsol.com/src/ugh.java");

		check("wiki ext link 3",
			"<a href='ftp://test@www.efsol.com/src/ugh.java'>ftp://test@www.efsol.com/src/ugh.java</a>",
			"ftp://test@www.efsol.com/src/ugh.java");

		check("wiki ext link 4",
			"<a href='http://www.efsol.com/friki?JustLink'>http://www.efsol.com/friki?JustLink</a>",
			"http://www.efsol.com/friki?JustLink");

		check("wiki ext link 5",
			"<a href='mailto:test@efsol.com'>mailto:test@efsol.com</a>",
			"mailto:test@efsol.com");

		check("wiki ext link 6",
			"<a href='mailto:test@efsol.gif'>mailto:test@efsol.gif</a>",
			"mailto:test@efsol.gif");

		check("wiki ext link 7",
			"<a href='http://www.efsol.com/friki?JustLink&x=2'>http://www.efsol.com/friki?JustLink&x=2</a>.",
			"http://www.efsol.com/friki?JustLink&x=2.");

		check("wiki ext link 8",
			"<b><a href='http://www.efsol.com/friki?JustLink&x=2'>http://www.efsol.com/friki?JustLink&x=2</a></b>",
			"'''http://www.efsol.com/friki?JustLink&x=2'''");

		check("wiki ext link 9",
			"<b><a href='HTTP://www.efsol.com/friki?JustLink&x=2'>HTTP://www.efsol.com/friki?JustLink&x=2</a></b>",
			"'''HTTP://www.efsol.com/friki?JustLink&x=2'''");

		check("wiki ext link 10",
			"<a href='http://www.efsol.com/FrankCarver.html'>http://www.efsol.com/FrankCarver.html</a>",
			"http://www.efsol.com/FrankCarver.html");
	}

	public void testWikiUnorderedList() {
		check("wiki unordered 1", "\n<ul>\n<li>first</li>\n<li>second</li>\n</ul>",
			"\t* first\n\t*second");

		check("wiki unordered 2", "\n<ul>\n<li>first</li>\n<li>second</li>\n</ul>\n",
			"\t* first\n\t*second\n");

		check("wiki unordered 3", "\n<ul>\n<li>first</li>\n<li>second</li>\n</ul>\nmore stuff",
			"\t* first\n\t*second\nmore stuff");

		check("wiki unordered 4", "\n<ul>\n<li>first</li>\n<li>second</li>\n</ul>\nmore stuff",
			"\t* first\n\t*second\nmore stuff");

		check("wiki unordered 5", "\n<ul>\n<li>first</li>\n</ul>",
			"\t* first");

		check("wiki unordered 6", "\n<ul>\n<li>first</li>\n</ul>",
			"\t*. first");
	}

	public void testWikiOrderedList() {
		check("wiki ordered 1", "\n<ol>\n<li>first</li>\n<li>second</li>\n</ol>\n",
			"\t1 first\n\t1second\n");

		check("wiki ordered 2", "\n<ol>\n<li>first</li>\n<li>second</li>\n</ol>\n",
			"        1 first\n        1second\n");

		check("wiki ordered 3", "\n<ol>\n<li>first</li>\n<li>second</li>\n</ol>\n",
			"        23. first\n        1second\n");

		check("wiki ordered 4", "\n<ol>\n<li>first</li>\n<li>second</li>\n</ol>\nmore stuff",
			"\t1 first\n\t1second\nmore stuff");

		check("wiki ordered 5", "\n<ol>\n<li>first</li>\n<li>second</li>\n</ol>\nmore stuff",
			"\t1 first\r\n\t1second\r\nmore stuff");
	}

	public void testWikiMixedList() {
		check("wiki mixed list 1", "\n<ul>\n<li>first</li>\n</ul>\n<ol>\n<li>second</li>\n</ol>\n",
			"\t* first\n\t1second\n");

		check("wiki mixed list 2", "\n<ol>\n<li>first</li>\n</ol>\n<ul>\n<li>second</li>\n</ul>\n",
			"\t1 first\n\t*second\n");

		check("wiki mixed list 3", "\n<ul>\n<li>first unordered element</li>\n<li>second unordered element</li>\n</ul>\n<ol>\n<li>third ordered element</li>\n</ol>",
			"\t* first unordered element\n\t* second unordered element\n\t1 third ordered element");

		check("wiki mixed list 4", "\n<ol>\n<li><img src='http://www.efsol.com/images/efsol-logo.gif'/></li>\n</ol>\n<ul>\n<li>second</li>\n</ul>\n",
			"\t1 http://www.efsol.com/images/efsol-logo.gif\n\t*second\n");
	}

	public void testWikiMonospace() {
		check("wiki monospace 1", "<pre>uh<i>hello</i> there</pre>",
			" uh''hello'' there");

		check("wiki monospace 2", "<pre>uh\n  there\n  too</pre>",
			" uh\n   there\n   too");

		check("wiki monospace 3", "<pre>uh\n  there\n  too</pre>",
			" uh\r\n   there\r\n   too");
	}

	public void testWikiISBN() {
		check("wiki isbn 1",
			"<a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047120708X</a>",
			"ISBN 047120708X");

		check("wiki isbn 2",
			"<a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047120708X</a>",
			"[ISBN 047120708X]");

		check("wiki isbn 3",
			"<a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047120708X</a>",
			"[ ISBN 047120708X]");

		check("wiki isbn 4",
			"<a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047120708X</a>",
			"[ ISBN 047120708X ]");

		check("wiki isbn 5",
			"<a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047120708X</a>",
			"ISBN: 047120708X");

		check("wiki isbn 6",
			"<a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047120708X</a>",
			"[ISBN: 047120708X]");

		check("wiki isbn 7",
			"<a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047120708X</a>",
			"[ ISBN: 047120708X]");

		check("wiki isbn 8",
			"<a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047120708X</a>",
			"[ ISBN: 047120708X ]");

		check("wiki isbn 9",
			"<a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047-120-708-X</a>",
			"ISBN 047-120-708-X");

		check("wiki isbn 10",
			"prefix <a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047-120-708-X</a> hello",
			"prefix ISBN 047-120-708-X hello");

		check("wiki isbn 11",
			"prefix <a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047-120-708-X</a> hello",
			"prefix [ISBN 047-120-708-X] hello");

		check("wiki isbn 12",
			"prefix <a href='http://www.amazon.com/exec/obidos/ASIN/047120708X/efficacysolut-20'>ISBN 047-120-708-X</a> hello",
			"prefix [ ISBN 047-120-708-X ] hello");
	}

	public void testWikiBlock() {
		check("wiki block 1", "<pre>hello</pre>",
			"[\nhello\n]");

		check("wiki block 2", "<pre>first\nsecond</pre>",
			"[\nfirst\nsecond\n]");

		check("wiki block 3", "<pre>first\nsecond  \nthird</pre>",
			"[\nfirst\nsecond  \nthird\n]");

		check("wiki block 4", "aa <pre>first\nsecond  \nthird</pre>bb",
			"aa [\nfirst\nsecond  \nthird\n]bb");

		check("wiki block 5", "<i>fir\\st</i>",
			"''fir\\st''");

		check("wiki block 6", "<pre>first\n|second  \nthe ThirdWord cell</pre>",
			"[\nfirst\n|second  \nthe ThirdWord cell\n]");

		check("wiki block 7", "<pre>fir\\st</pre>",	"[\nfir\\st\n]");

		check("wiki block 8", "<pre>abc</pre>ugh<pre>def</pre>",	"[\nabc\n]ugh[\ndef\n]");
	}

	public void testInterwikiLinks() {
		check("wiki interwiki 1",
			"NotAnotherWiki:FrankCarver",
			"NotAnotherWiki:FrankCarver");

		check("wiki interwiki 2",
			"<a href='http://news.alife.org/wiki/index.php?HoopyFrood'>ALife:HoopyFrood</a>",
			"ALife:HoopyFrood");

		check("wiki interwiki 3",
			"<a href='http://c2.com/cgi/wiki?FrankCarver'>Wiki:FrankCarver</a>",
			"Wiki:FrankCarver");
	}

	public void testJustLink() {
		check("wiki link 1", "Just<b></b>Link",
			"Just''''''Link");

		check("wiki link 2", "JustLink<a href='edit?JustLink'>?</a>",
			"JustLink");

		check("wiki link 3", "a JustLink<a href='edit?JustLink'>?</a>",
			"a JustLink");

		check("wiki link 4", "a JustLink<a href='edit?JustLink'>?</a> ",
			"a JustLink ");

		check("wiki link 5",
		"JustLink<a href='edit?JustLink'>?</a>\nAnotherLink<a href='edit?AnotherLink'>?</a>\nThirdLink<a href='edit?ThirdLink'>?</a>",
			"JustLink\nAnotherLink\nThirdLink");

		pages.put("JustLink", new MapTract("xx"));
		check("wiki link 6", "<a href='view?JustLink'>JustLink</a>",
			"JustLink");

		check("wiki link 7", "Just",
			"Just");

		check("wiki link 8", "JustL",
			"JustL");

		check("wiki link 9", "JustLI",
			"JustLI");

		check("wiki link 10", "JustLinkI",
			"JustLinkI");

		check("wiki link 11", "aJustLink",
			"aJustLink");

		check("wiki link 12", "(<a href='view?JustLink'>JustLink</a>)",
			"(JustLink)");
	}

	public void testDefList() {
		check("wiki deflist 1", "<dl>\n\t<dt>first</dt><dd>second</dd>\n</dl>\n",
			"\tfirst:\tsecond\n");

		check("wiki deflist 2",
			"<dl>\n"+
			"\t<dt>first term</dt><dd>This is first definition.</dd>\n" +
			"\t<dt>second term</dt><dd>This is the second definition.</dd>\n" +
			"</dl>\n",
			"\tfirst term:\tThis is first definition.\n\tsecond term:\tThis is the second definition.\n");

		check("wiki deflist 3",
			"<dl>\n"+
			"\t<dt>first term</dt><dd>This is first definition.</dd>\n" +
			"\t<dt>second term</dt><dd>This is the second definition.</dd>\n" +
			"</dl>\n",
			"\tfirst term:\tThis is first definition.\n\tsecond term:\tThis is the second definition.\n");
	}

	public void testTable() {
		check("wiki table 1", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td>hello</td></tr>\n</table>",
			"|hello|");

		check("wiki table 2", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td>hello</td></tr>\n<tr><td>there</td></tr>\n</table>",
			"|hello|\n|there|");

		check("wiki table 3", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td>hello</td><td>there</td></tr>\n</table>",
			"|hello|there|");

		check("wiki table 4", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td>hello</td><td>world</td></tr>\n<tr><td>there</td><td>their</td></tr>\n</table>",
			"|hello|world|\n|there|their|");

		check("wiki table 5", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td colspan='4'>hello</td></tr>\n</table>",
			"|hello||  |\t |");

		check("wiki table 6", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td colspan='2'>hello</td></tr>\n<tr><td>there</td><td>their</td></tr>\n</table>",
			"|hello| |\n|there|their|");

		check("wiki table 7", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td>hello</td><td>world</td></tr>\n<tr><td>there</td><td>their</td></tr>\n</table>"+
			"\n<p/><table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td>hello</td><td>world</td></tr>\n<tr><td>there</td><td>their</td></tr>\n</table>",
			"|hello|world|\n|there|their|\n\n|hello|world|\n|there|their|");

		check("wiki table 8", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td>hello</td></tr>\n<tr><td>there</td></tr>\n</table>",
			"|hello|  \t \n|there|");

		check("wiki table 9", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td colspan='2'>hello</td></tr>\n</table>",
			"||hello|");

		check("wiki table 10", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td colspan='2'>hello</td></tr>\n</table>",
			"|hello||");

		check("wiki table 11", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td colspan='3'>hello</td></tr>\n</table>",
			"||hello||");

		check("wiki table 12", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td><pre>hello\nthere</pre></td></tr>\n</table>",
			"|[\nhello\nthere\n]|");

		check("wiki table 12", "<table BORDER CELLSPACING='0' CELLPADDING='3'>\n<tr><td><pre>hello\n\nthere</pre></td></tr>\n</table>",
			"|[\nhello\n\nthere\n]|");
	}

	public void testIndents() {
		check("wiki indent 1", "\n<ol>\n<ol>\n<li>first</li>\n</ol>\n</ol>\n",
			"\t\t1 first\n");

		check("wiki indent 2", "\n<ul>\n<li>hello</li>\n<ol>\n<li>first</li>\n</ol>\n</ul>\n",
			"\t*hello\n\t\t1 first\n");

		check("wiki indent 3", "\n<ul>\n<li>hello</li>\n<ul>\n<li>first</li>\n</ul>\n</ul>\n",
			"\t*hello\n\t\t*first\n");

		check("wiki indent 3", "\n<ul>\n<li>hello</li>\n<ul>\n<li>first</li>\n</ul>\n</ul>\n",
			"        *hello\n                *first\n");
	}
}


