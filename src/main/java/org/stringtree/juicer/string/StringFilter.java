package org.stringtree.juicer.string;

public interface StringFilter extends StringSource, StringDestination {
	String filter(String string);
}
