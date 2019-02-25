package com.dangerye.cobweb.config;

import com.dangerye.cobweb.utils.CommunicationOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.*;

@Slf4j
public class CobwebRemoteCallServiceConfiguration {

    private static final int PORT = 34900;

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            log.error("CobwebRemoteCallService ServerSocket Error.", e);
        }

        Optional.ofNullable(serverSocket).ifPresent(server -> {

            log.info("CobwebRemoteCallService start & wait...");

            // 如果使用多线程，那就需要线程池，防止并发过高时创建过多线程耗尽资源
            ExecutorService threadPool1 = Executors.newFixedThreadPool(100);
            ExecutorService threadPool2 = Executors.newFixedThreadPool(100);

            Executors.newSingleThreadExecutor().execute(() -> {
                while (true) {
                    try {
                        // 监听端口
                        Socket socketItem = server.accept();
                        // 执行线程
                        FutureTask<Boolean> futureTask =
                                new FutureTask<>(() -> CommunicationOperation.handleResponse(socketItem, applicationContext));
                        // 控制执行
                        threadPool1.execute(futureTask);
                        // 控制超时销毁
                        threadPool2.execute(() -> {
                            try {
                                futureTask.get(1000, TimeUnit.MILLISECONDS);
                            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                                log.error("CobwebRemoteCallService futureTask run fail.", e);
                                futureTask.cancel(true);
                            }
                        });
                    } catch (Exception e) {
                        log.error("CobwebRemoteCallService socket exception", e);
                    }
                }
            });
        });
    }
}
