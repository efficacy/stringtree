package org.stringtree.util;

import org.stringtree.util.SequentialIdSource;

public class PrefixSequentialIdSource extends SequentialIdSource {
    private String prefix;
    private int width;

    public PrefixSequentialIdSource(String prefix, long initial, int width) {
        super(initial);
        this.prefix = prefix;
        this.width = width;
    }

    public PrefixSequentialIdSource(String prefix, long initial) {
        this(prefix, initial, 0);
    }

    public PrefixSequentialIdSource(String prefix) {
        this(prefix, 1);
    }

    public synchronized String next() {
        return id(super.next());
    }
    
    public String id(String string) {
        return prefix + pad(string);
    }

    private String pad(String string) {
        int len = string.length();
        if (len < width) {
            int n = width-len;
            StringBuffer ret = new StringBuffer(width);
            if (string.startsWith("-")) {
                ret.append("-");
                string = string.substring(1);
            }
            for (int i = 0; i < n; ++i) {
                ret.append('0');
            }
            ret.append(string);
            string = ret.toString();
        }
        return string;
    }

    public boolean valid(String id) {
        return null != id && id.startsWith(prefix) && super.valid(id.substring(prefix.length()));
    }
}
