package org.stringtree.xml;

public class UnbalancedTagException extends BadXMLException {
	public UnbalancedTagException(String tag, int line, int col) {
		super("Unbalanced tag '" + tag + "'", line, col);
	}
}