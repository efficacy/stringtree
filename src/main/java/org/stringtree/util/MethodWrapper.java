package org.stringtree.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface MethodWrapper {
    Object invoke(Object object) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
    boolean isCallable();
    public Method getMethod();
    public Object getArgument(int arg);
}
