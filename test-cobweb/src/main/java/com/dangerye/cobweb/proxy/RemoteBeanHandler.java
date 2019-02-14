package com.dangerye.cobweb.proxy;

import com.alibaba.fastjson.JSON;
import com.dangerye.cobweb.entity.TransferRequest;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

@Slf4j
public class RemoteBeanHandler implements InvocationHandler {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 34900;

    private static final String CHARSET = "UTF-8";

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

        String message = JSON.toJSONString(transferRequest);
        Socket socket = new Socket(HOST, PORT);

        OutputStream outputStream = socket.getOutputStream();
        byte[] sendBytes = message.getBytes(CHARSET);
        outputStream.write(sendBytes.length >> 8);
        outputStream.write(sendBytes.length);
        outputStream.write(sendBytes);
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        String response = null;
        while (true) {
            int first = inputStream.read();
            if (first == -1) {
                break;
            }

            int second = inputStream.read();
            int length = (first << 8) + second;
            byte[] bytes = new byte[length];
            int len = inputStream.read(bytes);
            response = new String(bytes, 0, len, CHARSET);
        }

        try {
            return JSON.parseObject(response, method.getReturnType());
        } catch (Exception e) {
            log.error("Proxy class JSON.parseObject error.", e);
            return null;
        }
    }
}
