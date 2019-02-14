package com.dangerye.cobweb.utils;

import com.dangerye.cobweb.entity.TransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

@Slf4j
public final class ServiceOperation {

    private ApplicationContext applicationContext;

    private ServiceOperation() {
    }

    public static ServiceOperation custom(ApplicationContext applicationContext) {
        ServiceOperation result = new ServiceOperation();
        result.setApplicationContext(applicationContext);
        return result;
    }

    public TransferResponse handle(String className, String methodName, Class<?>[] paramTypes, Object[] args) {
        try {
            Class<?> beanClass = Class.forName(className);
            Method method = beanClass.getMethod(methodName, paramTypes);

            Object bean = applicationContext.getBean(beanClass);
            Object result = method.invoke(bean, args);

            TransferResponse transferResponse = new TransferResponse();
            transferResponse.setReturnType(method.getReturnType().getName());
            transferResponse.setReturnValue(result);
            return transferResponse;
        } catch (Exception e) {
            log.error("ServiceOperation handle error.", e);
            return null;
        }
    }

    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
