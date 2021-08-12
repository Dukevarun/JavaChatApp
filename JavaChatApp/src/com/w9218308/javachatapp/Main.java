package com.w9218308.javachatapp;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}
