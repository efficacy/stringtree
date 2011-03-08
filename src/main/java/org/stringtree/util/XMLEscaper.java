package org.stringtree.util;

public class XMLEscaper implements ObjectToString {

    public String convert(Object value) {
        return null==value ? "" : value.toString().
            replace("&", "&amp;").
            replace("<", "&lt;").
            replace(">","&gt;").
            replace("'","&apos;").
            replace("\"", "&quot;");
    }

    public String revert(Object value) {
        return null==value ? "" : value.toString().
            replace("&lt;", "<").
            replace("&gt;", ">").
            replace("&apos;", "'").
            replace("&quot;", "\"").
            replace("&amp;", "&");
    }
}
