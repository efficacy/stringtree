package org.stringtree.util;

public class LongNumberUtils {
    
    public static long longValue(Object obj, long dfl) {
        long ret = dfl;
        if (obj != null) {
            if (obj instanceof Number) {
                ret = ((Number) obj).longValue();
            } else {
                try {
                    ret = Long.parseLong(obj.toString());
                } catch (NumberFormatException e) {
                    // not an exception condition, fall back to using the
                    // supplied default
                }
            }
        }

        return ret;
    }

    public static long longValue(Object s) {
        return longValue(s, 0);
    }
}
