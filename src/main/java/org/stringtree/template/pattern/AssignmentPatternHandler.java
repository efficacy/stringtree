package org.stringtree.template.pattern;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.StorerHelper;
import org.stringtree.finder.StringFinder;
import org.stringtree.template.StringCollector;
import org.stringtree.template.Templater;

/**
 * Recognise patterns of the form: 
 *   name=value -> return ""
 * As a side effect, set the context value "name" to value
 * If name starts with "@" set the value in the "base" context (if available)
 * If name starts with "^" set the value in the "parent" context (if available)
 * Note that "^" may be repeated to refer to the parent of the parent etc.
 */
public class AssignmentPatternHandler implements TemplatePatternHandler {

    public Object getObject(String name, StringFinder context, Templater templater, StringCollector collector) {
        int eq = name.indexOf("=");
        if (eq <= 0) {
            return null;
        }
        
        Fetcher fetcher = context.getUnderlyingFetcher();
        
        String var = name.substring(0, eq);
        if (var.startsWith("@")) {
            var = var.substring(1);
            fetcher = (Fetcher) context.getObject(Templater.BASE);
            if (null == fetcher) {
                System.err.println("WARNING: context " + context + " has no entry " + Templater.BASE);
                //Diagnostics.dumpFetcher(context, "context");
            }
        } else {
            while (var.startsWith("^")) {
                var = var.substring(1);
                fetcher = (Fetcher) fetcher.getObject(Templater.PARENT);
            }
        }
        
        if (null != fetcher) {
            Object val = templater.getObject(name.substring(eq+1), context, collector);
            StorerHelper.put(fetcher, var, val);
        } else {
            System.err.println("AssignmentPatternHandler attempt to assign to unknown context '" + name + "'");
        }
        return "";
    }
}
