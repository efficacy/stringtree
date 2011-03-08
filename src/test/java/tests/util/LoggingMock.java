package tests.util;

import org.stringtree.template.StringCollector;

public class LoggingMock {
    
    public StringCollector out;

    public LoggingMock(StringCollector out) {
        this.out = out;
    }

    protected void log(String string) {
        out.write(string);
    }
}
