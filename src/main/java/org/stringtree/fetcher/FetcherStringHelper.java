package org.stringtree.fetcher;

import org.stringtree.Fetcher;
import org.stringtree.finder.ObjectFinder;
import org.stringtree.util.StringUtils;

public class FetcherStringHelper {
    
    public static String getString(Fetcher context, String key) {
        return StringUtils.nullToEmpty(context.getObject(key));
    }

    public static String getString(Fetcher context, String key, String dfl) {
        return StringUtils.stringValue(context.getObject(key), dfl);
    }
    
    public static String getString(ObjectFinder context, String key) {
        return StringUtils.nullToEmpty(context.getObject(key));
    }

    public static String getString(ObjectFinder context, String key, String dfl) {
        return StringUtils.stringValue(context.getObject(key), dfl);
    }
}
