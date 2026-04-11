package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserDashboard extends JFrame {

    private User user;

    public UserDashboard(User user) {

        this.user = user;

        setTitle("User Dashboard");
        setSize(650, 400);
        setLayout(null); // 🔥 IMPORTANT
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔹 Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome " + user.getName());
        welcomeLabel.setBounds(20, 20, 300, 30);
        add(welcomeLabel);

        // 🔹 Browse Services Button
        JButton browseBtn = new JButton("Browse Services");
        browseBtn.setBounds(200, 120, 200, 40);
        add(browseBtn);

        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new BrowseServicesFrame(user);
                //JOptionPane.showMessageDialog(UserDashboard.this, "Browse Services Clicked");
                // 👉 Later: open BrowseServicesFrame
            }
        });

        // 🔹 My Bookings Button (future)
        JButton bookingBtn = new JButton("My Bookings");
        bookingBtn.setBounds(200, 180, 200, 40);
        add(bookingBtn);

        bookingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(UserDashboard.this, "My Bookings Clicked");
                // 👉 Later: open BookingFrame
            }
        });

        // 🔹 Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(500, 20, 100, 30);
        add(logoutBtn);

        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int confirm = JOptionPane.showConfirmDialog(
                        UserDashboard.this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose(); // close dashboard
                    new LoginFrame(); // back to login
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}