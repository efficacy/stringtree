package org.stringtree.fetcher;

import org.stringtree.Fetcher;
import org.stringtree.finder.ObjectFinder;
import org.stringtree.util.BooleanUtils;

public class FetcherBooleanHelper {
    
	public static boolean getBoolean(Fetcher context, String key, boolean dfl) {
		return BooleanUtils.booleanValue(context.getObject(key), dfl);
	}

    public static boolean getBoolean(ObjectFinder context, String key, boolean dfl) {
        return BooleanUtils.booleanValue(context.getObject(key), dfl);
    }

	public static boolean getBoolean(Fetcher context, String key) {
		return BooleanUtils.booleanValue(context.getObject(key));
	}

    public static boolean getBoolean(ObjectFinder context, String key) {
        return BooleanUtils.booleanValue(context.getObject(key));
    }
}
