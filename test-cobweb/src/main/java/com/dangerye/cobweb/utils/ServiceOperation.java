package com.dangerye.cobweb.utils;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public final class ServiceOperation {

    private ApplicationContext applicationContext;

    private ServiceOperation() {
    }

    public static ServiceOperation custom(ApplicationContext applicationContext) {
        ServiceOperation result = new ServiceOperation();
        result.setApplicationContext(applicationContext);
        return result;
    }

    public void test(String className, String methodName, Class<?>[] paramTypes, Object[] args) {
        try {
            Class<?> beanClass = Class.forName(className);
            Method method = beanClass.getMethod(methodName, paramTypes);

            Object bean = applicationContext.getBean(beanClass);
            Object result = method.invoke(bean, args);

            System.out.println("result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
