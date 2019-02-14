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
            ExecutorService threadPool = Executors.newFixedThreadPool(100);

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    while (true) {
                        Socket socketItem = server.accept();
                        FutureTask<Boolean> futureTask =
                                new FutureTask<>(() -> CommunicationOperation.handleResponse(socketItem, applicationContext));

                        threadPool.execute(futureTask);

                        boolean needLog = true;
                        try {
                            needLog = !futureTask.get(1000, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException | ExecutionException | TimeoutException e) {
                            futureTask.cancel(true);
                        }
                        if (needLog) {
                            log.error("CobwebRemoteCallService futureTask run fail.");
                        }
                    }
                } catch (Exception e) {
                    log.error("CobwebRemoteCallService socket exception", e);
                }
            });
        });
    }
}
