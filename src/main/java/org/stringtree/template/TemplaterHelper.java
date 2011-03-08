package org.stringtree.template;

import java.util.Map;

import org.stringtree.Fetcher;
import org.stringtree.finder.FetcherStringFinder;
import org.stringtree.finder.MapStringFinder;
import org.stringtree.finder.StringFinder;

public class TemplaterHelper {

    public static String expand(Templater templater, StringFinder context, String templateName) {
        StringCollector collector = new ByteArrayStringCollector();
        templater.expand(context, templateName, collector);
        return collector.toString();
    }

    public static String expand(Templater templater, Object object, StringFinder context, String templateName) {
        StringCollector collector = new ByteArrayStringCollector();
        expand(templater, object, context, templateName, collector);
        return collector.toString();
    }

    public static String expand(Templater templater, Object object, Map<String, Object> context, String templateName) {
        StringCollector collector = new ByteArrayStringCollector();
        expand(templater, object, new MapStringFinder(context), templateName, collector);
        return collector.toString();
    }

    public static void expand(Templater templater, Object object, StringFinder context,
            String templateName, StringCollector collector) {
        if (object != null) {
            Fetcher wrapped = new WrappedFetcher(object, context.getUnderlyingFetcher());
            templater.expand(new FetcherStringFinder(wrapped), templateName, collector);
        } else {
            templater.expand(context, templateName, collector);
        }
    }
}
