package org.stringtree.workflow;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import org.stringtree.util.Utils;

public class Transition {

	private String destination;
	private SideEffect effect;

	public Transition(String destination, String effect, Object params) {
		this.destination = destination;
		Object obj = createObject(effect, System.out);
		if (obj instanceof SideEffect) {
			this.effect = (SideEffect)obj;
			this.effect.init(params);
		}
	}

	@Override public String toString() {
		return destination;
	}

	@Override public boolean equals(Object other) {
		 return Utils.same(destination, other.toString());
	}

    @Override
    public int hashCode() {
        return "$Transition$".hashCode() + destination.hashCode();
    }

	public boolean doSideEffect(String from, String code, Object context) {
		boolean ret = true;

		if (effect != null) {
			ret = effect.execute(from, code, destination, context);
		}

		return ret;
	}

	/**
	 Attempt to create an object of a named class, logging any errors if appropriate

	 @param className the full qualified name of the class
	 @param logStream the stream to log errors to, if they occur
	 @return the newly created object, or null if it couldn't be done
	*/
	public static Object createObject(String className, OutputStream log) {
		Object ret = null;
		PrintStream out = null;

		if (log != null) {
			if (log instanceof PrintStream) {
				out = (PrintStream)log;
			} else {
				out = new PrintStream(log);
			}
		}

		try {
			ret = Class.forName(className).getDeclaredConstructor().newInstance();
		} catch(ClassNotFoundException e) {
			if (out != null) {
				out.println("couldn't find class '" + className + "'");
			}
		} catch(IllegalAccessException e) {
			if (out != null) {
				out.println("couldn't access class '" + className + "'");
			}
		} catch(InstantiationException e) {
			if (out != null) {
				out.println("couldn't instantiate class '" + className + "'");
			}
		} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			if (out != null) {
				out.println("couldn't call default constructor on class '" + className + "'");
			}
		}

		return ret;
	}

	public static Object createObject(String className) {
		return createObject(className, null);
	}
}