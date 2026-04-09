package com.servease.ui.dashboard;

import com.servease.model.User;

import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard(User user) {
        setTitle("Admin Dashboard");
        setSize(300, 200);
        add(new JLabel("Welcome Admin!", SwingConstants.CENTER));
        setVisible(true);
    }
}