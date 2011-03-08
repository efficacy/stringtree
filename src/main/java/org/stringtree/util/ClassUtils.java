package org.stringtree.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;

public class ClassUtils {
    
    @SuppressWarnings("unchecked")
	protected static Class loadClass(String className, ClassLoader loader)
            throws ClassNotFoundException {
        return Class.forName(className, true, loader);
    }

    public static Object rawCreate(String className, ClassLoader loader)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        return loadClass(className, loader).newInstance();
    }

    protected static void logCreationMessage(String message, Exception e,
            Writer out) {
        if (message != null && out != null) {
            PrintWriter pout = WriterUtils.ensurePrint(out);
            pout.println(message);
            e.printStackTrace(pout);
        }
    }

    protected static void logCreationMessage(String message, Exception e,
            PrintStream out) {
        if (message != null && out != null) {
            out.println(message);
            e.printStackTrace(out);
        }
    }

    protected static void logCreationException(String className, Exception e,
            Writer out) {
        logCreationMessage(creationMessage(className, e), e, out);
    }

    protected static void logCreationException(String className, Exception e,
            PrintStream out) {
        logCreationMessage(creationMessage(className, e), e, out);
    }

    private static String creationMessage(String className, Exception e) {
        String message;
        if (e instanceof ClassNotFoundException) {
            message = "couldn't find class '" + className + "'";
        } else if (e instanceof IllegalAccessException) {
            message = "couldn't access class '" + className + "'";
        } else if (e instanceof InstantiationException) {
            message = "couldn't instantiate class '" + className + "'";
        } else {
            message = "Unrecognized exception during object creation";
        }
        return message;
    }

    public static Object createObject(String className, ClassLoader loader) {
        Object ret = null;

        try {
            ret = rawCreate(className, loader);
        } catch (Exception e) {
            logCreationException(className, e, System.err);
        }

        return ret;
    }

    public static Object createObject(String className) {
        return createObject(className, ClassUtils.class.getClassLoader());
    }
}
