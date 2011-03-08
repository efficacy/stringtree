package org.stringtree.juicer.tract;

import org.stringtree.Tract;
import org.stringtree.fetcher.FetcherBooleanHelper;
import org.stringtree.fetcher.StorerHelper;

public class TokenHelper {
    
	public static final String tokenAttribute = "token";

	public static boolean isToken(Tract tract) {
		return FetcherBooleanHelper.getBoolean(tract, tokenAttribute);
	}

	public static void clearTokenFlag(Tract tract) {
		StorerHelper.put(tract, tokenAttribute, Boolean.FALSE);
	}

	public static void setTokenFlag(Tract tract) {
        StorerHelper.put(tract, tokenAttribute, Boolean.TRUE);
	}
}
