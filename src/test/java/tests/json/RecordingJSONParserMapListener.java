package tests.json;

import java.util.Map;

import org.stringtree.json.events.JSONParserMapListener;
import org.stringtree.mock.RecordingMock;

public class RecordingJSONParserMapListener extends JSONParserMapListener {

    RecordingMock recorder = new RecordingMock("");

    public RecordingJSONParserMapListener(Map<String, Object> context) {
        super(context);
    }

    public void start(int start) {
        recorder.record("start", start);
    }
    
    public void finish() {
        recorder.record("finish");
    }

    public void number(Number value) {
        recorder.record("number", value);
        super.number(value);
    }

    public void string(String value) {
        recorder.record("string", value);
        super.string(value);
    }
    
    public void direct(Object value) {
        recorder.record("direct", value);
        super.direct(value);
    }

    public void startArray() {
        recorder.record("startArray");
        super.startArray();
    }
    
    public void finishArray() {
        recorder.record("finishArray");
        super.finishArray();
    }
}
