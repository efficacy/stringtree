package org.stringtree.xml;

public class UnexpectedCharacterException extends BadXMLException {
	public UnexpectedCharacterException(char c, int line, int col) {
		super("Unexpected character '" + c + "'", line, col);
	}
}