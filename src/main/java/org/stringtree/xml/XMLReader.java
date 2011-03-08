package org.stringtree.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.stringtree.util.StringUtils;

public class XMLReader extends RootTagHandler implements XReader {

    private int level = 0;
    private boolean trim = true;
    private boolean includeDirectives = false;
    private boolean ignoreRoot = false;
    private boolean allowSingles = true;
    private boolean stripNamespaces = false;
    private String attributePrefix = "@";

    private String stripNamespaceIfRequired(String name) {
        if (stripNamespaces && name.contains(":")) {
            name = name.substring(name.indexOf(":")+1);
        }
        return name;
    }
    
    @SuppressWarnings("unchecked")
    public Object doPair(Object context, String name, Map<String, String> attributes, Reader in) throws IOException {
        Map<String, Object> map = (Map<String, Object>)context; 
        String oldCdata = getCdata();

        Map<String, Object> child = null;
        if (level == 0 && ignoreRoot) {
            child = map;
        } else {
            child = new HashMap<String, Object>();
        }
        
        ++level;
          Tag tag = (Tag)super.doPair(child, name, attributes, in);
        --level;
        if (level > 0 || ignoreRoot==false) populate(name, attributes, map, child, tag);
        
        setCdata(oldCdata);
        return tag;
    }

    @SuppressWarnings("unchecked")
    public Object doSingle(Object context, String name, Map<String, String> attributes) {
//System.err.println("doSingle name=" + name + " level=" + level + " attributes=" + attributes);
        Map<String, Object> map = (Map<String, Object>)context; 
        Map<String, Object> child = new HashMap<String, Object>();
        Tag tag = (Tag)super.doSingle(context, name, attributes);
        if (includeDirectives || !name.startsWith("?")) populate(name, attributes, map, child, tag);
        
        return tag;
    }


    private void populate(String name, Map<String, String> attributes, Map<String, Object> parent, Map<String, Object> child, Tag tag) {
        name = stripNamespaceIfRequired(name);
        
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String stripped = stripNamespaceIfRequired(entry.getKey());
            add(child, attributePrefix + stripped, entry.getValue());
        }
        
        Map<String, Object> cdataParent = parent;
        String cdataName = name;
        if (!child.isEmpty()) {
            add(parent, name, child);
            cdataName = "text()";
            cdataParent = child;
        }

        String cdata = tag.getData();
        if (trim) cdata = cdata.trim();
        if (!StringUtils.isBlank(cdata)) {
            add(cdataParent, cdataName, cdata);
        }
    }

    @SuppressWarnings("unchecked")
    private void add(Map<String, Object> map, String name, Object value) {
        Object child = map.get(name);
        if (null == child) {
            if (!(value instanceof Collection) && !allowSingles) {
                Collection<Object> collection = new ArrayList<Object>();
                collection.add(value);
                value = collection;
            }
            map.put(name, value);
        } else if (child instanceof Collection) {
            ((Collection<Object>)child).add(value);
        } else {
            Collection<Object> collection = new ArrayList<Object>();
            collection.add(child);
            collection.add(value);
            map.put(name, collection);
        }
    }

    public Object read(String string) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            run(map, new StringReader(string));
        } catch (IOException e) {
             e.printStackTrace();
        }

        if (allowSingles) return map;
        
        Collection<Object> collection = new ArrayList<Object>();
        collection.add(map);
        return collection;
    }

    public void setTrimCdata(boolean trim) {
        this.trim  = trim;
    }

    public void setIncludeProcessingDirectives(boolean includeDirectives) {
        this.includeDirectives = includeDirectives;
    }

    public void setIgnoreRoot(boolean ignore) {
        this.ignoreRoot = ignore;
    }

    public void setAllowSingles(boolean allow) {
        this.allowSingles = allow;
    }

    public void setStripNamespaces(boolean strip) {
        this.stripNamespaces = strip;
    }

    public void setAttributePrefix(String prefix) {
        this.attributePrefix = prefix;
    }
}
