package org.stringtree.fetcher.hierarchy;

import java.io.IOException;
import java.io.Reader;

import org.stringtree.Repository;

public interface HierarchyLoader {
    public void load(Repository repository, Reader reader)
        throws IOException;
}
