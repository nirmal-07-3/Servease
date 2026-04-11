package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProviderDashboard extends JFrame {

    private User user;

    public ProviderDashboard(User user) {

        this.user = user;

        setTitle("Provider Dashboard");
        setSize(650, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔹 Welcome Label (NO ALIGNMENT ERROR)
        JLabel welcomeLabel = new JLabel("Welcome " + user.getName());
        welcomeLabel.setBounds(20, 20, 300, 30);
        add(welcomeLabel);

        // 🔹 My Services Button
        JButton serviceBtn = new JButton("My Services");
        serviceBtn.setBounds(200, 120, 200, 40);
        add(serviceBtn);

        serviceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProviderServicesFrame(user);
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
                        ProviderDashboard.this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose(); // close dashboard
                    new LoginFrame(); // go back to log in
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}