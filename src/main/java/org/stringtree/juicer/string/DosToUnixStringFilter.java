package org.stringtree.juicer.string;

public class DosToUnixStringFilter extends CharacterStringFilter {
    
	protected boolean accept(char c) {
		return  c != '\r';
	}
}
