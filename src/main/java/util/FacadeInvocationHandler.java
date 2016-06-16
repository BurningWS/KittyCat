package util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wangsong09 on 2016/6/16.
 *
 * 作为基本代理
 */

public class FacadeInvocationHandler implements InvocationHandler {

    private Object target;
    private Class[] interfaces;

    public FacadeInvocationHandler(Object target, Class... interfaces) {
        this.target = target;
        this.interfaces = interfaces;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }

    public Object getProxy() {
        if (target == null || interfaces == null)
            return null;

        return Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, this);
    }
}
