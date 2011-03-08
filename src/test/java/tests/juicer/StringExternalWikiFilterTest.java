package tests.juicer;

import org.stringtree.Fetcher;
import org.stringtree.finder.StringFinder;
import org.stringtree.juicer.formatter.ExternalFormatter;
import org.stringtree.juicer.string.ArrayStringSource;

public class StringExternalWikiFilterTest extends WikiFilterTest {
    
	private String[] spec = {
		// harmonise line-endings
		"Replace !(\n\r)|(\r\n)|(\r)!\n!",

		// escape rogue HTML
		"Replace !<!&lt;!",
		"Replace !<!&gt;!",

		// "six-ticks" - override pattern recognition
		"Replace !''''''!<b></b>!",

		// "hands-off" block
		"Lock !\\[\n((.|\n)*?)\n\\]!<pre>&1</pre>!",

		// tabs, emphasis and rulers
		"Replace !        !\t!",
		"Replace !\n\\s*\n!\n<p/>\n!",
		"Replace !'{5}(.+?)'{5}!<i><b>$1</b></i>!",
		"Replace !'{3}(.+?)'{3}!<b>$1</b>!",
		"Replace !'{2}(.+?)'{2}!<i>$1</i>!",
		"Replace !(^|\n)-{4,}!\n<hr/>!",

		// monospace
		"Replace !((^|\n) (.+))+!<pre>$0</pre>!",
		"Replace !<pre> !<pre>!",
		"Replace !\n !\n!",

		// ordered/unordered list
		"FilterReplace org.stringtree.wiki.ListRow !((^|\n)\t+(\\*|[1234567890]+)\\.?\\s*(.*))+!&~0!",

		// def list
		"Replace !(((?:^|\n)\t+)(?:\\s*)(.*)(?:\\s*):(?:\\s*)(.*))+!<dl>\n$0\n</dl>!",
		"Replace !((?:^|\n)\t+)(?:\\s*)(.*)(?:\\s*):(?:\\s*)(.*)!$1<dt>$2</dt><dd>$3</dd>!",

		// embedded URLs
		"Lock !([Hh][Tt][Tt][Pp]):([^\\s\\<\\>\\[\\]\"'\\(\\)\\?])*[^\\s\\<\\>\\[\\]\"'\\(\\)\\?\\,\\.](\\.[Gg][Ii][Ff]|\\.[Jj][Pp][EeGg]{1,2}|\\.[Pp][Nn][Gg])([\\?][^\\s\\<\\>\\[\\]\"'\\(\\)\\?]*[^\\s\\<\\>\\[\\]\"'\\(\\)\\?\\,\\.])?!<img src='&0'/>!",
		"Lock !([Hh][Tt][Tt][Pp]|[Ff][Tt][Pp]|[Mm][Aa][Ii][Ll][Tt][Oo]):([^\\s\\<\\>\\[\\]\"'\\(\\)\\?])*[^\\s\\<\\>\\[\\]\"'\\(\\)\\?\\,\\.]([\\?][^\\s\\<\\>\\[\\]\"'\\(\\)\\?]*[^\\s\\<\\>\\[\\]\"'\\(\\)\\?\\,\\.])?!<a href='&0'>&0</a>!",

		// embedded ISBN
		"FilterLock org.stringtree.wiki.CompressISBN !(?:\\[\\s?)?(?:ISBN:?\\s?)((?:\\d[ -]?){9}(?:[\\dXx]))(?:\\s?\\])?!<a href='http://www.amazon.com/exec/obidos/ASIN/&~1/efficacysolut-20'>ISBN &1</a>!",

		// remote wiki link
		"FilterLock org.stringtree.wiki.RemoteWikiLink !([A-Za-z]+)(:[A-Za-z]+)!&~1&0!",
		"ForceReplace !^remote\\{(.*)\\}([A-Za-z]+):([A-Za-z]+)!<a href='$1$3'>$2:$3</a>!",
		"ForceReplace !^missing\\{(.*)\\}([A-Za-z]+):([A-Za-z]+)!$2:$3!",

		// internal page link
		"FilterLock org.stringtree.wiki.LocalWikiLink !\\b(([A-Z][a-z]+){2,})\\b!&~1!",
		"ForceReplace !^view\\{(.*)\\}!<a href='view?$1'>$1</a>!",
		"ForceReplace !^edit\\{(.*)\\}!$1<a href='edit?$1'>?</a>!",

		// merge locked and unlocked regions so they can be placed in table cells
		"Join",

		// table
		"FilterLock org.stringtree.wiki.TableRow !(((^|\n)\\|((.*|(<pre>(.|\n)*</pre>))\\|[ \t]*)))+!<table BORDER CELLSPACING='0' CELLPADDING='3'>&~0\n</table>!"
	};

	protected StringFinder createFormatter(Fetcher context) {
		type = "StringExternalWikiFilter";
		return new ExternalFormatter(new ArrayStringSource(spec), context);
	}
}

