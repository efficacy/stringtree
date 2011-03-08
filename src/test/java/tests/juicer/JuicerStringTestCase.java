package tests.juicer;

import org.stringtree.juicer.Rewindable;
import org.stringtree.juicer.string.StringSource;

import junit.framework.TestCase;

public class JuicerStringTestCase extends TestCase {

    protected StringSource source;

    protected void rewind() {
        ((Rewindable)source).rewind();
    }

}
