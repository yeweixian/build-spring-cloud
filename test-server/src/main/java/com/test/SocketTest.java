package com.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
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

    public static class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new StringDecoder());
        }
    }

    public static class ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            System.out.println(msg.toString(CharsetUtil.UTF_8));
            ctx.write(Unpooled.copiedBuffer("Hello, client. I get the msg.", CharsetUtil.UTF_8));
        }
    }

    public static class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, server. I am client.", CharsetUtil.UTF_8));
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            System.out.println(msg.toString(CharsetUtil.UTF_8));
        }
    }

    @Test
    public void testNettyServer() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ServerHandler());
            serverBootstrap.bind(PORT).sync()
                    .channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Test
    public void testNettyClient() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ClientHandler());
            bootstrap.connect(HOST, PORT).sync()
                    .channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
