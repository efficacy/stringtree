package org.stringtree.finder;

import org.stringtree.Tract;

public interface TractFinder extends ObjectFinder {
    Tract get(String name);
}
