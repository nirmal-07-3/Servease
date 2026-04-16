package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard(User user){

        setTitle("Admin Dashboard");
        setSize(800,500);
        setLayout(new GridLayout(3,1,20,20));
        setLocationRelativeTo(null);

        JButton users = new JButton("Manage Users");
        JButton services = new JButton("Manage Services");
        JButton logout = new JButton("Logout");

        add(users);
        add(services);
        add(logout);

        logout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}