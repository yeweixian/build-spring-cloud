package com.dangerye.cobweb.utils;

import com.alibaba.fastjson.JSON;
import com.dangerye.cobweb.entity.TransferRequest;
import com.dangerye.cobweb.entity.TransferResponse;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CommunicationOperation {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 34900;
    private static final String CHARSET = "UTF-8";

    public static TransferResponse requestRemoteService(TransferRequest transferRequest) throws IOException {

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
        } catch (IOException e) {
            log.error("CommunicationOperation requestRemoteService error.", e);
            throw e;
        }

        try {
            return JSON.parseObject(response, TransferResponse.class);
        } catch (Exception e) {
            log.error("CommunicationOperation requestRemoteService error.", e);
            return null;
        }
    }

    public static boolean handleResponse(Socket socketItem, ApplicationContext applicationContext) {
        try (Socket socket = socketItem) {

            // Receive
            InputStream inputStream = socket.getInputStream();
            String transferRequestMsg = read(inputStream);

            // Reply
            OutputStream outputStream = socket.getOutputStream();
            TransferResponse transferResponse = Optional.ofNullable(JSON.parseObject(transferRequestMsg, TransferRequest.class))
                    .map(transferRequest -> {

                        List<Class<?>> paramTypes = Lists.newArrayList();
                        List<Object> paramValues = Lists.newArrayList();

                        if (CollectionUtils.isNotEmpty(transferRequest.getParameters())) {
                            transferRequest.getParameters().forEach(parameter -> {
                                try {
                                    paramTypes.add(ClassUtils.getClass(parameter.getParamType()));
                                    paramValues.add(parameter.getParamValue());
                                } catch (Exception ignored) {
                                }
                            });
                        }
                        // Handle
                        return ServiceOperation.custom(applicationContext)
                                .handle(transferRequest.getClassName(),
                                        transferRequest.getMethodName(),
                                        paramTypes.toArray(new Class<?>[]{}),
                                        paramValues.toArray());
                    })
                    .orElse(new TransferResponse());
            String message = JSON.toJSONString(transferResponse);
            write(outputStream, message);
            outputStream.close();
            inputStream.close();
            return true;
        } catch (Exception e) {
            log.error("CommunicationOperation handleResponse error.", e);
            return false;
        }
    }

    private static String read(InputStream inputStream) throws IOException {
        String result = null;
        while (true) {
            int first = inputStream.read();
            if (first == -1) {
                break;
            }

            int second = inputStream.read();
            int length = (first << 8) + second;
            byte[] bytes = new byte[length];
            int len = inputStream.read(bytes);
            result = new String(bytes, 0, len, CHARSET);
        }
        return result;
    }

    private static void write(OutputStream outputStream, String message) throws IOException {
        byte[] sendBytes = message.getBytes(CHARSET);
        outputStream.write(sendBytes.length >> 8);
        outputStream.write(sendBytes.length);
        outputStream.write(sendBytes);
    }
}
