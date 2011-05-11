package tests.db;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.stringtree.mock.RecordingMock;
import org.stringtree.util.MethodCallUtils;
import org.stringtree.util.MethodWrapper;

public class RecordingProxy extends RecordingMock implements InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        record(proxy, name, args);
        MethodWrapper wrapper = MethodCallUtils.findMethod(this, name, args);
        if (null != wrapper && wrapper.isCallable()) {
            return MethodCallUtils.call(this, wrapper);
        }
        
        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Object createProxy(Class class1) {
        try {
            Class proxyClass = Proxy.getProxyClass(
                class1.getClassLoader(), 
                new Class[] { class1 });
            Constructor constructor = proxyClass.getConstructor(
                new Class[] { InvocationHandler.class });
            return constructor.newInstance(new Object[] { this });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
     }
}
