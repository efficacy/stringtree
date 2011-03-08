package org.stringtree.util.iterator;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Map;

public class Spliterator extends AbstractIterator<String> implements StringIterator {

    public static final char TT_ORDINARY = 'O';
    public static final char TT_SEP = 'S';
    public static final char TT_PAD = 'P';
    public static final char TT_QUOTE = 'Q';
    public static final char TT_END = 'E';
    public static final char TT_ESCAPE = 'X';
    public static final char TT_NONE = 'N';

    protected Map<Character, Character> types;
    protected CharacterIterator cit;
    protected boolean eatseps = true;
    protected boolean eatquotes = true;
    protected boolean joinseps = false;

    protected char c;
    protected char ttype;
    protected char inquote = 0;
    protected char hadsep = 0;
    protected String lookahead = null;
    protected StringBuffer token;

    public Spliterator(String string, String seps) {
        types = new HashMap<Character, Character>();
        token = new StringBuffer();
        setSeparators(seps);
        reset(string);
    }

    public Spliterator(String string) {
        this(string, " ");
    }

    public Spliterator() {
        this("");
    }

    public void start() {
        c = cit.first();
        ttype = type(c);
    }

    public void reset(String string) {
        cit = new StringCharacterIterator(string);
        inquote = 0;
        hadsep = 0;
        lookahead = null;
        token.setLength(0);
        ttype = TT_NONE;
    }

    public boolean hasNext() {
        if (null == lookahead) {
            lookahead = nextObject();
        }
        return null != lookahead || ttype != TT_END;
    }
    
    public String next() {
        if (!hasNext()) return null;
        return nextObject();
    }
    
    private String nextObject() {
        if (TT_NONE == ttype) start();
        
        String ret = null;
        
        if (null != lookahead) {
            ret = lookahead;
            lookahead = null;
            return ret;
        }

        if (TT_END == ttype) {
            if (hadsep > 0 && !joinseps) {
                ret = "";
                hadsep = 0;
            }
            return ret;
        }

        boolean gathering = true;

        if (hadsep > 0) {
            if (!eatseps) {
                ret = Character.toString(hadsep);
            }
            hadsep = 0;
        } else {
            do {
                switch(ttype) {
                case TT_PAD:
                    break;
                case TT_QUOTE:
                    if (0 == inquote) {
                        inquote = c;
                    } else if (c == inquote) {
                        inquote = 0;
                    }
                    if (!eatquotes) {
                        token.append(c);
                    }
                    break;
                case TT_ORDINARY:
                    token.append(c);
                    break;
                case TT_SEP:
                    if (joinseps && token.length() == 0) break;
                    
                    hadsep = c;
                    gathering = false;
                    break;
                case TT_END:
                    gathering = false;
                    break;
                }
                advance();
            } while (gathering);
            
            ret = token.toString();
            token.setLength(0);
        }
        return ret;
    }

    public String tail(boolean skipSeparator) {
        if (ttype == TT_END)
            return "";
        
        StringBuffer ret = new StringBuffer();
        
        if (null != lookahead) {
            ret.append(lookahead);
            lookahead = null;
        }

        if (!skipSeparator && hadsep > 0) {
            ret.append(hadsep);
            hadsep = 0;
        }

        while (c != CharacterIterator.DONE) {
            ret.append(c);
            c = cit.next();
        }
        ttype = TT_END;

        return ret.toString();
    }

    public String tail() {
        return tail(false);
    }

    protected char type(char c) {
        if (c == CharacterIterator.DONE)
            return TT_END;
        if (inquote > 0 && c != inquote)
            return TT_ORDINARY;

        Character cc = Character.valueOf(c);

        return types.containsKey(cc)
            ? types.get(cc).charValue()
            : TT_ORDINARY;
    }

    protected void advance() {
        c = cit.next();
        ttype = type(c);
        if (ttype == TT_ESCAPE) {
            char ec = cit.next();
            c = convertEscape(ec);
            ttype = TT_ORDINARY;
        }
    }

    public void setType(char c, char type) {
        types.put(Character.valueOf(c), Character.valueOf(type));
    }

    public void setType(char from, char to, char type) {
        for (char c = from; c <= to; ++c) {
            setType(c, type);
        }
    }

    public void setType(String seps, char type) {
        CharacterIterator it = new StringCharacterIterator(seps);
        for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
            setType(c, type);
        }
    }

    public void setSeparators(String chars, boolean eat, boolean clear) {
        if (clear) clear();
        setType(chars, TT_SEP);
        eatSeparators(eat);
    }

    public void setSeparators(String chars) {
        setSeparators(chars, true, true);
    }

    public void addSeparators(String chars) {
        setSeparators(chars, true, false);
    }

    public void setPadding(String chars) {
        setType(chars, TT_PAD);
    }

    public void setQuotes(String chars, boolean eat) {
        setType(chars, TT_QUOTE);
        eatQuotes(eat);
    }

    public void setQuotes(String chars) {
        setQuotes(chars, true);
    }

    public void clear() {
        types.clear();
    }

    public void eatSeparators(boolean b) {
        this.eatseps = b;
    }

    public void eatQuotes(boolean b) {
        this.eatquotes = b;
    }

    protected char convertEscape(char c) {
        switch (c) {
        case 't':
            return '\t';
        case 'n':
            return '\n';
        case 'r':
            return '\r';
        }
        return c;
    }

    protected boolean isquote(char inquote) {
        return ttype == TT_QUOTE && c == inquote;
    }
    
    public String nextString() {
        return next();
    }

    protected void joinSeparators(boolean b) {
        joinseps = b;
    }
}
