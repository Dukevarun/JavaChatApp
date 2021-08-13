package com.w9218308.javachatapp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class Client extends JFrame {
    private JPanel contentPane;

    private String name, address;
    private int port;
    private JTextArea txthistory;
    private JTextField txtMessage;
    private JButton btnSend;
    private DefaultCaret caret;

    private DatagramSocket socket;
    private InetAddress ip;

    private Thread send;

    public Client(String name, String address, int port) {
        // super();
        this.name = name;
        this.address = address;
        this.port = port;
        boolean connect = openConnection(address);
        if (!connect) {
            System.err.println("Connection Failed!");
            console("Connection Failed!");
        }
        createWindow();
        console("Attempting a connection to " + address + ":" + port + ", user: " + name);
        String connection = "/c/" + name;
        send(connection.getBytes());
    }

    private boolean openConnection(String address) {
        try {
            socket = new DatagramSocket();
            ip = InetAddress.getByName(address);
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String receive() {
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = new String(packet.getData());
        return message;
    }

    private void send(final byte[] data) {
        send = new Thread("Send") {
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        send.start();
    }

    private void createWindow() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(880, 550);
        setTitle("Java Chat Client");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        txthistory = new JTextArea();
        txthistory.setEditable(false);
        JScrollPane scroll = new JScrollPane(txthistory);
        caret = (DefaultCaret) txthistory.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 10;
        gc.gridwidth = 3;
        gc.insets = new Insets(5, 5, 5, 5);
        contentPane.add(scroll, gc);

        txtMessage = new JTextField(10);
        txtMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    send(txtMessage.getText());
                }
            }
        });
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 2;
        gc.weighty = .2;
        gc.gridwidth = 2;
        gc.insets = new Insets(0, 5, 0, 5);

        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send(txtMessage.getText());
            }
        });
        contentPane.add(txtMessage, gc);
        gc.gridx = 2;
        gc.gridy = 1;
        gc.weightx = 0.1;
        gc.weighty = .1;
        gc.gridwidth = 1;
        gc.insets = new Insets(0, 0, 0, 5);
        contentPane.add(btnSend, gc);

        setVisible(true);
        txtMessage.requestFocusInWindow();
    }

    private void send(String message) {
        if (message.equals("")) {
            return;
        }
        message = name + ": " + message;
        console(message);
        send(message.getBytes());
        txtMessage.setText("");
    }

    public void console(String message) {
        txthistory.append(message + "\n\r");
        txthistory.setCaretPosition(txthistory.getDocument().getLength());
    }
}