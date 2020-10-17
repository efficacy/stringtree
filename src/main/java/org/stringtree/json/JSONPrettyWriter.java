package org.stringtree.json;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

public class JSONPrettyWriter extends JSONWriter {
	private static String spaces = "                                                            ";
	private static int stepsize = 3;
	private static int max = spaces.length() / stepsize;
    private int level = 0;

    public JSONPrettyWriter(boolean emitClassName) {
    	super(emitClassName);
    }

    public JSONPrettyWriter() {
    	super(false);
    }

    private void indent() {
    	if (0 == level) return;
    	if (level < max) {
    		add(spaces.substring(0, level*stepsize));
    	} else {
	    	add(spaces);
	    	for (int i = level - max; i < level; ++i) {
	    		for (int j = 0; j < stepsize; ++j) {
	    			add(" ");
	    		}
	    	}
    	}
    }

    private void nl() {
    	add("\r\n");
    	indent();
    }

	private void stepIn() {
		++level;
    	nl();
	}

	private void stepOut() {
        --level;
		nl();
	}

    @Override protected void bean(Object object) {
        add("{");
        stepIn();

        BeanInfo info;
        boolean addedSomething = false;
        try {
            info = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (int i = 0; i < props.length; ++i) {
                PropertyDescriptor prop = props[i];
                String name = prop.getName();
                Method accessor = prop.getReadMethod();
                if ((emitClassName==true || !"class".equals(name)) && accessor != null) {
                    if (!accessor.canAccess(object)) accessor.setAccessible(true);
                    Object value = accessor.invoke(object, (Object[])null);
                    if (addedSomething) {
                    	add(',');
                    	nl();
                    }
                    add(name, value);
                    addedSomething = true;
                }
            }
            Field[] ff = object.getClass().getFields();
            for (int i = 0; i < ff.length; ++i) {
                Field field = ff[i];
                if (addedSomething) add(',');
                add(field.getName(), field.get(object));
                addedSomething = true;
            }
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        } catch (InvocationTargetException ite) {
            ite.getCause().printStackTrace();
            ite.printStackTrace();
        } catch (IntrospectionException ie) {
            ie.printStackTrace();
        }

        stepOut();
        add("}");
    }

    @Override protected void map(Map<String,Object> map) {
        add("{");
        stepIn();

        Iterator<Map.Entry<String,Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<?,?> e = it.next();
            value(e.getKey());
            add(":");
            value(e.getValue());
            if (it.hasNext()) {
            	add(',');
            	nl();
            }
        }

        stepOut();
        add("}");
    }

    @Override protected void array(Iterator<?> it) {
        add("[");
        stepIn();

        while (it.hasNext()) {
            value(it.next());
            if (it.hasNext()) {
            	add(",");
            	nl();
            }
        }

        stepOut();
        add("]");
    }

    @Override protected void array(Object object) {
        add("[");
        stepIn();

        int length = Array.getLength(object);
        for (int i = 0; i < length; ++i) {
            value(Array.get(object, i));
            if (i < length - 1) {
            	add(',');
            	nl();
            }
        }

        stepOut();
        add("]");
    }
}
