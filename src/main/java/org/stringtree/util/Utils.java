package org.stringtree.util;

public class Utils {
    
    public static final boolean same(Object a, Object b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }

    public static void sleep(int millis, boolean log) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            if (log) {
                ie.printStackTrace();
            }
        }
    }

    public static void sleep(int millis) {
        sleep(millis, false);
    }
}
