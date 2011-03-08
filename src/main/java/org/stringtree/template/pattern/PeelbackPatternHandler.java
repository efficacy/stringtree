package org.stringtree.template.pattern;

import org.stringtree.fetcher.FetcherHelper;
import org.stringtree.finder.StringFinder;
import org.stringtree.template.StringCollector;
import org.stringtree.template.Templater;

public class PeelbackPatternHandler implements TemplatePatternHandler {

    public Object getObject(String name, StringFinder context, 
            Templater templater, StringCollector collector) {
        if (name.indexOf(".") < 0)
            return null;

        return FetcherHelper.getPeelback(context.getUnderlyingFetcher(), name, ".");
    }

}
