package org.stringtree.xmlevents;

public interface XMLCharacterHandler {
    public void handle(History<String> history, char c, int line, int column);
}
