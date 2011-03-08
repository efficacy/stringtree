package org.stringtree.util.spec;

import java.util.Iterator;

import org.stringtree.util.iterator.QCSVSpliterator;

public class CSVFormat extends ArrayFormat {
    
    protected Iterator<String> getIterator(String text) {
        return new QCSVSpliterator(text);
    }
}