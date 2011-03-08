package org.stringtree.json.events;

import java.util.HashMap;
import java.util.Map;

public class JSONConstants {

    protected static final Object OBJECT_END = new Object();
    protected static final Object ARRAY_END = new Object();
    protected static final Object COLON = new Object();
    protected static final Object COMMA = new Object();
    protected static final Object VALUE = new Object();

    protected static Map<Character, Character> escapes = new HashMap<Character, Character>();

    static {
        escapes.put(Character.valueOf('"'), Character.valueOf('"'));
        escapes.put(Character.valueOf('\\'), Character.valueOf('\\'));
        escapes.put(Character.valueOf('/'), Character.valueOf('/'));
        escapes.put(Character.valueOf('b'), Character.valueOf('\b'));
        escapes.put(Character.valueOf('f'), Character.valueOf('\f'));
        escapes.put(Character.valueOf('n'), Character.valueOf('\n'));
        escapes.put(Character.valueOf('r'), Character.valueOf('\r'));
        escapes.put(Character.valueOf('t'), Character.valueOf('\t'));
    }

    public static final int FIRST = 0;
    public static final int CURRENT = 1;
    public static final int NEXT = 2;

}
