package org.stringtree.fetcher;

import org.stringtree.Fetcher;

public interface ContextSensitiveFetcher extends Fetcher {
    void setContext(Fetcher context);
}
