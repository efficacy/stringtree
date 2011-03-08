package org.stringtree.template.pattern;

import org.stringtree.finder.StringFinder;
import org.stringtree.template.ByteArrayStringCollector;
import org.stringtree.template.StringCollector;
import org.stringtree.template.Templater;

/**
 * recognize patterns of the form: 
 *   '@text' -> return the result of expanding the template named in value 'text' 
 *   '^text' -> return the result of looking up the value named in value 'text'  
 */
public class IndirectPatternHandler implements TemplatePatternHandler {

    public Object getObject(String name, StringFinder context,
            Templater templater, StringCollector collector) {
        if (name.startsWith("@")) {
            String value = getIndirect(name, context);
            if (value != null) templater.expand(context, value, collector);
            return "";
        } else if (name.startsWith("^")) {
            String value = getIndirect(name, context);
            if (value != null) return context.getObject(value);
            return "";
        } else if (name.startsWith("~")) {
            String ret = "";
            String value = name.substring(1).trim();
            if (value != null) {
                ByteArrayStringCollector temp = new ByteArrayStringCollector();
                templater.expand(context, value, temp);
                ret = temp.toString();
            }
            return ret;
        }
        
        return null;
    }
    
    private String getIndirect(String name, StringFinder context) {
        name = name.substring(1);
        String value = context.get(name);
        if (value != null) {
            value = value.trim();
            if (!"".equals(value)) {
                return value;
            }
        }
        return null;
    }
}
