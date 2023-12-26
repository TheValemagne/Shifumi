package com.example.shifumi.p2p;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            socket = serverSocket.accept();
            initializeStreams();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeStreams() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(byte[] data) {
        try {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] receiveData() {
        byte[] buffer = new byte[1024];
        int bytesRead;
        try {
            bytesRead = inputStream.read(buffer);
            if (bytesRead != -1) {
                byte[] data = new byte[bytesRead];
                System.arraycopy(buffer, 0, data, 0, bytesRead);
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
