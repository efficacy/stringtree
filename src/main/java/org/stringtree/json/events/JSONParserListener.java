package org.stringtree.json.events;

public interface JSONParserListener {

    void start(int start);
    void finish();

    void number(Number value);
    void string(String value);
    void direct(Object value);
    void startArray();
    void finishArray();
    void startObject();
    void finishObject();
    void key(String token);
}
