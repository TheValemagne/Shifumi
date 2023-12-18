package com.example.shifumi.p2p;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.Manifest;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.shifumi.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerToPeerManager {
        private WifiP2pManager wifiP2pManager;
        private WifiP2pManager.Channel channel;
        private ServerSocket serverSocket;
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        private MainActivity activity;

        public PeerToPeerManager(WifiP2pManager manager, WifiP2pManager.Channel channel) {
            this.wifiP2pManager = manager;
            this.channel = channel;
        }

        @SuppressLint("MissingPermission")
        public void discoverPeers() {
            wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    // Wi-Fi Direct discovery started successfully
                }

                @Override
                public void onFailure(int reasonCode) {
                    // Wi-Fi Direct discovery failed
                }
            });
        }

    @SuppressLint("MissingPermission")
    public void connectToPeer(String ipAddress) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = ipAddress;

        wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Connection initiated successfully
            }

            @Override
            public void onFailure(int reason) {
                // Connection failed
            }
        });
    }

        @SuppressLint("MissingPermission")
        public void createGroup() {
            wifiP2pManager.createGroup(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    // Group created successfully
                }

                @Override
                public void onFailure(int reason) {
                    // Group creation failed
                }
            });
        }

        public void startServer() {
            try {
                serverSocket = new ServerSocket(8888);
                new Thread(new ServerThread()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private class ServerThread implements Runnable {
            @Override
            public void run() {
                try {
                    socket = serverSocket.accept();
                    initializeStreams();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
