package org.stringtree.util.spec;

import org.stringtree.finder.StringFinder;

public interface Handler {
    void open(StringFinder context);
    Object parse(String name, Object value);
    void close(StringFinder context);
}
