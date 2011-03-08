package org.stringtree.util;

public class EndOfTheLine {
    private char[] eol;
    private int n = 0;

    protected EndOfTheLine(String eol) {
        this.eol = eol.toCharArray();
    }
    
    public static EndOfTheLine CR() {
        return new EndOfTheLine("\r");
    }
    
    public static EndOfTheLine LF() {
        return new EndOfTheLine("\n");
    }
    
    public static EndOfTheLine CRLF() {
        return new EndOfTheLine("\r\n");
    }
    
    public boolean match(final int c) {
        if (c == -1 || (c == eol[n]) && n == eol.length-1) {
            reset();
            return true;
        }
        
        if (c == eol[n]) {
            ++n;
            return false;
        }
        
        reset();
        return false;
    }

    private void reset() {
        n = 0;
    }
}
