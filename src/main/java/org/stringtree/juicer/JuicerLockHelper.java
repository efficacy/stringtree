package org.stringtree.juicer;

import org.stringtree.Tract;
import org.stringtree.fetcher.FetcherBooleanHelper;
import org.stringtree.fetcher.StorerHelper;

public class JuicerLockHelper {
    
	public static final String key = "locked";

	public static boolean isLocked(Tract tract) {
		return tract != null && FetcherBooleanHelper.getBoolean(tract, key);
	}

	public static void lock(Tract tract) {
		if (tract != null) {
			StorerHelper.put(tract, key, Boolean.TRUE);
		}
	}

	public static void unlock(Tract tract) {
		if (tract != null) {
			tract.remove(key);
		}
	}
}
