package org.stringtree.template.pattern;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import org.stringtree.Listable;
import org.stringtree.finder.StringFinder;
import org.stringtree.template.StringCollector;
import org.stringtree.template.Templater;
import org.stringtree.template.TemplaterHelper;
import org.stringtree.util.ArrayIterator;
import org.stringtree.util.PublicMapEntryIterator;
import org.stringtree.util.iterator.EnumerationIterator;

/**
 * recognize patterns of the forms: 
 *   * template -> expand named template 
 *   item * template -> push (each element of) item into "this" and expand named template
 *   item * template / sep -> push (each element of) item into "this" and expand named template separated by ${sep} 
 *   item * / sep -> insert (each element of) item separated by ${sep}
 *   (note that if / is present, but sep is empty, use a newline as a separator)
 */
public class IteratingPatternHandler implements TemplatePatternHandler {

    private static final String NL = System.getProperty("line.separator");
    private static final String QUOTED_NEWLINE = "'" + NL + "'";

    @SuppressWarnings("unchecked")
    public Object getObject(String name, StringFinder context,
            Templater templater, StringCollector collector) {
        int times = name.indexOf('*');
        int divide = name.indexOf('/');
        int missing = name.indexOf('%');
        if (times < 0 || (divide >= 0 && divide < times) || (missing >= 0 && missing < times))
            return null;

        if (times == 0) {
            templater.expand(context, name.substring(1).trim(), collector);
            return "";
        }

        String template = "";
        String separator = "";
        String alternative = "";
        String itemName = name.substring(0, times).trim();
        
        int template_start = times + 1;
        int template_end = name.length();
        if (divide > 0 && missing > 0) {
            template_end = Math.min(divide,missing);
        } else if (divide > 0) {
            template_end = divide;
        } else if (missing > 0) {
            template_end = missing;
        }
        
        template = name.substring(template_start, template_end).trim();
        if (template.length() == 0 && divide > 0) {
            template = "@this";
        }
        
        if (divide > 0) {
            int separator_start = divide + 1;
            int separator_end = name.length();
            if (missing > 0) {
                separator_end = missing;
            }
            separator = name.substring(separator_start, separator_end).trim();
            if (separator.length() == 0) {
                separator = QUOTED_NEWLINE;
            }
        }
        
        if (missing > 0) {
            alternative = name.substring(missing + 1).trim();
        }

        Object item = templater.getObject(itemName, context, collector);
        
        if (item != null) {
            Iterator iterator = null;
    
            if (item instanceof Iterator) {
                iterator = ((Iterator) item);
            } else if (item instanceof Listable) {
                iterator = ((Listable) item).list();
            } else if (item instanceof Iterable) {
                iterator = ((Iterable) item).iterator();
            } else if (item instanceof Enumeration) {
                iterator = new EnumerationIterator((Enumeration) item);
            } else if (item instanceof Object[]) {
                iterator = new ArrayIterator<Object>((Object[]) item);
            }else if (item instanceof Map) {
                iterator = new PublicMapEntryIterator(((Map)item).entrySet().iterator());
            }

            if (iterator != null) {
                while (iterator.hasNext()) {
                    Object object = iterator.next(); 
                    expand(object, template, context, templater, collector);
                    if (iterator.hasNext()) {
                        collector.write(templater.getObject(separator, context, collector));
                    }
                }
            } else {
                expand(item, template, context, templater, collector);
            }
        } else if (missing > 0) {
            collector.write(templater.get(alternative, context, collector));
        }

        return "";

    }

    private void expand(Object obj, String template, StringFinder context,
            Templater templater, StringCollector collector) {
        TemplaterHelper.expand(templater, obj, context, template, collector);
    }
}
