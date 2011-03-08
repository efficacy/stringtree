package tests.spec;

import org.stringtree.finder.StringFinder;
import org.stringtree.util.spec.EnvironmentLoader;

import junit.framework.TestCase;

public class EnvironmentTest extends TestCase {
    
    public void testEnvironment() {
        StringFinder rep = EnvironmentLoader.loadEnvironment();
        // Diagnostics.dumpFetcher(rep.getUnderlyingFetcher(), "environment");
        String[] possibilties = new String[] { "JAVA_HOME", "USER", "USER_NAME", "USERPROFILE", "PATH" };
        boolean checked = false;
		for (String possibility : possibilties) {
            String env = System.getenv(possibility);
            if (env != null) { 
                String stored = rep.get(possibility);
				if (env.equals(stored)) {
                    checked = true;
                    break;
                }
            }
        }
        assertTrue(checked);
    }
}
