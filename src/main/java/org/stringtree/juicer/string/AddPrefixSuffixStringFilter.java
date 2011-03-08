package org.stringtree.juicer.string;

public class AddPrefixSuffixStringFilter extends PassStringFilter {
    
	private String prefix = "";
	private String suffix = "";

	public AddPrefixSuffixStringFilter(String prefix, String suffix) {
		setPrefixSuffix(prefix, suffix);
	}

	public AddPrefixSuffixStringFilter() {
		// this method intentionally left blank
	}

	public AddPrefixSuffixStringFilter(String prefix, String suffix, StringSource source) {
		super(source);
		setPrefixSuffix(prefix, suffix);
	}

	public AddPrefixSuffixStringFilter(StringSource source) {
		super(source);
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setPrefixSuffix(String prefix, String suffix) {
		setPrefix(prefix);
		setSuffix(suffix);
	}

	public String filter(String input) {
		return prefix + input + suffix;
	}
}
