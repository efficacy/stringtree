package org.stringtree.util;

public class FloatNumberUtils {
    
    public static float floatValue(Object obj, float dfl) {
        float ret = dfl;
        if (obj != null) {
            if (obj instanceof Number) {
                ret = ((Number) obj).floatValue();
            } else {
                try {
                    ret = Float.parseFloat(obj.toString());
                } catch (NumberFormatException e) {
                    // not an exception consition, fall back to using the
                    // supplied default
                }
            }
        }

        return ret;
    }

    public static float floatValue(Object s) {
        return floatValue(s, 0.0f);
    }

    public static double doubleValue(Object obj, double dfl) {
        double ret = dfl;
        if (obj != null) {
            if (obj instanceof Number) {
                ret = ((Number) obj).doubleValue();
            } else {
                try {
                    ret = Double.parseDouble(obj.toString());
                } catch (NumberFormatException e) {
                    // not an exception condition, fall back to using the
                    // supplied default
                }
            }
        }

        return ret;
    }

    public static double doubleValue(Object s) {
        return doubleValue(s, 0.0);
    }
}
