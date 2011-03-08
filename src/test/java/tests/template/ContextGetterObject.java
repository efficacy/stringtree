package tests.template;

import org.stringtree.finder.StringFinder;

public class ContextGetterObject {
    public Object get(StringFinder context, String name) {
        return "[" + context.get(name) + "]";
    }
}
