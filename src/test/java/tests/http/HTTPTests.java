package tests.http;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTTPTests extends TestCase {

    public static TestSuite suite() {
		TestSuite ret = new TestSuite();

		ret.addTest(new TestSuite(HTTPClientTest.class));

		return ret;
	}
}
