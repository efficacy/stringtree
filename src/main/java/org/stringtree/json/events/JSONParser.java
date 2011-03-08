package org.stringtree.json.events;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;


public class JSONParser {
    protected CharacterIterator it;
    protected char c;
    protected Object token;
    protected StringBuffer buf = new StringBuffer();
    
    protected JSONParserListener listener;
    protected Object context;
    
    public JSONParser(JSONParserListener listener, Object context) {
        this.listener = listener;
        this.context = context;
    }

    protected char next() {
        c = it.next();
        return c;
    }

    protected void skipWhiteSpace() {
        while (Character.isWhitespace(c)) {
            next();
        }
    }

    protected void parse(CharacterIterator ci, int start) {
        listener.start(start);
        it = ci;
        switch (start) {
        case JSONConstants.FIRST:
            c = it.first();
            break;
        case JSONConstants.CURRENT:
            c = it.current();
            break;
        case JSONConstants.NEXT:
            c = it.next();
            break;
        }
        parse(true);
        listener.finish();
    }

    public void parse(CharacterIterator it) {
        parse(it, JSONConstants.NEXT);
    }

    public void parse(String string) {
        parse(new StringCharacterIterator(string), JSONConstants.FIRST);
    }

    protected void parse(boolean store) {
        skipWhiteSpace();
        char ch = c;
        next();
        switch (ch) {
            case ']': token = JSONConstants.ARRAY_END; break;
            case ',': token = JSONConstants.COMMA; break;
            case '}': token = JSONConstants.OBJECT_END; break;
            case ':': token = JSONConstants.COLON; break;
            case '"': token = string(store); break;
            case '[': array(); token = JSONConstants.VALUE; break;
            case '{': object(); token = JSONConstants.VALUE;  break;
            case 't':
                next(); next(); next(); // assumed r-u-e
                listener.direct(Boolean.TRUE);
                token = JSONConstants.VALUE;
                break;
            case'f':
                next(); next(); next(); next(); // assumed a-l-s-e
                listener.direct(Boolean.FALSE);
                token = JSONConstants.VALUE;
                break;
            case 'n':
                next(); next(); next(); // assumed u-l-l
                listener.direct(null);
                token = JSONConstants.VALUE;
                break;
            default:
                c = it.previous();
                if (Character.isDigit(c) || c == '-') {
                    number();
                }
                token = JSONConstants.VALUE;
        }
//        System.out.println("token: " + token); // enable this line to see the token stream
    }

    protected void number() {
        int length = 0;
        boolean isFloatingPoint = false;
        buf.setLength(0);
        
        if (c == '-') {
            add();
        }
        length += addDigits();
        if (c == '.') {
            add();
            length += addDigits();
            isFloatingPoint = true;
        }
        if (c == 'e' || c == 'E') {
            add();
            if (c == '+' || c == '-') {
                add();
            }
            addDigits();
            isFloatingPoint = true;
        }
 
        String s = buf.toString();
        Number value = isFloatingPoint 
            ? (length < 17) ? (Number)Double.valueOf(s) : new BigDecimal(s)
            : (length < 19) ? (Number)Long.valueOf(s) : new BigInteger(s);
        listener.number(value);
    }
    
    protected int addDigits() {
        int ret;
        for (ret = 0; Character.isDigit(c); ++ret) {
            add();
        }
        return ret;
    }

    protected void add(char cc) {
        buf.append(cc);
        next();
    }

    protected void add() {
        add(c);
    }

    protected String string(boolean store) {
        buf.setLength(0);
        while (c != '"') {
            if (c == '\\') {
                next();
                if (c == 'u') {
                    add(unicode());
                } else {
                    Object value = JSONConstants.escapes.get(Character.valueOf(c));
                    if (value != null) {
                        add(((Character) value).charValue());
                    }
                }
            } else {
                add();
            }
        }
        next();
        String value = buf.toString();
        if (store) listener.string(value);
        return value;
    }

    protected char unicode() {
        int value = 0;
        for (int i = 0; i < 4; ++i) {
            switch (next()) {
            case '0': case '1': case '2': case '3': case '4': 
            case '5': case '6': case '7': case '8': case '9':
                value = (value << 4) + c - '0';
                break;
            case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
                value = (value << 4) + (c - 'a') + 10;
                break;
            case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
                value = (value << 4) + (c - 'A') + 10;
                break;
            }
        }
        return (char) value;
    }

    protected void array() {
        listener.startArray();
        parse(true);
        while (token != JSONConstants.ARRAY_END) {
            parse(true);
            if (token == JSONConstants.COMMA) {
                parse(true);
            }
        }
        listener.finishArray();
    }
    
    protected void object() {
        listener.startObject();
        parse(false);
        while (token instanceof String) {
            listener.key((String) token);
            parse(true); // should be a colon
            if (token != JSONConstants.OBJECT_END) {
                parse(true);
                parse(true); // comma or end
                if (token == JSONConstants.COMMA) {
                    parse(false);
                }
            }
        }

        listener.finishObject();
    }
}
