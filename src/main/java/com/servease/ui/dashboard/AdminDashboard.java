package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {

    private User user;

    public AdminDashboard(User user) {

        this.user = user;

        setTitle("Admin Dashboard");
        setSize(650, 400);
        setLayout(null); // 🔥 IMPORTANT
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔹 Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome Admin: " + user.getName());
        welcomeLabel.setBounds(20, 20, 300, 30);
        add(welcomeLabel);

        // 🔹 Manage Users Button (example)
        JButton manageUsersBtn = new JButton("Manage Users");
        manageUsersBtn.setBounds(200, 120, 200, 40);
        add(manageUsersBtn);

        manageUsersBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AdminDashboard.this, "Manage Users Clicked");
            }
        });

        // 🔹 Manage Services Button (optional)
        JButton manageServicesBtn = new JButton("Manage Services");
        manageServicesBtn.setBounds(200, 180, 200, 40);
        add(manageServicesBtn);

        manageServicesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AdminDashboard.this, "Manage Services Clicked");
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
                        AdminDashboard.this,
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