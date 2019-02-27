package com.dangerye.cobweb.utils;

import com.alibaba.fastjson.JSON;
import com.dangerye.cobweb.entity.TransferRequest;
import com.dangerye.cobweb.entity.TransferResponse;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CommunicationOperation {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 34900;
    private static final String CHARSET = "UTF-8";

    public static TransferResponse requestRemoteService(TransferRequest transferRequest) throws IOException, ClassNotFoundException {

        String message = JSON.toJSONString(transferRequest);
        String response;

        try (Socket socket = new Socket(HOST, PORT)) {
            // send
            OutputStream outputStream = socket.getOutputStream();
            write(outputStream, message);
            socket.shutdownOutput();

            // result
            InputStream inputStream = socket.getInputStream();
            response = read(inputStream);

            inputStream.close();
            outputStream.close();
        }

        TransferResponse transferResponse = JSON.parseObject(response, TransferResponse.class);
        if (transferResponse.getReturnType() == null) {
            return null;
        }
        Class<?> cls = ClassUtils.getClass(transferResponse.getReturnType());
        if (Objects.equal(cls, Void.TYPE)) {
            return null;
        }

        transferResponse.setReturnValue(Optional.ofNullable(transferResponse.getReturnValue())
                .map(JSON::toJSONString)
                .map(item -> JSON.parseObject(item, cls))
                .orElse(null));
        return transferResponse;
    }

    public static boolean handleResponse(Socket socketItem, ApplicationContext applicationContext) throws IOException,
            ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        try (Socket socket = socketItem) {
            // Receive
            InputStream inputStream = socket.getInputStream();
            String transferRequestMsg = read(inputStream);

            // Reply
            OutputStream outputStream = socket.getOutputStream();
            TransferRequest transferRequest = JSON.parseObject(transferRequestMsg, TransferRequest.class);
            if (transferRequest == null) {
                throw new IOException("CommunicationOperation handleResponse error: transferRequestMsg{" + transferRequestMsg + "}");
            }

            // 参数类型
            List<Class<?>> paramTypes = Lists.newArrayList();
            // 参数值
            List<Object> paramValues = Lists.newArrayList();

            if (CollectionUtils.isNotEmpty(transferRequest.getParameters())) {
                transferRequest.getParameters().forEach(parameter -> {
                    try {
                        Class<?> cls = ClassUtils.getClass(parameter.getParamType());
                        Object param = Optional.ofNullable(parameter.getParamValue())
                                .map(JSON::toJSONString)
                                .map(item -> JSON.parseObject(item, cls))
                                .orElse(null);
                        // add to List
                        paramTypes.add(cls);
                        paramValues.add(param);
                    } catch (Exception e) {
                        log.error("CommunicationOperation handleResponse error.", e);
                    }
                });

                int paramSize = transferRequest.getParameters().size();
                if (paramSize != paramTypes.size() || paramSize != paramValues.size()) {
                    throw new IOException("CommunicationOperation handleResponse Parameter exception.");
                }
            }

            // Handle
            TransferResponse transferResponse = ServiceOperation.custom(applicationContext)
                    .handle(transferRequest.getClassName(), transferRequest.getMethodName(), paramTypes.toArray(new Class<?>[]{}), paramValues.toArray());
            String message = JSON.toJSONString(transferResponse);

            write(outputStream, message);
            outputStream.close();
            inputStream.close();
            return true;
        }
    }

    private static String read(InputStream inputStream) throws IOException {
        int first = inputStream.read();
        if (first == -1) {
            throw new IOException("read exception.");
        }
        int second = inputStream.read();
        int length = (first << 8) + second;
        byte[] bytes = new byte[length];
        int len = inputStream.read(bytes);

        String result = new String(bytes, 0, len, CHARSET);
        log.info("CommunicationOperation.read, message:{}", result);
        return result;
    }

    private static void write(OutputStream outputStream, String message) throws IOException {
        log.info("CommunicationOperation.write, message:{}", message);
        byte[] sendBytes = message.getBytes(CHARSET);
        if ((sendBytes.length >> 16) > 0) {
            throw new IllegalArgumentException("Transmit data exceeding 65536 bytes.");
        }
        outputStream.write(sendBytes.length >> 8);
        outputStream.write(sendBytes.length);
        outputStream.write(sendBytes);
    }
}
