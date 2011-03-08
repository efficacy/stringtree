package org.stringtree.fetcher.hierarchy;

import java.util.Map;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.ListableHelper;
import org.stringtree.fetcher.StorerHelper;
import org.stringtree.util.ArrayIterator;
import org.stringtree.util.IntegerNumberUtils;
import org.stringtree.util.StringUtils;
import org.stringtree.util.sort.SortedIteratorIterator;

public class FlatHierarchyHelper implements HierarchyHelper {
    public static final int NODE = 1;
    public static final int ATTRIBUTE = 2;

    public static final String COUNT = "~";
    private static final String ATTRIBUTE_PREFIX = "@";
    private static final String ATTRIBUTE_SUFFIX = "";
    private static final String CHILD_PREFIX = "[";
    private static final String CHILD_SUFFIX = "]";
    private static final String SEPARATOR = "/";
    private static final String ROOT_SEPARATOR = "//";

    
    private int getType(String key) {
        return key.indexOf(ATTRIBUTE_PREFIX)<0 ? NODE : ATTRIBUTE;
    }

    public String getRootKey(Fetcher fetcher) {
        return null;
    }
    
    public String getAttributeKey(String key, String attribute) {
        return key + SEPARATOR + ATTRIBUTE_PREFIX + attribute + ATTRIBUTE_SUFFIX;
    }

    public String getChildKey(String key, String child) {
         StringBuffer ret = new StringBuffer();
         if (key != null) {
             ret.append(key);
             ret.append(SEPARATOR);
         } else {
             ret.append(ROOT_SEPARATOR);
         }
         ret.append(child);
         return ret.toString();
    }
    
    public String getParentKey(String key) {
        int sep = key.lastIndexOf(SEPARATOR);
        return sep >= 0 ? key.substring(0,sep) : null;
    }

    public int getDepth(String key) {
        if (key == null) {
            return 0;
        }
        
        int ret = 0;
        StringTokenizer tok = new StringTokenizer(key, SEPARATOR);
        while (tok.hasMoreTokens()) {
            ++ret;
            tok.nextToken();
        }
        
        return ret;
    }
    
    public String getLeafName(String key) {
        if (key.endsWith(CHILD_PREFIX)) {
            return null;
        }
        
        int sep = key.lastIndexOf(SEPARATOR);
        if (key.charAt(sep+1) == '@') {
            ++sep;
        }
        
        key = key.substring(sep+1);
        
        int child = key.lastIndexOf(CHILD_PREFIX);
        
        if (child >= 0) {
            key = key.substring(0, child);
        }
        
        return key;
    }

    public int count(Fetcher fetcher, String key) {
        return IntegerNumberUtils.intValue(fetcher.getObject(key + COUNT));
    }

    public int countChildren(Fetcher fetcher, String key, String child) {
        return count(fetcher, getChildKey(key, child));
    }
    
    public String createChildKey(Fetcher fetcher, String key, String child) {
        String ret = getChildKey(key, child);
        int count = countChildren(fetcher, key, child) + 1;
        
        StorerHelper.put(fetcher, ret+COUNT, Integer.toString(count));
        StringBuffer buf = new StringBuffer(ret);
        buf.append(CHILD_PREFIX);
        buf.append(count);
        buf.append(CHILD_SUFFIX);
        ret = buf.toString();
        
        return ret;
    }
    
    public Iterator<String> getChildKeys(Fetcher fetcher, String key, String child) {
        String base = getChildKey(key, child);
        int n = countChildren(fetcher, key, child);
        String[] ret = new String[n];
        
        for (int i = 0; i < n; ++i) {
            ret[i] = base + CHILD_PREFIX + (i+1) + CHILD_SUFFIX;
        }
        
        return new ArrayIterator<String>(ret);
    }

    public String getFirstChildKey(Fetcher fetcher, String key, String child) {
        String ret = null;
        int n = countChildren(fetcher, key, child);
        
        if (n > 1) {
            Iterator<String> it = getChildKeys(fetcher, key, child);
            ret = (it != null && it.hasNext()) ? (String)it.next() : null;
        } else {
            ret = getChildKey(key, child);
        }
        
        return ret;
    }

    public Iterator<String> depthFirst(Fetcher fetcher) {
        return new SortedIteratorIterator<String>(ListableHelper.list(fetcher)); // TODO does this do it in the right (depth first) order ?
    }    

    public Object getAttribute(Fetcher fetcher, String key, String attribute) {
        return fetcher.getObject(getAttributeKey(key, attribute));
    }
    
    public void setAttribute(Fetcher fetcher, String key, String attribute, Object value) {
        StorerHelper.put(fetcher, getAttributeKey(key, attribute), value);
    }

    public void setAttributes(Fetcher fetcher, String key, Map<String,?> args) {
        Iterator<String> it = args.keySet().iterator();
        while (it.hasNext()) {
            String arg = StringUtils.stringValue(it.next());
            setAttribute(fetcher, key, arg, args.get(arg));
        }
    }

    public Object getChild(Fetcher fetcher, String key, String child) {
        if (!child.endsWith("]")) {
            child += "[1]";
        }
        return fetcher.getObject(getChildKey(key, child));
    }

    public String getStringValue(Fetcher fetcher, String key) {
        return StringUtils.stringValue(fetcher.getObject(key));
    }
    
    public int getIntegerValue(Fetcher fetcher, String key) {
        return IntegerNumberUtils.intValue(fetcher.getObject(key));
    }
    
    public String getStringAttribute(Fetcher fetcher, String key, String attribute) {
        return StringUtils.stringValue(getAttribute(fetcher, key, attribute));
    }
    
    public int getIntegerAttribute(Fetcher fetcher, String key, String attribute) {
        return IntegerNumberUtils.intValue(getAttribute(fetcher, key, attribute));
    }

    public String getStringChild(Fetcher fetcher, String key, String child) {
        return StringUtils.stringValue(getChild(fetcher, key, child));
    }
    
    public int getIntegerChild(Fetcher fetcher, String key, String child) {
        return IntegerNumberUtils.intValue(getChild(fetcher, key, child));
    }
    
    
    public String putChild(Fetcher fetcher, String parent, String child, Object value) {
        String key = createChildKey(fetcher, parent, child);
        StorerHelper.put(fetcher, key, value);
        return key;
    }
    
    public String putAttribute(Fetcher fetcher, String parent, String attribute, Object value) {
        String key = getAttributeKey(parent, attribute);
        StorerHelper.put(fetcher, key, value);
        return key;
    }

    public void putCdata(Fetcher fetcher, String key, String cdata) {
        StorerHelper.put(fetcher, key, cdata);
    }

    public boolean isAttribute(String key) {
        return getType(key) == ATTRIBUTE;
    }

    public boolean isNode(String key) {
        return getType(key) == NODE;
    }
}
