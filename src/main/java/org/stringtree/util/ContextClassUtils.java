package org.stringtree.util;

import java.util.List;

import org.stringtree.SystemContext;
import org.stringtree.finder.StringFinder;

public class ContextClassUtils {
    
	@SuppressWarnings("rawtypes")
	public static Object create(String name, Object[] params, Class[] types,
            StringFinder context) {
        return PackageParameterClassUtils.create(name, params, types,
                (List) context.getObject(SystemContext.IMPORT_PACKAGES),
                getClassLoader(context));
    }

	@SuppressWarnings("rawtypes")
	public static Object createObject(String def, StringFinder context, boolean init) {
        Object object = PackageParameterClassUtils.create(def, (List) context
                .getObject(SystemContext.IMPORT_PACKAGES),
                getClassLoader(context));
        if (init && null != object) MethodCallUtils.call(object, "init", context);
        return object;
    }

	@SuppressWarnings("rawtypes")
	public static Object createObject(String name, String param,
            StringFinder context, boolean init) {
        Object object = PackageParameterClassUtils.create(name, new Object[] { param },
                new Class[] { String.class }, (List) context
                    .getObject(SystemContext.IMPORT_PACKAGES),
                getClassLoader(context));
        if (init && null != object) MethodCallUtils.call(object, "init", context);
        return object;
    }

    public static ClassLoader getClassLoader(StringFinder context) {
        ClassLoader ret = null;
        if (context != null)
            ret = (ClassLoader) context
                .getObject(SystemContext.SYSTEM_CLASSLOADER);
        if (ret == null)
            ret = ContextClassUtils.class.getClassLoader();
        return ret;
    }

    public static Object ensureObject(String def, StringFinder context) {
        Object object = context.getObject(def);
        if (null == object) {
            object = createObject(def, context, true);
        }
        return object;
    }

    public static Object ensureObject(String name, String param, StringFinder context, boolean init) {
        if (StringUtils.isBlank(param)) {
            Object obj = context.getObject(name);
            if (null != obj) return obj;
        }
        
        return createObject(name, param, context, init);
    }

    public static Object ensureObject(String name, String param, StringFinder context) {
        if (StringUtils.isBlank(param)) {
            Object obj = context.getObject(name);
            if (null != obj) return obj;
        }
        
        return createObject(name, param, context, true);
    }
}
