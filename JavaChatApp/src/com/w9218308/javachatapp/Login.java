package com.w9218308.javachatapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {
    private JPanel contentPane;
    private JLabel lblName;
    private JLabel lblIpAddress;
    private JLabel lblPort;
    private JLabel lblAddressDesc;
    private JLabel lblPortDesc;
    private JTextField txtName;
    private JTextField txtAddress;
    private JTextField txtPort;
    private JButton btnLogin;

    public Login(String string) {
        super(string);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 380);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        lblName = new JLabel("Name:");
        lblName.setBounds(127, 34, 45, 16);
        contentPane.add(lblName);

        txtName = new JTextField(10);
        txtName.setBounds(67, 50, 165, 28);
        contentPane.add(txtName);

        lblIpAddress = new JLabel("IP Address:");
        lblIpAddress.setBounds(111, 96, 77, 16);
        contentPane.add(lblIpAddress);

        txtAddress = new JTextField(10);
        txtAddress.setBounds(67, 116, 165, 28);
        contentPane.add(txtAddress);

        lblAddressDesc = new JLabel("(eg: 192.168.0.2)");
        lblAddressDesc.setBounds(94, 142, 112, 16);
        contentPane.add(lblAddressDesc);

        lblPort = new JLabel("Port:");
        lblPort.setBounds(133, 171, 34, 16);
        contentPane.add(lblPort);

        txtPort = new JTextField(10);
        txtPort.setBounds(67, 191, 165, 28);
        contentPane.add(txtPort);

        lblPortDesc = new JLabel("(eg: 8192)");
        lblPortDesc.setBounds(116, 218, 68, 16);
        contentPane.add(lblPortDesc);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(91, 311, 117, 28);
        contentPane.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String address = txtAddress.getText();
                int port = Integer.parseInt(txtPort.getText());
                login(name, address, port);
            }
        });
    }

    private void login(String name, String address, int port) {
        dispose();
        new Client(name, address, port);
    }

}