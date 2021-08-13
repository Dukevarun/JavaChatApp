package com.w9218308.javachatapp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
// import java.util.UUID;

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

    private void send(final byte[] data, final InetAddress address, final int port) {
        send = new Thread("Send") {
            @Override
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        send.start();
    }

    protected void process(DatagramPacket packet) {
        String string = new String(packet.getData());
        if (string.startsWith("/c/")) {
            // UUID id = UUID.randomUUID();
            int id = UniqueIdentifier.getIdentifier();
            System.out.println("Identifier: " + id);
            clients.add(new ServerClient(string.substring(3, string.length()), packet.getAddress(), packet.getPort(), id));
            System.out.println(string.substring(3, string.length()));
            String ID = "/c/" + id;
            send(ID, packet.getAddress(), packet.getPort());
        } else if (string.startsWith("/m/")) {
            sendToAll(string);
        } else {
            System.out.println(string);
        }
    }

    private void send(String message, InetAddress address, int port) {
        message += "/e/";
        send(message.getBytes(), address, port);
    }

    private void sendToAll(String message) {
        for (int i = 0; i < clients.size(); i++) {
            ServerClient client = clients.get(i);
            send(message.getBytes(), client.address, client.port);
        }
    }
}