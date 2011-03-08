package org.stringtree.json;

public class StderrStreamErrorListener extends BufferErrorListener {
    
    public void end() {
        System.err.print(buffer.toString());
    }
}
