package tests.jndi;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JNDITests extends TestCase {

    public static TestSuite suite() {
		TestSuite ret = new TestSuite();

		ret.addTest(new TestSuite(ContextTest.class));

		return ret;
	}
}
