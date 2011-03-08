package org.stringtree.util.testing;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import junit.framework.TestCase;

public class SystemStreamTestCase extends TestCase {
    private PrintStream realOut;
    protected ByteArrayOutputStream output;
    private PrintStream realErr;
    protected ByteArrayOutputStream error;
    
    public void claimOutput() {
        realOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }
    
    public void releaseOutput() {
        System.setOut(realOut);
    }
    
    public void claimError() {
        realErr = System.err;
        error = new ByteArrayOutputStream();
        System.setErr(new PrintStream(error));
    }
    
    public void releaseError() {
        System.setErr(realErr);
    }

}

