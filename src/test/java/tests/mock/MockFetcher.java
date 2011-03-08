package tests.mock;

import org.stringtree.Fetcher;
import org.stringtree.template.StringCollector;

import tests.util.LoggingMock;

public class MockFetcher extends LoggingMock implements Fetcher {
    
    public Object resultOfGetObject = null;

    public MockFetcher(StringCollector out) {
        super(out);
    }

    public Object getObject(String name) {
        out.write("getObject('" + name + "')");
        return resultOfGetObject;
    }
}