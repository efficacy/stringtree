package org.stringtree.fetcher;

import org.stringtree.Fetcher;
import org.stringtree.finder.ObjectFinder;
import org.stringtree.util.IntegerNumberUtils;
import org.stringtree.util.LongNumberUtils;

public class FetcherNumberHelper {
    
    public static int getInt(Fetcher context, String key, int dfl) {
        return IntegerNumberUtils.intValue(context.getObject(key), dfl);
    }

    public static int getInt(Fetcher context, String key) {
        return IntegerNumberUtils.intValue(context.getObject(key));
    }
    
    public static int getInt(ObjectFinder context, String key, int dfl) {
        return IntegerNumberUtils.intValue(context.getObject(key), dfl);
    }

    public static int getInt(ObjectFinder context, String key) {
        return IntegerNumberUtils.intValue(context.getObject(key));
    }
    
    public static long getLong(Fetcher context, String key, int dfl) {
        return LongNumberUtils.longValue(context.getObject(key), dfl);
    }

    public static long getLong(Fetcher context, String key) {
        return LongNumberUtils.longValue(context.getObject(key));
    }
    
    public static long getLong(ObjectFinder context, String key, int dfl) {
        return LongNumberUtils.longValue(context.getObject(key), dfl);
    }

    public static long getLong(ObjectFinder context, String key) {
        return LongNumberUtils.longValue(context.getObject(key));
    }
}
