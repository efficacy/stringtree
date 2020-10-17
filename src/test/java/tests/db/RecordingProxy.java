package tests.db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.stringtree.mock.RecordingMock;
import org.stringtree.util.MethodCallUtils;
import org.stringtree.util.MethodWrapper;

public class RecordingProxy extends RecordingMock implements InvocationHandler {
    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        record(proxy, name, args);
        MethodWrapper wrapper = MethodCallUtils.findMethod(this, name, args);
        if (null != wrapper && wrapper.isCallable()) {
            return MethodCallUtils.call(this, wrapper);
        }

        return null;
    }

	@SuppressWarnings("rawtypes")
	public Object createProxy(Class class1) {
        try {
            return Proxy.newProxyInstance(
                class1.getClassLoader(),
                new Class[] { class1 }, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
     }
}
