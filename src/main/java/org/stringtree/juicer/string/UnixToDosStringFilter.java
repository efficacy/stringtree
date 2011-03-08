package org.stringtree.juicer.string;

public class UnixToDosStringFilter extends CharacterStringFilter {
    
	protected boolean accept(char c) {
		return  c != '\r';
	}

	protected void put(char c, StringBuffer buf) {
		if (c == '\n') {
			buf.append("\r\n");
		} else {
			super.put(c, buf);
		}
	}
}
