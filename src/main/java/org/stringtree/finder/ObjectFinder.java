package org.stringtree.finder;

import org.stringtree.Container;
import org.stringtree.Fetcher;

public interface ObjectFinder extends Container, Fetcher {
    Object getObject(String name);
    Fetcher getUnderlyingFetcher();
}
