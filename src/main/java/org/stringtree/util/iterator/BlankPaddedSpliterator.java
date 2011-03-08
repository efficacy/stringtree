package org.stringtree.util.iterator;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class BlankPaddedSpliterator extends Spliterator {
    
    static final String padding = " \t";
    
    public BlankPaddedSpliterator(String string, String seps) {
        super(string, "");
        setType('\\', TT_ESCAPE);
        setPadding(" \t");
        addSeparators(seps);
        if (overlap(padding, seps)) { 
            joinSeparators(true);
        }
    }
    
    private static boolean overlap(String padding, String seps) {
        CharacterIterator cit = new StringCharacterIterator(seps);
        for (char c = cit.first(); c != CharacterIterator.DONE; c = cit.next()) {
            if (padding.indexOf(c) >= 0) return true;
        }
        return false;
    }

    public BlankPaddedSpliterator(String string) {
        this(string,padding);
    }
}
