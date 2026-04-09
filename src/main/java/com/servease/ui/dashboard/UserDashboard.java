package com.servease.ui.dashboard;

import com.servease.model.User;

import javax.swing.*;

public class UserDashboard extends JFrame {

    public UserDashboard(User user) {

        setTitle("User Dashboard");
        setSize(300, 200);
        add(new JLabel("Welcome"+user.getName()+"!", SwingConstants.CENTER));
        setVisible(true);
    }
}