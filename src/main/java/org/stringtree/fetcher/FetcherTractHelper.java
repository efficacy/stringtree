package org.stringtree.fetcher;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.tract.TractHelper;

public class FetcherTractHelper {
    
    public static Tract getTract(Fetcher context, String key) {
        return TractHelper.convert(context.getObject(key), true);
    }
}
