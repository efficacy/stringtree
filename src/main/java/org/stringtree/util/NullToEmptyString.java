package org.stringtree.util;

public class NullToEmptyString implements ObjectToString {

    public String convert(Object value) {
        return null==value ? "" : value.toString();
    }

}
