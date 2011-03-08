package tests.json;

import java.util.Map;

import org.stringtree.json.events.PrefixMatchHandler;
import org.stringtree.mock.RecordingMock;

public class MockPrefixMatchHandler extends RecordingMock implements PrefixMatchHandler {

    public void handle(String prefix, Map<String, Object> context) {
        record("handle", new Object[] {prefix, context});
//System.err.println("handle prefix=" + prefix + " context=" + context);
    }

}
