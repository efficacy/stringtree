package org.stringtree.fetcher.hierarchy;

import java.util.Iterator;
import java.util.Map;

import org.stringtree.Fetcher;

public interface HierarchyHelper {
    boolean isAttribute(String key);
    boolean isNode(String key);

    String getRootKey(Fetcher fetcher);
    String getAttributeKey(String key, String attribute);
    String getChildKey(String key, String child);
    String getParentKey(String key);
    int getDepth(String key);
    String getLeafName(String key);
    int count(Fetcher fetcher, String key);
    int countChildren(Fetcher fetcher, String key, String child);
    String createChildKey(Fetcher fetcher, String key, String child);
    Iterator<String> getChildKeys(Fetcher fetcher, String key, String child);
    String getFirstChildKey(Fetcher fetcher, String key, String child);
    Iterator<String> depthFirst(Fetcher fetcher);
    Object getAttribute(Fetcher fetcher, String key, String attribute);
    void setAttribute(Fetcher fetcher, String key, String attribute, Object value);
    void setAttributes(Fetcher fetcher, String key, Map<String,?> args);
    Object getChild(Fetcher fetcher, String key, String child);
    String getStringValue(Fetcher fetcher, String key);
    int getIntegerValue(Fetcher fetcher, String key);
    String getStringAttribute(Fetcher fetcher, String key, String attribute);
    int getIntegerAttribute(Fetcher fetcher, String key, String attribute);
    String getStringChild(Fetcher fetcher, String key, String child);
    int getIntegerChild(Fetcher fetcher, String key, String child);
    
    String putChild(Fetcher fetcher, String parent, String child, Object value);
    String putAttribute(Fetcher fetcher, String parent, String attribute, Object value);
    void putCdata(Fetcher fetcher, String key, String cdata) ;
}
