package org.stringtree.fetcher.hierarchy;

import java.io.IOException;
import java.io.Writer;

import org.stringtree.Fetcher;

public interface HierarchyStorer {
    public void store(Fetcher fetcher, Writer writer)
        throws IOException;
}
