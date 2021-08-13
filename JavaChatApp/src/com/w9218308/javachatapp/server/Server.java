package com.w9218308.javachatapp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable {
    private int port;
    private DatagramSocket socket;
    private Thread run, manage, send, receive;
    private boolean running = false;

    public Server(int port) {
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }
        run = new Thread(this, "Server");
        run.start();
    }

    @Override
    public void run() {
        running = true;
        System.out.println("Server started on port " + port);
        manageClients();
        receive();
    }

    private void manageClients() {
        manage = new Thread("Manage") {
            @Override
            public void run() {
                while (running) {
                    // Manage
                }
            }
        };
        manage.start();
    }

    private void receive() {
        receive = new Thread("Receive") {
            @Override
            public void run() {
                while (running) {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String string = new String(packet.getData());
                    System.out.println(string);
                }
            }
        };
        receive.start();
    }
}
