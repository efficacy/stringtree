package org.stringtree.template.pattern;

import java.util.StringTokenizer;

import org.stringtree.finder.StringFinder;
import org.stringtree.template.StringCollector;
import org.stringtree.template.Templater;
import org.stringtree.util.MethodCallUtils;

public class PipePatternHandler implements TemplatePatternHandler {

    public Object getObject(String name, StringFinder context, Templater templater, StringCollector collector) {
        if (name.indexOf("|") < 0)
            return null;

        StringTokenizer tok = new StringTokenizer(name, "|");
        String item = tok.nextToken();
        Object ret = templater.getObject(item.trim(), context, collector);
        while (ret != null && tok.hasMoreTokens()){
            String pipe = tok.nextToken();
            int pos = pipe.lastIndexOf('.', pipe.length());
            String destinationName = pipe.substring(0, pos);
            Object destination = templater.getObject(destinationName, context, collector);
            String methodName = pipe.substring(pos + 1);
           
            ret = MethodCallUtils.callOptionalContext(context, destination, methodName, ret);
        }
        
        return ret;
    }

}
