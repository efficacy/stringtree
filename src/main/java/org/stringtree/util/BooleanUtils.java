package org.stringtree.util;

public class BooleanUtils {
    
    public static boolean booleanValue(Object value, boolean dfl) {
        boolean ret = dfl;

        if (value instanceof Boolean) {
            ret = ((Boolean) value).booleanValue();
        } else if (value != null) {
            String svalue = value.toString();
            if ("true".equalsIgnoreCase(svalue) || "y".equalsIgnoreCase(svalue)
                    || "t".equalsIgnoreCase(svalue)) {
                ret = true;
            }

            if ("false".equalsIgnoreCase(svalue)
                    || "n".equalsIgnoreCase(svalue)
                    || "f".equalsIgnoreCase(svalue)) {
                ret = false;
            }
        }

        return ret;
    }

    public static boolean booleanValue(Object value) {
        return booleanValue(value, false);
    }

    public static String toString(boolean value) {
        return value ? "true" : "false";
    }

    public static String trueFalseNull(Boolean value, String t, String f, String n) {
        if (null == value) return n;
        return value.booleanValue() ? t : f;
    }
}
