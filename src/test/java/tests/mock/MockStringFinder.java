package tests.mock;

import org.stringtree.finder.StringFinder;
import org.stringtree.template.StringCollector;

public class MockStringFinder extends MockObjectFinder implements StringFinder {
    
    public MockStringFinder(StringCollector out) {
        super(out, "");
    }

    public String get(String name) {
        log("get('" + name + "')");
        return (String) resultOfGetObject;
    }
}