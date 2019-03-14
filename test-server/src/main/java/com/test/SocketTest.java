package com.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Socket 研究学习
 * 参考地址: https://www.cnblogs.com/yiwangzhibujian/p/7107785.html
 */
@Slf4j
public class SocketTest {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 34900;

    private static final String CHARSET = "UTF-8";

    /**
     * Receive
     */
    @Test
    public void testSocketServer1() throws IOException {
        System.out.println("server start & wait...");
        ServerSocket server = new ServerSocket(PORT);
        Socket socket = server.accept();
        InputStream inputStream = socket.getInputStream();

        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();

        while ((len = inputStream.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len, CHARSET));
        }

        System.out.println("get message from client: " + sb);
        inputStream.close();
        socket.close();
        server.close();
    }

    /**
     * Send
     */
    @Test
    public void testSocketClient1() throws IOException {
        System.out.println("client send...");
        Socket socket = new Socket(HOST, PORT);
        OutputStream outputStream = socket.getOutputStream();

        String message = "Hello, DangerYe";

        outputStream.write(message.getBytes(CHARSET));

        System.out.println("client send done.");
        outputStream.close();
        socket.close();
    }

    /**
     * Receive & Reply
     */
    @Test
    public void testSocketServer2() throws IOException {
        System.out.println("server start & wait...");
        ServerSocket server = new ServerSocket(PORT);
        Socket socket = server.accept();
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

        System.out.println("server stop.");
        outputStream.close();
        inputStream.close();
        socket.close();
        server.close();
    }

    /**
     * Send & Result
     */
    @Test
    public void testSocketClient2() throws IOException {
        System.out.println("client send...");
        Socket socket = new Socket(HOST, PORT);
        OutputStream outputStream = socket.getOutputStream();

        String message = "Hello, DangerYe";

        outputStream.write(message.getBytes(CHARSET));
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();

        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();

        while ((len = inputStream.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len, CHARSET));
        }

        System.out.println("get message from server: " + sb);
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    /**
     * Loop Receive
     */
    @Test
    public void testSocketServer3() throws IOException {
        System.out.println("server start & wait...");
        ServerSocket server = new ServerSocket(PORT);
        Socket socket = server.accept();
        InputStream inputStream = socket.getInputStream();

        byte[] bytes;
        while (true) {
            int first = inputStream.read();
            if (first == -1) {
                break;
            }

            int second = inputStream.read();
            int length = (first << 8) + second;
            bytes = new byte[length];
            int len = inputStream.read(bytes);
            System.out.println("get message from client: " + new String(bytes, 0, len, CHARSET));
        }

        System.out.println("server stop.");
        inputStream.close();
        socket.close();
        server.close();
    }

    /**
     * Loop Send
     */
    @Test
    public void testSocketClient3() throws IOException {
        System.out.println("client send...");
        Socket socket = new Socket(HOST, PORT);
        OutputStream outputStream = socket.getOutputStream();

        String message = "Hello, DangerYe";
        byte[] sendBytes = message.getBytes(CHARSET);
        outputStream.write(sendBytes.length >> 8);
        outputStream.write(sendBytes.length);
        outputStream.write(sendBytes);
        outputStream.flush();

        message = "Here is client.";
        sendBytes = message.getBytes(CHARSET);
        outputStream.write(sendBytes.length >> 8);
        outputStream.write(sendBytes.length);
        outputStream.write(sendBytes);
        outputStream.flush();

        message = "Nice to meet you!";
        sendBytes = message.getBytes(CHARSET);
        outputStream.write(sendBytes.length >> 8);
        outputStream.write(sendBytes.length);
        outputStream.write(sendBytes);

        System.out.println("client send done.");
        outputStream.close();
        socket.close();
    }

    @Test
    public void testSocketServer4() throws IOException {
        System.out.println("server start & wait...");
        ServerSocket server = new ServerSocket(PORT);

        //如果使用多线程，那就需要线程池，防止并发过高时创建过多线程耗尽资源
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                while (true) {
                    System.out.println("server accept...");
                    Socket socket = server.accept();
                    threadPool.submit(() -> {
                        try {
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

                            System.out.println("server stop.");
                            outputStream.close();
                            inputStream.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        while (true) {
        }
    }

    @Test
    public void testSocketServer5() throws IOException {
        System.out.println("server start & wait...");
        ServerSocket server = new ServerSocket(PORT);
        Socket socket = server.accept();
        InputStream inputStream = socket.getInputStream();

        byte[] bytes = new byte[1024];
        int i = 0;
        while (true) {
            int data = inputStream.read();
            if (data == -1) {
                break;
            }
            System.out.println("data: " + data);
            bytes[i] = (byte) data;
            i++;
        }

        System.out.println("server stop. msg: " + new String(bytes, 0, i, CHARSET));
        inputStream.close();
        socket.close();
        server.close();
    }

    @Test
    public void testSocketClient5() throws IOException {
        System.out.println("client send...");
        Socket socket = new Socket(HOST, PORT);
        OutputStream outputStream = socket.getOutputStream();
//        for (int i = -128; i < 128; i++) {
//            outputStream.write(i);
//        }

//        outputStream.write(-1);

        String message = "Hello, DangerYe. 我好犀利";
        byte[] sendBytes = message.getBytes(CHARSET);
        outputStream.write(sendBytes);

        System.out.println("client send done.");
        outputStream.close();
        socket.close();
    }

    @Test
    public void testNettyServer() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .bind(PORT)
                .sync()
                .channel().closeFuture().sync();
    }

    @Test
    public void testNettyClient() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                    }
                });
        Channel channel = bootstrap.connect(HOST, PORT).channel();

        while (true) {
            channel.writeAndFlush("Hello world! time: " + System.currentTimeMillis());
            Thread.sleep(2000);
        }
    }
}
