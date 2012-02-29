package org.stringtree.util.iterator;

import java.util.Iterator;

public interface StringIterator extends Iterator<String>, Iterable<String> {
    String nextString();
}
