package com.example.shifumi.p2p;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerToPeerManager {
        private WifiP2pManager wifiP2pManager;
        private WifiP2pManager.Channel channel;
        private Context context;
        private ServerSocket serverSocket;
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public PeerToPeerManager(Context context) {
            this.context = context;
            initializeWifiDirect();
        }

        private void initializeWifiDirect() {
            wifiP2pManager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
            channel = wifiP2pManager.initialize(context, Looper.getMainLooper(), null);

            // Register Wi-Fi Direct broadcast receiver in your activity to receive Wi-Fi Direct events.
            // (Note: This is not included in this example)
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
