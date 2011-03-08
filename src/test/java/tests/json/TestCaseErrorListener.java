package tests.json;

import org.stringtree.json.JSONErrorListener;

public class TestCaseErrorListener implements JSONErrorListener {

    public boolean valid;
    public int column;
    public String message;

    public void start(String text) {
        valid = true;
        column = -1;
        message = "";
    }

    public void error(String message, int column) {
        if (valid) {
            this.column = column;
            this.message = message;
            valid = false;
        }
    }

    public void end() {
    }
}
