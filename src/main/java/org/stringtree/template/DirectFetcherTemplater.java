package org.stringtree.template;

import org.stringtree.Fetcher;

public class DirectFetcherTemplater extends RecursiveTemplater {
    
    private Fetcher templates;

    public DirectFetcherTemplater(Fetcher templates) {
        setTemplateFetcher(templates);
    }

    public void setTemplateFetcher(Fetcher templates) {
        this.templates = templates;
    }
    
    protected DirectFetcherTemplater() {
        // take care to call setTemplatefetcher if you use this constructor);
    }

    protected Object getTemplate(String templateName, Fetcher context) {
        return templates.getObject(templateName);
    }
    
    public String toString() {
        return "DirectFetcherTemplater[" + templates + "]";
    }
}
