package org.stringtree.template.pattern;

import org.stringtree.finder.StringFinder;
import org.stringtree.template.StringCollector;
import org.stringtree.template.Templater;
import org.stringtree.util.StringUtils;

/**
 * recognize patterns of the forms: 
 * item ? present -> if item is present, lookup ${present} 
 * item : absent -> if item is absent, lookup ${absent} 
 * item ? present : absent -> if item is present, lookup ${present} if item is absent, lookup ${absent} 
 * item ?: absent -> if item is present, lookup ${item} if item is absent, lookup ${absent}
 */
public class PresentAbsentPatternHandler implements TemplatePatternHandler {

    public Object getObject(String name, StringFinder context, Templater templater, StringCollector collector) {
        int present = name.indexOf('?');
        int absent = name.indexOf(':');

        if (present < 0 && absent < 0)
            return null;

        Object item = null;
        String ifpresent = "";
        String ifabsent = "";

        if (present > 0 && absent <= 0) {
            item = templater.getObject(name.substring(0, present).trim(), context, collector);
            ifpresent = name.substring(present + 1).trim();
        }
        if (present <= 0 && absent > 0) {
            item = templater.getObject(name.substring(0, absent).trim(), context, collector);
            ifabsent = name.substring(absent + 1).trim();
        }
        if (present >= 0 && absent >= 0 && absent > present) {
            String itemName = name.substring(0, present).trim();
            item = templater.getObject(itemName, context, collector);
            ifpresent = name.substring(present + 1, absent).trim();
            ifabsent = name.substring(absent + 1).trim();

            if (ifpresent.length() == 0) {
                ifpresent = itemName;
            }
        }

        return (isPresent(item)) 
            ? templater.getObject(ifpresent, context, collector) 
            : templater.getObject(ifabsent, context, collector);
    }

    private boolean isPresent(Object item) {
        if (StringUtils.isBlank(item) || (item instanceof Boolean && Boolean.FALSE.equals(item)))
            return false;
        
        return true;
    }
}
