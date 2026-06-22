package main;

import java.awt.EventQueue;
import view.view_login;

public class main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    view_login vista = new view_login();
                    vista.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}