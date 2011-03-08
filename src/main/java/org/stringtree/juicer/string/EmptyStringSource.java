package org.stringtree.juicer.string;


public class EmptyStringSource implements StringSource {
    
	public static final StringSource it = new EmptyStringSource();

	public String nextString() {
		return null;
	}
}
