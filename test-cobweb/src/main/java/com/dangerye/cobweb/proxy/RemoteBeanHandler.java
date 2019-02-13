package com.dangerye.cobweb.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RemoteBeanHandler implements InvocationHandler {

    public static Object newInstance(Class apiInterface) {
        return Proxy.newProxyInstance(RemoteBeanHandler.class.getClassLoader(), new Class[]{apiInterface}, new RemoteBeanHandler());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String className = method.getDeclaringClass().getName();
        System.out.println(className);
        String methodName = method.getName();
        System.out.println(methodName);
        Class<?>[] paramTypes = method.getParameterTypes();


        return null;
    }
}
