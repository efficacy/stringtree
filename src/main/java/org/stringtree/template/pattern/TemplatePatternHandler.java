package org.stringtree.template.pattern;

import org.stringtree.finder.StringFinder;
import org.stringtree.template.StringCollector;
import org.stringtree.template.Templater;

public interface TemplatePatternHandler {
    Object getObject(String name, StringFinder context, Templater templater, StringCollector collector);
}
