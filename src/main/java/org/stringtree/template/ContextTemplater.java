package org.stringtree.template;

import org.stringtree.Fetcher;

public class ContextTemplater extends RecursiveTemplater {

    private String key;

    public ContextTemplater(String key) {
        this.key = key;
    }

    public ContextTemplater() {
        this(Templater.TEMPLATE_SOURCE + ".");
    }

    protected Object getTemplate(String templateName, Fetcher context) {
        return context.getObject(key + templateName);
    }
}
