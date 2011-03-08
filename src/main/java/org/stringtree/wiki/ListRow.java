package org.stringtree.wiki;

import java.util.Stack;

import org.stringtree.Fetcher;
import org.stringtree.juicer.string.SplitLinesStringFilter;
import org.stringtree.juicer.string.StringFilter;
import org.stringtree.juicer.string.StringStringSource;
import org.stringtree.juicer.regex.MatcherStringFetcher;
import org.stringtree.util.StringUtils;

class LineTransformContext extends Stack<String> {
    
	public String getType() {
		return (isEmpty()) 
			? null
			: StringUtils.stringValue(peek());
	}

	public void endList(StringBuffer buf) {
		if (!isEmpty()) {
			String type = StringUtils.stringValue(pop());
			buf.append("\n</");
			buf.append(type);
			buf.append(">");
		}
	}

	public void startList(String type, StringBuffer buf) {
		push(type);
		buf.append("\n<");
		buf.append(type);
		buf.append(">");
	}

	public void endAll(StringBuffer buf) {
		while (!isEmpty()) {
			endList(buf);
		}
	}

	public void ensureType(StringBuffer buf, String type, int indent) {
		while (!isEmpty() && size() > indent) {
			endList(buf);
		}
		while (size() < indent) {
			startList(type, buf);
		}
		if (!type.equals(getType())) {
			endList(buf);
			startList(type, buf);
		}
	}
}

public class ListRow implements Fetcher {
    
	public Object getObject(String content) {
		LineTransformContext context = new LineTransformContext();
		StringBuffer buf = new StringBuffer();

		StringFilter rows = new SplitLinesStringFilter();
		rows.connectSource(new StringStringSource(content));

		for (String row = rows.nextString(); row != null; row = rows.nextString()) {
			MatcherStringFetcher splitter =
				new MatcherStringFetcher("(^|\n)(\t+)(\\*|[1234567890]+)\\.?\\s*(.*)",
				row);

			int indent = splitter.get(2).length();
			String type = ("*".equals(splitter.get(3))) ? "ul" : "ol";
			String text = splitter.get(4);

			context.ensureType(buf, type, indent);
			buf.append("\n<li>");
			buf.append(text);
			buf.append("</li>");
		}
		context.endAll(buf);

		String ret = buf.toString();
		return ret;
	}
}
