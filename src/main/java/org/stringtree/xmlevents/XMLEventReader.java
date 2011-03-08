package org.stringtree.xmlevents;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.stringtree.xml.XReader;

public class XMLEventReader implements XReader {

    public static final String KEY_ATTR_PREFIX = "~ATTR_PREFIX";
    public static final String KEY_TRIM_CDATA = "~TRIM_CDATA";
    public static final String KEY_ALLOW_SINGLES = "~ALLOW_SINGLES";
    public static final String KEY_IGNORE_ROOT = "~IGNORE_ROOT";
    public static final String KEY_STRIP_NAMESPACES = "~STRIP_NAMESPACES";
    public static final String KEY_INCLUDE_DIRECTIVES = "~INCLUDE_DIRECTIVES";
    
    private Map<String, Object> parameters;
    
    public XMLEventReader() {
        parameters = new HashMap<String, Object>();
        parameters.put(KEY_TRIM_CDATA, Boolean.valueOf(true));
        parameters.put(KEY_INCLUDE_DIRECTIVES, Boolean.valueOf(false));
        parameters.put(KEY_IGNORE_ROOT, Boolean.valueOf(false));
        parameters.put(KEY_ALLOW_SINGLES, Boolean.valueOf(true));
        parameters.put(KEY_ATTR_PREFIX, "@");
    }
    
    @SuppressWarnings("unchecked")
	public Object read(String text) {
        Map<String, Object> map = new HashMap<String,Object>();
        XMLEventParser parser = new XMLEventParser(parameters);
        XMLEventReaderHandler handler = new XMLEventReaderHandler(parameters);
        try {
            map = (Map<String, Object>)parser.process(new StringReader(text), handler, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void setAttributePrefix(String prefix) {
        parameters.put(KEY_ATTR_PREFIX, prefix);
    }

    public void setTrimCdata(boolean trimCdata) {
        parameters.put(KEY_TRIM_CDATA, Boolean.valueOf(trimCdata));
    }

    public void setAllowSingles(boolean allowSingles) {
        parameters.put(KEY_ALLOW_SINGLES, Boolean.valueOf(allowSingles));
    }

    public void setIgnoreRoot(boolean ignoreRoot) {
        parameters.put(KEY_IGNORE_ROOT, Boolean.valueOf(ignoreRoot));
    }

    public void setIncludeProcessingDirectives(boolean includeProcessingDirectives) {
        parameters.put(KEY_INCLUDE_DIRECTIVES, Boolean.valueOf(includeProcessingDirectives));
    }

    public void setStripNamespaces(boolean strip) {
        parameters.put(KEY_STRIP_NAMESPACES, Boolean.valueOf(strip));
    }
}
