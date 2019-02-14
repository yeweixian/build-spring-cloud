package com.dangerye.cobweb.proxy;

import com.alibaba.fastjson.JSON;
import com.dangerye.cobweb.entity.TransferRequest;
import com.dangerye.cobweb.utils.CommunicationOperation;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;

@Slf4j
public class RemoteBeanHandler implements InvocationHandler {

    public static Object newInstance(Class apiInterface) {
        return Proxy.newProxyInstance(RemoteBeanHandler.class.getClassLoader(), new Class[]{apiInterface}, new RemoteBeanHandler());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setClassName(method.getDeclaringClass().getName());
        transferRequest.setMethodName(method.getName());
        transferRequest.setParameters(Lists.newArrayList());

        Class<?>[] paramTypes = method.getParameterTypes();
        for (int i = 0; i < paramTypes.length; i++) {
            TransferRequest.Parameter parameter = new TransferRequest.Parameter();
            parameter.setParamType(paramTypes[i].getName());
            parameter.setParamValue(args[i]);
            transferRequest.getParameters().add(parameter);
        }

        return Optional.ofNullable(CommunicationOperation.requestRemoteService(transferRequest))
                .map(transferResponse -> {
                    try {
                        if (transferResponse.getReturnType() == null && transferResponse.getReturnValue() == null) {
                            return null;
                        }
                        Class<?> cls = ClassUtils.getClass(transferResponse.getReturnType());
                        if (Objects.equal(cls, Void.TYPE)) {
                            return null;
                        }

                        String str = JSON.toJSONString(transferResponse.getReturnValue());
                        return JSON.parseObject(str, cls);
                    } catch (Exception e) {
                        log.error("RemoteBeanHandler invoke error.", e);
                        return null;
                    }
                })
                .orElse(null);
    }
}
