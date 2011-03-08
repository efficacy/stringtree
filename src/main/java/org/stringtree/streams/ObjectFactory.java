package org.stringtree.streams;

import org.stringtree.Fetcher;

public interface ObjectFactory {
    Object create(Fetcher context);
}
