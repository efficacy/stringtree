package tests.workflow;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WorkflowTests extends TestCase {
    
	public WorkflowTests(String name) {
		super(name);
	}

	public static TestSuite suite() {
		TestSuite ret = new TestSuite();

		ret.addTest(new TestSuite(SpecTest.class));
		ret.addTest(new TestSuite(LoaderTest.class));

		return ret;
	}
}
