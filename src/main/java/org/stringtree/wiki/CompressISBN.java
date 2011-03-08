package org.stringtree.wiki;

import org.stringtree.juicer.string.SetOfCharactersStringFilter;
import org.stringtree.juicer.string.StringFilterFetcher;

public class CompressISBN extends StringFilterFetcher {
    
	public CompressISBN() {
		super(new SetOfCharactersStringFilter("0123456789X"));
	}
}
