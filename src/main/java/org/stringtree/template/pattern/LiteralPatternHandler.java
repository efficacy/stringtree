package org.stringtree.template.pattern;

import org.stringtree.finder.StringFinder;
import org.stringtree.template.StringCollector;
import org.stringtree.template.Templater;

/**
 * recognize patterns of the form: 
 *   'text' -> return the value of the text 
 * Note that text must not contain '}/*?: If you need to, then
 * place the text in a separate template, and include with ${*tpl}
 */
public class LiteralPatternHandler implements TemplatePatternHandler {

    public Object getObject(String name, StringFinder context,
            Templater templater, StringCollector collector) {
        if (!name.startsWith("'"))
            return null;
        String contents = name.substring(1, name.length() - 1);
        return (contents.indexOf('\'') < 0) ? contents : null;
    }
}
