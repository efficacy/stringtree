package org.stringtree.template;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.stringtree.Fetcher;
import org.stringtree.Repository;
import org.stringtree.fetcher.StringDirectoryRepository;
import org.stringtree.fetcher.TractURLFetcher;
import org.stringtree.fetcher.filter.SuffixFilter;
import org.stringtree.finder.FetcherStringKeeper;
import org.stringtree.finder.MapStringKeeper;
import org.stringtree.finder.StringKeeper;
import org.stringtree.util.URLReadingUtils;

public class EasyTemplater extends DirectFetcherTemplater implements Repository {

    private StringKeeper context;
    
    public EasyTemplater(Fetcher templates, StringKeeper context) {
        super(templates);
        this.context = context;
    }
    
    public EasyTemplater(Fetcher templates, Fetcher context) {
        super(templates);
        this.context = new FetcherStringKeeper(context);
    }
    
    public EasyTemplater(Fetcher templates) {
        this(templates, new MapStringKeeper());
    }

    public EasyTemplater(String location) {
        try {
            setTemplateFetcher(new TractURLFetcher(URLReadingUtils.findURL(location, "file")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.context = new MapStringKeeper();
    }

    public EasyTemplater(URL url) {
        this(new TractURLFetcher(url));
    }

    public EasyTemplater(File file) {
        this(new StringDirectoryRepository(file, new SuffixFilter(".tpl")));
    }

    public String toString(String templateName) {
        ByteArrayStringCollector collector = expand(templateName);
        return collector.toString();
    }

    public String toString(Object template) {
        ByteArrayStringCollector collector = expand(template);
        return collector.toString();
    }

    private ByteArrayStringCollector expand(String templateName) {
        ByteArrayStringCollector collector = new ByteArrayStringCollector();
        expand(context, templateName, collector);
        return collector;
    }

    private ByteArrayStringCollector expand(Object template) {
        ByteArrayStringCollector collector = new ByteArrayStringCollector();
        expandTemplate(context, template, collector);
        return collector;
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }

    public byte[] toBytes(String templateName) {
        ByteArrayStringCollector collector = expand(templateName);
        return collector.toByteArray();
    }

    public Object getObject(String name) {
        return context.getObject(name);
    }

    public void clear() {
        context.clear();
    }

    public boolean isLocked() {
        return context.isLocked();
    }

    public void lock() {
        context.lock();
    }

    public void remove(String name) {
        context.remove(name);
    }
}
