package org.stringtree.util;

import org.stringtree.fetcher.LiveObjectWrapper;

public class StringUtils {
    
    public static boolean isBlank(String s) {
        return s == null || s.length() == 0 || s.trim().equals("");
    }

    public static boolean isBlank(Object obj) {
        return obj == null || isBlank(obj.toString());
    }

    public static String stringValue(Object obj, String dfl) {
        String ret = dfl;

        if (obj instanceof LiveObjectWrapper) {
            obj = ((LiveObjectWrapper) obj).getObject();
        }
        if (obj != null) {
            ret = (obj instanceof String) ? (String) obj : obj.toString();
        }

        return ret;
    }

    public static String stringValue(Object obj) {
        return stringValue(obj, null);
    }

    public static String nullToEmpty(Object obj) {
        return stringValue(obj, "");
    }
}
