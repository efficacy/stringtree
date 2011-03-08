package org.stringtree.xml;

import java.io.IOException;

public class BadXMLException extends IOException {
	public BadXMLException(String s) {
		super(s);
	}
    
	public BadXMLException(String s, int line, int col) {
		this(s + " at line " + line + ", column " + col);
	}
}