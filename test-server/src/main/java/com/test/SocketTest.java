package com.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
}
