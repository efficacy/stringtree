package org.stringtree.wiki;

import java.util.Map;

import org.stringtree.Container;
import org.stringtree.Fetcher;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.juicer.formatter.Formatter;
import org.stringtree.juicer.string.StringSource;
import org.stringtree.juicer.tract.ExternalTractPipeline;
import org.stringtree.juicer.tract.JoinTractFilter;
import org.stringtree.juicer.tract.RegexReplaceTractFilter;
import org.stringtree.juicer.tract.RegexSplitReplaceTractFilter;
import org.stringtree.juicer.tract.TractFilter;

public class WikiFormatter extends Formatter {
    
	public WikiFormatter(Map<String, Object> pages) {
		this(new MapFetcher(pages, false), null);
	}

	public WikiFormatter(Map<String, Object> pages, Map<String, Object> remoteNames) {
		this(new MapFetcher(pages, false), new MapFetcher(remoteNames, false));
	}

	public WikiFormatter(Container pages, Fetcher remoteNames) {
		this(new WikiFormatterContext(pages, remoteNames));
	}

	public WikiFormatter(Fetcher context) {
		super(context);
	}
	
	protected TractFilter[] getFilters(Fetcher context) {
		return new TractFilter[] {
			new RegexReplaceTractFilter("(\n\r)|(\r\n)|(\r)", "\n"),
			new RegexReplaceTractFilter("<", "&lt;"),
			new RegexReplaceTractFilter(">", "&gt;"),
				
			// intentional break
			new RegexReplaceTractFilter("''''''","<b></b>"),
	
			// unparsed block
			new RegexSplitReplaceTractFilter("\\[\n((.|\n)*?)\n\\]", "<pre>&1</pre>"),
				
			new RegexReplaceTractFilter("        ", "\t"),
			new RegexReplaceTractFilter("\n\\s*\n", "\n<p/>\n"),
			new RegexReplaceTractFilter("'{5}(.+?)'{5}", "<i><b>$1</b></i>"),
			new RegexReplaceTractFilter("'{3}(.+?)'{3}", "<b>$1</b>"),
			new RegexReplaceTractFilter("'{2}(.+?)'{2}", "<i>$1</i>"),
			new RegexReplaceTractFilter("(^|\n)-{4,}", "\n<hr/>"),
	
			// monospace
			new RegexReplaceTractFilter("((^|\n) (.+))+","<pre>$0</pre>"),
			new RegexReplaceTractFilter("<pre> ","<pre>"),
			new RegexReplaceTractFilter("\n ","\n"),
				
			// ordered/unordered list
			new RegexSplitReplaceTractFilter("((^|\n)\t+(\\*|[1234567890]+)\\.?\\s*(.*))+","&~0", false, new ListRow()),
	
			// def list
			new RegexReplaceTractFilter("(((?:^|\n)\t+)(?:\\s*)(.*)(?:\\s*):(?:\\s*)(.*))+","<dl>\n$0\n</dl>"),
			new RegexReplaceTractFilter("((?:^|\n)\t+)(?:\\s*)(.*)(?:\\s*):(?:\\s*)(.*)","$1<dt>$2</dt><dd>$3</dd>"),
	
			// embedded URLs
			new RegexSplitReplaceTractFilter("([Hh][Tt][Tt][Pp]):([^\\s\\<\\>\\[\\]\"'\\(\\)\\?])*[^\\s\\<\\>\\[\\]\"'\\(\\)\\?\\,\\.](\\.[Gg][Ii][Ff]|\\.[Jj][Pp][EeGg]{1,2}|\\.[Pp][Nn][Gg])([\\?][^\\s\\<\\>\\[\\]\"'\\(\\)\\?]*[^\\s\\<\\>\\[\\]\"'\\(\\)\\?\\,\\.])?","<img src='&0'/>"),
			new RegexSplitReplaceTractFilter("([Hh][Tt][Tt][Pp]|[Ff][Tt][Pp]|[Mm][Aa][Ii][Ll][Tt][Oo]):([^\\s\\<\\>\\[\\]\"'\\(\\)\\?])*[^\\s\\<\\>\\[\\]\"'\\(\\)\\?\\,\\.]([\\?][^\\s\\<\\>\\[\\]\"'\\(\\)\\?]*[^\\s\\<\\>\\[\\]\"'\\(\\)\\?\\,\\.])?", "<a href='&0'>&0</a>"),
				
			// embedded ISBN
			new RegexSplitReplaceTractFilter("(?:\\[ ?)?(?:ISBN:? ?)((?:\\d[ -]?){9}(?:[\\dXx]))(?: ?\\])?","<a href='http://www.amazon.com/exec/obidos/ASIN/&~1/efficacysolut-20'>ISBN &1</a>", new CompressISBN()),
	
			// remote wiki link
			new RegexSplitReplaceTractFilter("([A-Za-z]+)(:[A-Za-z]+)","&~1&0", new RemoteWikiLink()),
			new RegexReplaceTractFilter("^remote\\{(.*)\\}([A-Za-z]+):([A-Za-z]+)","<a href='$1$3'>$2:$3</a>", false),
			new RegexReplaceTractFilter("^missing\\{(.*)\\}([A-Za-z]+):([A-Za-z]+)","$2:$3", false),
	
			// internal page link
			new RegexSplitReplaceTractFilter("\\b(([A-Z][a-z]+){2,})\\b","&~1", new LocalWikiLink()),
			new RegexReplaceTractFilter("^view\\{(.*)\\}","<a href='view?$1'>$1</a>", false),
			new RegexReplaceTractFilter("^edit\\{(.*)\\}","$1<a href='edit?$1'>?</a>", false),
	
			// a "hook" for extra user-defined processing without mucking up this file
			new ExternalTractPipeline((StringSource)context.getObject("wiki.extension")),
			
			// merge locked and unlocked regions so they can be placed in table cells
			new JoinTractFilter(),
	
			// table
			new RegexSplitReplaceTractFilter("(((^|\n)\\|((.*|(<pre>([^<]|\n)*</pre>))\\|[ \t]*)))+","<table BORDER CELLSPACING='0' CELLPADDING='3'>&~0\n</table>", new TableRow()),			
	
			new JoinTractFilter()
		};
	}
}
