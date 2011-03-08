package tests.workflow;

import junit.framework.TestCase;

import org.stringtree.util.testing.Checklist;
import org.stringtree.workflow.StateMachineSpec;

public class SpecTest extends TestCase {
    
	private StateMachineSpec spec;
	private Checklist<String> c;

	public void setUp() {
		spec = new StateMachineSpec();
	}

	public void testEmpty() {
		assertEquals(null, spec.getDestination("2", "SAD"));
	}

	public void testSingle() {
		spec.addDestination("2", "SAD", "1");
		assertEquals("1", spec.getDestination("2", "SAD"));
		assertEquals(null, spec.getDestination("2", "HAPPY"));
	}

	public void testMultiple() {
		spec.addDestination("1", "HAPPY", "3");
		spec.addDestination("2", "SAD", "1");
		spec.addDestination("2", "BORED", "2");
		spec.addDestination("3", "COMPLETE", "_EXIT");

		assertEquals("2", spec.getDestination("2", "BORED"));
		assertEquals("1", spec.getDestination("2", "SAD"));
		assertEquals("3", spec.getDestination("1", "HAPPY"));
		assertEquals("_EXIT", spec.getDestination("3", "COMPLETE"));
	}

	public void testClone() {
		spec.addDestination("1", "HAPPY", "3");
		spec.addDestination("2", "SAD", "1");
		spec.addDestination("2", "BORED", "2");
		spec.addDestination("3", "COMPLETE", "_EXIT");

		spec.cloneState("2", "4");
		assertEquals("2", spec.getDestination("4", "BORED"));
		assertEquals("1", spec.getDestination("4", "SAD"));
	}

	public void testGetExits() {
		spec.addDestination("1", "HAPPY", "3");
		spec.addDestination("2", "SAD", "1");
		spec.addDestination("2", "BORED", "2");
		spec.addDestination("3", "COMPLETE", "_EXIT");

		c = new Checklist<String>( "SAD", "BORED" );
		c.consider(spec.getExits("2", false));
		assertTrue(c.allAndOnlyOnce());

		c = new Checklist<String>( "SAD", "BORED", "_BACK", "_RESET");
		c.consider(spec.getExits("2", true));
		assertTrue(c.allAndOnlyOnce());
	}
}
