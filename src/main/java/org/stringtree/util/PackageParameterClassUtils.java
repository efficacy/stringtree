package org.stringtree.util;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class PackageParameterClassUtils {
    
	public static Object create(String className, Object[] params,
            Class[] types, List packages, ClassLoader loader) {
        if (packages == null || packages.isEmpty()) {
            return ParameterClassUtils.create(className, params, types, loader);
        }

        Iterator it = packages.iterator();
        String prefix = "";
        boolean found = false;

        do {
            try {
                String fullname = prefix + className;
                ClassUtils.loadClass(fullname, loader);
                found = true;
            } catch (ClassNotFoundException cnfe) {
                if (it.hasNext()) {
                    prefix = (String) it.next();
                    if (!prefix.endsWith("."))
                        prefix += ".";
                } else {
                    prefix = null;
                }
            }
        } while (!found && prefix != null);

        return prefix != null ? ParameterClassUtils.create(prefix + className,
                params, types, loader) : null;
    }

    public static Object create(String def, List packages, ClassLoader loader) {
        if (packages == null || packages.isEmpty()) {
            return ParameterClassUtils.createObject(def, loader);
        }

        String className = def;
        int gap = className.indexOf(' ');
        if (gap != -1) {
            className = def.substring(0, gap);
        }

        String prefix = findPrefix(packages, loader, className);

        return prefix != null ? ParameterClassUtils.createObject(prefix + def,
                loader) : null;
    }

    private static String findPrefix(List packages, ClassLoader loader,
            String className) {
        String prefix = "";
        Iterator it = packages.iterator();
        boolean found = false;

        do {
            try {
                String fullname = prefix + className;
                ClassUtils.loadClass(fullname, loader);
                found = true;
            } catch (ClassNotFoundException cnfe) {
                if (it.hasNext()) {
                    prefix = (String) it.next();
                    if (!prefix.endsWith("."))
                        prefix += ".";
                } else {
                    prefix = null;
                }
            }
        } while (!found && prefix != null);

        return prefix;
    }

    public static Object createObject(String name, Object[] params,
            Class[] types, List packages) {
        return create(name, params, types, packages,
                PackageParameterClassUtils.class.getClassLoader());
    }

    public static Object createObject(String name, Object param, List packages,
            ClassLoader loader) {
        return create(name, new Object[] { param }, null, packages, loader);
    }

    public static Object createObject(String name, Object param, List packages) {
        return create(name, new Object[] { param }, null, packages,
                PackageParameterClassUtils.class.getClassLoader());
    }

    public static Object createObject(String def, List packages,
            ClassLoader loader) {
        return create(def, packages, loader);
    }

    public static Object createObject(String def, List packages) {
        return create(def, packages, PackageParameterClassUtils.class
                .getClassLoader());
    }
}
