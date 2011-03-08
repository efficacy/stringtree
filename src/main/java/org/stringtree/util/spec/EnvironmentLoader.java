package org.stringtree.util.spec;

import java.util.Map;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.StorerHelper;
import org.stringtree.finder.FetcherStringFinder;
import org.stringtree.finder.StringFinder;

public class EnvironmentLoader {

    public static void loadEnvironment(Fetcher context) {
    	Map<String, String> env = System.getenv();
    	for (Map.Entry<String, String> entry : env.entrySet()) {
    		StorerHelper.put(context, entry.getKey(), entry.getValue());
    	}
    }

    public static StringFinder loadEnvironment() {
    	Fetcher fetcher = new MapFetcher();
        loadEnvironment(fetcher);
        return new FetcherStringFinder(fetcher);
    }
}
