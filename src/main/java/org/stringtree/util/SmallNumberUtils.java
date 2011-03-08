package org.stringtree.util;

public class SmallNumberUtils {
    
    public static short shortValue(Object obj, short dfl) {
        short ret = dfl;
        if (obj != null) {
            if (obj instanceof Number) {
                ret = ((Number)obj).shortValue();
            } else {
                try {
                    ret = Short.parseShort(obj.toString());
                } catch(NumberFormatException e) {
                    // not an exception condition, fall back to using the supplied default
                }
            }
        }
    
        return ret;
    }

    public static short shortValue(Object s) {
        return shortValue(s, (short)0);
    }
    
    public static byte byteValue(Object obj, byte dfl) {
        byte ret = dfl;
        if (obj != null) {
            if (obj instanceof Number) {
                ret = ((Number)obj).byteValue();
            } else {
                try {
                    ret = Byte.parseByte(obj.toString());
                } catch(NumberFormatException e) {
                    // not an exception condition, fall back to using the supplied default
                }
            }
        }
    
        return ret;
    }

    public static byte byteValue(Object s)  {
        return byteValue(s, (byte)0);
    }
}
