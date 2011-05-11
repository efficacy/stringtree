package org.stringtree.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.StorerHelper;
import org.stringtree.finder.StringFinder;
import org.stringtree.finder.StringFinderHelper;
import org.stringtree.finder.StringKeeper;

class ObjectMethodWrapper implements MethodWrapper {
    
    public Method method;
    public Object[] args;

    public ObjectMethodWrapper(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public Object invoke(Object object) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        return method.invoke(object, args);
    }

    public String toString() {
        return method.getName() + "("
                + ((args != null && args.length > 0) ? ("" + args[0]) : "")
                + ")";
    }

    public boolean isCallable() {
        return true;
    }
    
    public Method getMethod() {
        return method;
    }

    public Object getArgument(int arg) {
        return args[arg];
    }
}

class MissingMethodWrapper implements MethodWrapper {
    protected Object dfl;

    public MissingMethodWrapper(Object dfl) {
        this.dfl = dfl;
    }

    public Object invoke(Object object) {
        return dfl;
    }

    public String toString() {
        return "missing method";
    }

    public boolean isCallable() {
        return false;
    }
    
    public Method getMethod() {
        return null;
    }

    public Object getArgument(int arg) {
        return null;
    }
}

abstract class MethodFinder {
    protected Object selector;
    protected Object[] args;
    protected Object dfl;

    public MethodFinder(Object selector, Object[] args, Object dfl) {
        this.selector = selector;
        this.args = args;
        this.dfl = dfl;
    }

    public MethodFinder(Object selector, Object[] args) {
        this(selector, args, null);
    }

    public MethodFinder(Object selector) {
        this(selector, null);
    }

    public abstract MethodWrapper find(Object object);
}

@SuppressWarnings("rawtypes")
public class MethodCallUtils {
    public static final String MATCHED_METHOD = "MethodCallUtils.matched.method";
    public static final String TARGET_EXCEPTION = "stringtree.target.exception";

	protected static final Class keeperargs[] = new Class[] { StringKeeper.class };
    protected static final Class finderargs[] = new Class[] { StringFinder.class };
    protected static final Class noargs[] = new Class[] {};
    protected static final MethodWrapper missing = new MissingMethodWrapper(null);

    public static MethodWrapper findObjectMethod(Object object, String name, Class[] types, Object[] args) {
        if (object != null) {
        	Class cls = null;
        	if (object instanceof Class) {
        		cls = (Class)object;
        	} else {
        		cls = object.getClass();
        	}
            Method[] methods = cls.getMethods();
            // check for exact types
            for (int i = 0; i < methods.length; ++i) {
                Method method = methods[i];
                if (name.equals(method.getName())
                        && Arrays.equals(types, method.getParameterTypes())) {
                    return new ObjectMethodWrapper(method, args);
                }
            }
            // check for inherited types
            for (int i = 0; i < methods.length; ++i) {
                Method method = methods[i];
                if (methodMatches(name, types, method)) {
                    return new ObjectMethodWrapper(method, args);
                }
            }
        }
        return null;
    }
    
    private static boolean methodMatches(String name, Class[] types, Method method) {
        if (!name.equals(method.getName()) || types.length != method.getParameterTypes().length)
            return false;
        Class<?> [] methodTypes = method.getParameterTypes();
        for (int i = 0; i < methodTypes.length; i++) {
            if (!methodTypes[i].isAssignableFrom(types[i]))
                return false;
        }
        
        return true;
    }

    public static MethodWrapper findMethod(Object object, String name) {
        return findObjectMethod(object, name, noargs, null);
    }

    public static MethodWrapper findMethod(Object object, String name, Object arg) {
        return findObjectMethod(object, name, new Class[] { arg.getClass() }, new Object[] { arg });
    }

    public static MethodWrapper findMethod(Object object, String name, Object[] args) {
        Class[] types = noargs;
        if (null != args && args.length > 0) {
            types = new Class[args.length];
            for (int i = 0; i < args.length; ++i) {
                types[i] = args[i].getClass();
            }
        }
        return findObjectMethod(object, name, types, args);
    }

    public static Object call(Object object, MethodWrapper method, boolean mandatory, String prefix) {
        Object ret = null;
        String failmessage = null;

        try {
            if (method != null) {
                ret = method.invoke(object);
            } else if (mandatory && null != object) {
                failmessage = "mandatory method not present on object " + object;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            failmessage = e.getMessage();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            failmessage = e.getMessage();
        } catch (InvocationTargetException e) {
            Throwable actual = e.getCause();
            actual.printStackTrace();
            recordException(method.getArgument(0), actual);
        }

        if (failmessage != null) {
            throw new UnsupportedOperationException(prefix + failmessage);
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
	private static void recordException(Object location, Throwable actual) {
        if (location instanceof Fetcher) {
            Fetcher context = (Fetcher)location;
            List<Throwable> exceptions = (List<Throwable>) context.getObject(TARGET_EXCEPTION);
            if (null == exceptions) {
                exceptions = new ArrayList<Throwable>();
                StorerHelper.put(context, TARGET_EXCEPTION, exceptions);
            }
            
            exceptions.add(actual);
        }
    }

    public static Object call(Object object, MethodWrapper method) {
        return call(object, method, false, "");
    }

    public static Object call(Object object, boolean mandatory, String prefix, MethodFinder finder) {
        MethodWrapper method = finder.find(object);
        Object ret = call(object, method, mandatory, prefix);
        return ret;
    }

    public static Object call(Object object, String name, boolean mandatory) {
        return call(object, mandatory, name + "()", new MethodFinder(name) {
            public MethodWrapper find(Object object) {
                return findObjectMethod(object, (String) selector, noargs, null);
            }
        });
    }

    public static Object call(Object object, String name, final Object arg) {
        return call(object, true, name + "()", new MethodFinder(name) {
            public MethodWrapper find(Object object) {
                return findMethod(object, (String) selector, arg );
            }
        });
    }

    public static Object call(Object object, String name, StringFinder context, boolean mandatory) {
        StringKeeper keeper = StringFinderHelper.ensureKeeper(context);
        return call(object, mandatory, name + "(StringFinder context)",
                new MethodFinder(name, new Object[] { keeper }) {
                    public MethodWrapper find(Object object) {
                        String name = (String) selector;
                        MethodWrapper ret = findObjectMethod(object, name, keeperargs, args);
                        if (ret == null) ret = findObjectMethod(object, name, finderargs, args);
                        if (ret == null) ret = findObjectMethod(object, name, noargs, null);
                        return ret;
                    }
                });
    }

    public static Object call(Object object, String[] names, StringFinder context, boolean mandatory, Object dfl) {
        final StringKeeper keeper = StringFinderHelper.ensureKeeper(context);
        Object ret = call(object, mandatory, "", new MethodFinder(names, new Object[] { keeper }, dfl) {
            public MethodWrapper find(Object object) {
                String[] names = (String[]) selector;
                for (int i = 0; i < names.length; ++i) {
                    String name = names[i];
                    MethodWrapper ret = findObjectMethod(object, name, keeperargs, args);
                    if (ret == null)
                        ret = findObjectMethod(object, name, finderargs, args);
                    if (ret == null)
                        ret = findObjectMethod(object, name, noargs, null);
                    if (ret != null)
                        return ret;
                }
                return new MissingMethodWrapper(dfl);
            }
        });
        return ret;
    }

    public static Object call(Object object, String name) {
        return call(object, name, false);
    }

    public static Object call(Object object, String name, StringFinder context) {
        return call(object, name, context, false);
    }

    public static Object callOptionalContext(Object object, String name, StringFinder context) {
        return call(object, name, context, false);
    }

    public static Object call(Object object, String[] names, StringFinder context, Object dfl) {
        Object ret = call(object, names, context, false, dfl);
        return ret;
    }

    public static Object call(Object object, String[] names, StringFinder context) {
        Object ret = call(object, names, context, false, null);
        return ret;
    }

    public static Object callOptionalContext(StringFinder context, Object destination, String methodName, Object param) {
//System.err.println("methodCallUtils: methodName=" + methodName + " arg=" + param + " of " + param.getClass());
        MethodWrapper method = findMethod(destination, methodName, param );
//System.err.println(" after raw solo, method=" + method);
        if (method == null && context instanceof StringKeeper) {
            method = findObjectMethod(destination, methodName, 
                    new Class[] { StringKeeper.class, param.getClass() }, new Object[] { context, param });
        }
//System.err.println(" after raw keeper, method=" + method);
        if (method == null) {
            method = findObjectMethod(destination, methodName, 
                    new Class[] { StringFinder.class, param.getClass() }, new Object[] { context, param });
        }
//System.err.println(" after raw finder, method=" + method);
            
        if (method == null && param.getClass() != Object.class) {
            method = findObjectMethod(destination, methodName, 
                    new Class[] { Object.class }, new Object[] { param });
//System.err.println(" after object solo, method=" + method);
            if (method == null && context instanceof StringKeeper) {
                method = findObjectMethod(destination, methodName, 
                        new Class[] { StringKeeper.class, Object.class }, new Object[] { context, param });
            }
//System.err.println(" after object keeper, method=" + method);
            if (method == null) {
                method = findObjectMethod(destination, methodName, 
                        new Class[] { StringFinder.class, Object.class }, new Object[] { context, param });
            }
//System.err.println(" after object finder, method=" + method);
        }
            
        if (method == null && param.getClass() != String.class) {
            String stringParam = param.toString();
            method = findObjectMethod(destination, methodName, 
                    new Class[] { String.class }, new Object[] { stringParam });
//System.err.println(" after string solo, method=" + method);
            if (method == null && context instanceof StringKeeper) {
                method = findObjectMethod(destination, methodName, 
                        new Class[] { StringKeeper.class, String.class }, new Object[] { context, stringParam });
            }
//System.err.println(" after string keeper, method=" + method);
            if (method == null) {
                method = findObjectMethod(destination, methodName, 
                        new Class[] { StringFinder.class, String.class }, new Object[] { context, stringParam });
            }
//System.err.println(" after string finder, method=" + method);
        }

        Object ret = null;
        if (method != null && destination != null) {
            try {
                ret = method.invoke(destination);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                Throwable actual = e.getCause();
                recordException(context, actual);
                actual.printStackTrace();
            }
        }

        return ret;
    }
}
