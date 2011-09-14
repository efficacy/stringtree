package org.stringtree.juicer.string;

public class JoinLinesStringFilter extends JoinStringFilter {
    private String separator = "\n";
    
	public JoinLinesStringFilter(StringSource source) {
		super(source);
	}

	public JoinLinesStringFilter() {
		// this method intentionally left blank
	}
	
	public void setSeparator(String separator) {
		this.separator = separator;
	}
    
	protected void collect(String s) {
		super.collect(s);
		super.collect(separator);
	}
}
