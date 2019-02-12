package com.dangerye.cobweb.config;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Slf4j
public class CobwebRemoteCallServiceConfiguration {

    private static final int PORT = 34900;

    private static final String CHARSET = "UTF-8";

    @PostConstruct
    public void init() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        log.info("CobwebRemoteCallService start & wait...");

        // 如果使用多线程，那就需要线程池，防止并发过高时创建过多线程耗尽资源
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                while (true) {
                    Socket socketItem = server.accept();
                    FutureTask<Boolean> futureTask = new FutureTask<>(() -> {
                        try (Socket socket = socketItem) {

//                            Random random = new Random();
//                            int time = random.nextInt(2000);
//                            log.info("sleep time: " + time);
//                            Thread.sleep(time);

                            InputStream inputStream = socket.getInputStream();
                            byte[] bytes = new byte[1024];
                            int len;
                            StringBuilder sb = new StringBuilder();

                            while ((len = inputStream.read(bytes)) != -1) {
                                sb.append(new String(bytes, 0, len, CHARSET));
                            }

                            System.out.println("get message from client: " + sb);

                            OutputStream outputStream = socket.getOutputStream();

                            String message = "Hello, client! I get the message.";

                            outputStream.write(message.getBytes(CHARSET));

                            outputStream.close();
                            inputStream.close();
                            return true;
                        } catch (Exception e) {
                            log.error("CobwebRemoteCallService communication exception", e);
                            return false;
                        }
                    });

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
    }
}
