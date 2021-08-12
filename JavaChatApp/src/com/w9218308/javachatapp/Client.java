package com.w9218308.javachatapp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

public class Client extends JFrame {
    private JPanel contentPane;

    private String name, address;
    private int port;
    private JTextArea txthistory;
    private JTextField txtMessage;
    private JButton btnSend;
    
    public Client(String name, String address, int port) {
        super();
        this.name = name;
        this.address = address;
        this.port = port;
        createWindow();
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
        contentPane.setBorder(new EmptyBorder(5, 5 , 5, 5));
        setContentPane(contentPane);
        setVisible(true);
        
        txthistory = new JTextArea();
        txtMessage = new JTextField(10);
        btnSend = new JButton("Send");
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 10;
        gc.gridwidth = 3;
        contentPane.add(txthistory, gc);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 2;
        gc.weighty = .2;
        gc.gridwidth = 2;
        gc.insets = new Insets(0, 0, 0, 5);
        contentPane.add(txtMessage, gc);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 2;
        gc.gridy = 1;
        gc.weightx = 0.1;
        gc.weighty = .1;
        gc.gridwidth= 1;
        gc.insets = new Insets(0, 0, 0, 5);
        contentPane.add(btnSend, gc);
    }
}
