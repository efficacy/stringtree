package org.stringtree.xml;

public interface XReader {
    void setAttributePrefix(String prefix);
    void setTrimCdata(boolean trim);
    Object read(String text);
    void setAllowSingles(boolean allow);
    void setIgnoreRoot(boolean ignore);
    void setStripNamespaces(boolean strip);
    void setIncludeProcessingDirectives(boolean b);
}
