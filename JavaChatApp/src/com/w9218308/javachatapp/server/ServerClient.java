package com.w9218308.javachatapp.server;

import java.net.InetAddress;

public class ServerClient {
    public String name;
    public InetAddress address;
    public int port;
    public int attempt = 0;
    private final int ID;

    public ServerClient(String name, InetAddress address, int port, final int ID) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}