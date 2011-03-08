package org.stringtree.juicer.string;

import org.stringtree.util.StringUtils;

public class IgnoreCommentLineStringFilter extends RejectStringFilter {
    
	protected String prefix;
	protected boolean ignoreBlankLines;
	
	public IgnoreCommentLineStringFilter(String prefix, boolean ignoreBlankLines) {
		init(prefix, ignoreBlankLines);
	}
	
	public IgnoreCommentLineStringFilter() {
		this("#", true);
	}

	public IgnoreCommentLineStringFilter(String prefix, boolean ignoreBlankLines, StringSource source) {
		super(source);
		init(prefix, ignoreBlankLines);
	}

	public void init(String prefix, boolean ignoreBlankLines) {
		this.prefix = prefix;
		this.ignoreBlankLines = ignoreBlankLines;
	}
	
	public IgnoreCommentLineStringFilter(StringSource source) {
		this("#", true, source);
	}

	protected boolean reject(String s) {
		return StringUtils.isBlank(s) || s.startsWith(prefix);
	}
}
