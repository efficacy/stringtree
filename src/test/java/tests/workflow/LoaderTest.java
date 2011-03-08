package tests.workflow;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.stringtree.workflow.ClassicMachineLoader;
import org.stringtree.workflow.HierarchyMachineLoader;
import org.stringtree.workflow.StateMachine;
import org.stringtree.workflow.StateMachineFactory;
import org.stringtree.workflow.StateMachineSpec;

import junit.framework.TestCase;

@SuppressWarnings("unchecked")
public class LoaderTest extends TestCase {
    
	StateMachineSpec spec;
	StateMachine machine;
	Object context;
	File files;
	boolean verbose;

	public void setUp() {
		spec = new StateMachineSpec();
		context = new HashMap();
		files = new File("testfiles/workflow");
		verbose = false;
	}

	private void doTest(StateMachine machine) {
		assertEquals("2", machine.getState());
		assertEquals("2", machine.next("BORED"));
		assertEquals("2", machine.next("STAY"));
		assertEquals("1", machine.next("SAD"));
		assertEquals("3", machine.next("HAPPY"));
		assertEquals("_EXIT", machine.next("COMPLETE"));
		assertEquals(null, machine.lookForward("WHAT"));
		assertEquals(null, machine.next("WHAT"));

		machine.reset();
		assertEquals(null, machine.lookBack());
		assertEquals("2", machine.next("BORED"));
		assertEquals(null, machine.lookBack());
		assertEquals("1", machine.next("SAD"));
		assertEquals("2", machine.lookBack());
		assertEquals("3", machine.next("HAPPY"));
		assertEquals("1", machine.lookBack());

		assertEquals("1", machine.next("BACK"));
		assertEquals("1", machine.getState());
		assertEquals("2", machine.next("BACK"));
		assertEquals("2", machine.getState());
		assertEquals(null, machine.next("BACK"));
		assertEquals("2", machine.getState());

		machine.reset();
		assertEquals("1", machine.next("SAD"));
		assertEquals("1", machine.getState());
		assertEquals("2", machine.next("RESET"));
		assertEquals("2", machine.getState());
	}

	public void testExplicit() {
		spec.setInitialState("2");

		spec.addDestination("1", "HAPPY", "3");
		spec.addDestination("1", "BACK", "_BACK");
		spec.addDestination("1", "RESET", "_RESET");
		spec.addDestination("1", "STAY", "_SELF");
		spec.addDestination("2", "SAD", "1");
		spec.addDestination("2", "BORED", "2");
		spec.addDestination("2", "BACK", "_BACK");
		spec.addDestination("2", "STAY", "_SELF");
		spec.addDestination("3", "COMPLETE", "_EXIT");
		spec.addDestination("3", "BACK", "_BACK");
		spec.addDestination("3", "PLOP", "UGH");
		spec.addDestination("3", "STAY", "_SELF");

		machine = new StateMachine(spec, context);
		doTest(machine);
	}

	public void testLoader() throws IOException {
		ClassicMachineLoader loader = new ClassicMachineLoader();

		loader.load(spec, new FileReader(new File(files, "classic.txt")));
		machine = new StateMachine(spec, context);
		doTest(machine);
	}

	public void testClassic() throws IOException {
		if (verbose) System.out.println("LoaderTest.testClassic");
		StateMachineFactory factory = new StateMachineFactory(new ClassicMachineLoader());
		factory.load(new FileReader(new File(files, "classic.txt")));
		doTest(factory.create(context));
		assertEquals(null,((Map)context).get("message"));
	}

	public void testInheriting() throws IOException {
		if (verbose) System.out.println("LoaderTest.testInheriting");
		StateMachineFactory factory = new StateMachineFactory(new ClassicMachineLoader());
		factory.load(new FileReader(new File(files, "inherit.txt")));
		doTest(factory.create(context));
	}

	public void testSideEffects() throws IOException {
		if (verbose) System.out.println("LoaderTest.testSideEffects");
		StateMachineFactory factory = new StateMachineFactory(new ClassicMachineLoader());
		factory.load(new FileReader(new File(files, "effects.txt")));
		StateMachine machine = factory.create(context);

		doTest(machine);
		assertEquals("executing tx from '1' via 'HAPPY' to '3' (hello world)",((Map)context).get("message"));

		assertEquals("2", machine.getState());

		assertEquals("1", machine.lookForward("BACKDOOR"));
		assertEquals(null, machine.next("BACKDOOR"));
		assertEquals("2", machine.getState());
		assertEquals("Warning, no state change",((Map)context).get("message"));

		assertEquals("1", machine.lookForward("SAD"));
		assertEquals("1", machine.next("SAD"));
		assertEquals("1", machine.getState());

		machine.setContext(machine);
		assertEquals("3", machine.lookForward("BOMB"));
		assertEquals(null, machine.next("BOMB"));
		assertEquals("2", machine.getState());

		assertEquals("3", machine.lookForward("BOUNCE"));
		assertEquals(null, machine.next("BOUNCE"));
		assertEquals("1", machine.getState());

		assertEquals("3", machine.lookForward("HAPPY"));
		assertEquals("3", machine.next("HAPPY"));
		assertEquals("3", machine.getState());

		assertEquals("3", machine.lookForward("HAPPY"));
		assertEquals(null, machine.next("HAPPY"));
		assertEquals("2", machine.getState());

		assertEquals("3", machine.lookForward("HAPPY"));
		assertEquals(null, machine.next("HAPPY"));
		assertEquals("2", machine.getState());
	}

	public void testHierarchy() throws IOException {
		if (verbose) System.out.println("LoaderTest.testHierarchy");
		StateMachineFactory factory = new StateMachineFactory(new HierarchyMachineLoader());
		factory.load(new FileReader(new File(files, "hier.txt")));
		StateMachine machine = factory.create(context);

		assertEquals("", machine.getState());
		assertEquals(".image", machine.next("image"));
		assertEquals(".image", machine.getState());
		assertEquals(".image.margins", machine.next("margins"));
		assertEquals("executing tx from '.image' via 'margins' to '.image.margins' (hello world)",((Map)context).get("message"));
		assertEquals(".image.margins", machine.getState());
		assertEquals(".image.margins.left", machine.next("left"));
		assertEquals(".image.margins.left", machine.getState());
		assertEquals(".image.margins", machine.next("_BACK"));
		assertEquals("executing tx from '.image.margins.left' via '_BACK' to '.image.margins' (hello world)",((Map)context).get("message"));
		assertEquals(".image.margins", machine.getState());
		assertEquals(".image", machine.next("_BACK"));
		assertEquals(".image", machine.getState());
		assertEquals(".image.content", machine.next("content"));
		assertEquals(".image.content", machine.getState());
		assertEquals(".image.content.text", machine.next("text"));
		assertEquals(".image.content.text", machine.getState());
		assertEquals(".image.content", machine.next("_BACK"));
		assertEquals(".image.content", machine.getState());
		assertEquals(".image", machine.next("_BACK"));
		assertEquals(".image", machine.getState());
		assertEquals(null, machine.next("plop"));
		assertEquals(".image", machine.getState());


		machine.reset();
		assertEquals("", machine.getState());
	}
}
