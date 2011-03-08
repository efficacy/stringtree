package org.stringtree.fetcher;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.finder.FetcherStringKeeper;
import org.stringtree.finder.StringKeeper;
import org.stringtree.util.MethodCallUtils;
import org.stringtree.util.MethodWrapper;
import org.stringtree.util.StringUtils;
import org.stringtree.util.iterator.QCSVSpliterator;

public class BeanFetcher implements Fetcher {
    protected Object obj;
    protected Map<String, Method> methods;
    protected Map<String, Field> fields;
    protected Fetcher context;

    public BeanFetcher(Object obj, Fetcher context) {
        this.obj = obj;
        this.context = context;
        methods = new HashMap<String, Method>();
        fields = new HashMap<String, Field>();

        try {
            BeanInfo info = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] props = info.getPropertyDescriptors();

            for (int i = 0; i < props.length; ++i) {
                PropertyDescriptor prop = props[i];
                methods.put(prop.getName(), prop.getReadMethod());
            }
        } catch (IntrospectionException ie) {
            ie.printStackTrace();
        }

        Field[] ff = obj.getClass().getDeclaredFields();

        for (int i = 0; i < ff.length; ++i) {
            Field field = ff[i];
            fields.put(field.getName(), field);
        }
    }

    public BeanFetcher(Object obj) {
        this(obj, null);
    }

    private Object invoke(String key) {
        Object ret = null;
        StringKeeper stringKeeper = new FetcherStringKeeper(context);
        
        // keys ending with brackets bypass the property lookup and go direct to methods
        if (key.endsWith(")") && key.contains("(")) {
        	int argStart = key.indexOf("(");
			String method = key.substring(0, argStart);
			String args = key.substring(argStart+1, key.length()-1);
        	ret = invokeargs(method, args, stringKeeper);
        }

        // first try a "JavaBean" style property
        // (usually "getXXX()", but can be overridden by property descriptor)
        Method method = methods.get(key);
        if (method != null) {
            try {
                ret = method.invoke(obj, (Object[])null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // if that fails, try a regular method, with or without a context
        if (ret == null) {
            ret = MethodCallUtils.call(obj, key, stringKeeper, false);
        }
        
        // keep going, try a "get" method, with or without a context
        if (ret == null) {
            ret = MethodCallUtils.callOptionalContext(stringKeeper, obj, "get", key);
        }

        // if that fails, look for a public field
        if (ret == null) {
            Field field = fields.get(key);
            if (field != null) {
                try {
                    int modifiers = field.getModifiers();
                    if (Modifier.isPublic(modifiers)) {
                        ret = field.get(obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    private Object invokeargs(String method, String parameter, StringKeeper stringKeeper) {
    	Object ret = null;
		if (StringUtils.isBlank(parameter)) {
			ret = MethodCallUtils.call(obj, method, stringKeeper, false);
		} else {
			List<Object> args = null;
			Object value = context.getObject(parameter);
			if (null != value) {
				args = Collections.singletonList(value);
			} else {
				args = parseParameter(parameter, stringKeeper);
			}
			MethodWrapper wrapper = null;
			if (args.isEmpty()) {
				wrapper = MethodCallUtils.findMethod(obj, method);
			} else {
				wrapper = MethodCallUtils.findMethod(obj, method, args.toArray());
			}

			if (null != wrapper) {
				ret = MethodCallUtils.call(obj, wrapper);
			}
		}
		
		return ret;
	}

	private List<Object> parseParameter(String parameter, StringKeeper stringKeeper) {
		List<Object> ret = new ArrayList<Object>();
		QCSVSpliterator splitter = new QCSVSpliterator(parameter);
		splitter.eatQuotes(false);
		for (String arg : splitter) {
			if (arg.startsWith("'") && arg.endsWith("'")) {
				ret.add(arg.substring(1, arg.length()-1));
			} else {
				Object object = stringKeeper.getObject(arg);
				if (null != object) {
					ret.add(object);
				} else {
					System.err.println("Error! no value found for method parameter '" + arg + "' aborting call");
					return Collections.emptyList();
				}
			}
		}
		
		return ret;
	}

	public Object getObject(String key) {
        Object ret = null;

        if (Tract.CONTENT.equals(key)) {
            ret = obj;
        } else {
            ret = invoke(key);
        }

        return ret;
    }

    public boolean contains(String key) {
        return "this".equals(key) || methods.containsKey(key);
    }
}