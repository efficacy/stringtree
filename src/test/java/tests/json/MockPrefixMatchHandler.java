package tests.json;

import java.util.Map;

import org.stringtree.json.events.PrefixMatchHandler;

import tests.AssertingMock;

public class MockPrefixMatchHandler extends AssertingMock implements PrefixMatchHandler {

    public void handle(String prefix, Map<String, Object> context) {
        record("handle", new Object[] {prefix, context});
//System.err.println("handle prefix=" + prefix + " context=" + context);
    }

}
