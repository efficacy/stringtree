package org.stringtree.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class NumericValidator {
    
    public static boolean isValidNumericCharacter(char c) {
        return Character.isDigit(c);
    }

    public static boolean isValidInitalCharacter(char c) {
        return c == '-' || isValidNumericCharacter(c);
    }

    public static boolean isValidNumber(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Number) {
            return true;
        }

        String text = obj.toString().trim();
        if (text.length() == 0) {
            return false;
        }

        CharacterIterator ci = new StringCharacterIterator(text);

        char c = ci.first();
        if (!isValidInitalCharacter(c)) {
            return false;
        }

        for (c = ci.next(); c != CharacterIterator.DONE; c = ci.next()) {
            if (!isValidNumericCharacter(c)) {
                return false;
            }
        }

        return true;
    }
}
