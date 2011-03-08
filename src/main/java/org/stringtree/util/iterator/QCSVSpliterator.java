package org.stringtree.util.iterator;

public class QCSVSpliterator extends BlankPaddedSpliterator {
    
    public QCSVSpliterator(String string) {
        super(string, ",");
        setQuotes("'\"");
    }
}
