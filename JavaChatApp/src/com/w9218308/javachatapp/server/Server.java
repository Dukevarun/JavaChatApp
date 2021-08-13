package com.w9218308.javachatapp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private List<ServerClient> clients = new ArrayList<ServerClient>();

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
                    process(packet);

                    clients.add(new ServerClient("name", packet.getAddress(), packet.getPort(), 50));
                    System.out.println(clients.get(0).address.toString() + ":" + clients.get(0).port);
                }
            }
        };
        receive.start();
    }

    protected void process(DatagramPacket packet) {
        String string = new String(packet.getData());
        if (string.startsWith("/c/")) {
            clients.add(new ServerClient(string.substring(3, string.length()), packet.getAddress(), packet.getPort(), 50));
            System.out.println(string.substring(3, string.length()));
        } else {
            System.out.println(string);
        }
    }
}
