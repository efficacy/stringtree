package org.stringtree.juicer.string;

public class SetOfCharactersStringFilter extends CharacterStringFilter {
    
	protected String set;
	
	public SetOfCharactersStringFilter(String set) {
		this.set = set;
	}
	
	protected boolean accept(char c) {
		return set.indexOf(c) != -1;
	}
}
