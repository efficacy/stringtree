package org.stringtree.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.stringtree.Fetcher;
import org.stringtree.SystemContext;

@SuppressWarnings("unchecked")
public class ParameterClassUtils {
    
	private static final Class[] SINGLE_STRING_CLASS_ARRAY = new Class[] { String.class };
    private static final Object[] SINGLE_STRING_OBJECT_ARRAY = new Object[] { "" };

    public static Object rawCreateWithParameters(Class c, Object[] args, Class[] types) throws Exception {
        Object ret = null;

        if (types == null) {
            types = new Class[args.length];
            for (int i = 0; i < args.length; ++i) {
                types[i] = args[i].getClass();
            }
        }

        try {
            Constructor cx = c.getConstructor(types);
            ret = cx.newInstance(args);
        } catch (NoSuchMethodException nsme) {
            // No constructor taking parameters, so try the no-arg one instead
            ret = ClassUtils.rawCreate(c);
        }

        return ret;
    }

    public static Object rawCreateWithParameters(String className,
            Object[] args, Class[] types, ClassLoader loader) throws Exception {
        Class c = ClassUtils.loadClass(className, loader);
        return rawCreateWithParameters(c, args, types);
    }

    public static Object rawCreateWithDefaultParameters(String className, ClassLoader loader) throws Exception {
        Object ret = null;
        try {
            ret = ClassUtils.rawCreate(className, loader);
        } catch (Exception e) {
            ret = rawCreateWithParameters(className, SINGLE_STRING_OBJECT_ARRAY, SINGLE_STRING_CLASS_ARRAY, loader);
        }

        return ret;
    }
    
    protected static Object create(String className, Object[] params, Class[] types, ClassLoader loader) {
        Object ret = null;

        try {
            if (params != null) {
                ret = rawCreateWithParameters(className, params, types, loader);
            } else {
                ret = rawCreateWithDefaultParameters(className, loader);
            }
        } catch (NoSuchMethodException nsme) {
            ClassUtils.logCreationMessage("no such constructor " + className
                    + dumpClassArray(types), nsme, System.err);
        } catch (InvocationTargetException ite) {
            ClassUtils.logCreationMessage("couldn't invoke constructor "
                    + className + dumpClassArray(types), ite, System.err);
        } catch (SecurityException se) {
            ClassUtils.logCreationMessage("not allowed to invoke constructor "
                    + className + "(StringFactory)", se, System.err);
        } catch (Exception e) {
            ClassUtils.logCreationException(className, e, System.err);
        }

        return ret;
    }

    private static String dumpClassArray(Class[] types) {
        if (null == types || 0 == types.length) return "[]";
        
    	StringBuffer ret = new StringBuffer("[");
    	for (int i = 0; i < types.length; ++i) {
    		if (i > 0) ret.append(",");
    		ret.append(types[i].toString());
    	}
    	ret.append("]");
    	return ret.toString();
    }

    public static Object createObject(String className, Object param,
            ClassLoader loader) {
        return create(className, new Object[] { param }, null, loader);
    }

    public static Object createObject(String className, Object param) {
        return create(className, new Object[] { param }, null,
                ParameterClassUtils.class.getClassLoader());
    }

    public static Object createObject(String className, String param,
            ClassLoader loader) {
        return create(className, new Object[] { param },
                SINGLE_STRING_CLASS_ARRAY, loader);
    }

    public static Object createObject(String className, String param) {
        return create(className, new Object[] { param },
                SINGLE_STRING_CLASS_ARRAY, ParameterClassUtils.class
                        .getClassLoader());
    }

    public static Object createObject(String def, ClassLoader loader) {
        Object ret = null;
        int gap = def.indexOf(' ');
        if (gap != -1) {
            String className = def.substring(0, gap);
            String args = def.substring(gap + 1).trim();
            ret = createObject(className, args, loader);
        } else {
            ret = create(def, null, null, loader);
        }

        return ret;
    }

    public Object createObject(String def) {
        return createObject(def, ParameterClassUtils.class.getClassLoader());
    }
    
    public static Object createObjectWithClassloader(String def, Fetcher context, Class clazz) {
        return createObject(def, getClassLoader(context, clazz));
    }
    
    public static ClassLoader getClassLoader(Fetcher context, Class clazz) {
        ClassLoader loader = null;
        
        if (null != context) {
            Object cl = context.getObject(SystemContext.SYSTEM_CLASSLOADER);
            if (cl instanceof ClassLoader)
                loader = (ClassLoader) cl;
        }
        if (loader == null)
            loader = clazz.getClassLoader();
        return loader;
    }
}
