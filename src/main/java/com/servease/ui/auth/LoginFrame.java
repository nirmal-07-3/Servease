package com.servease.ui.auth;

import com.servease.controller.UserController;
import com.servease.model.User;
import com.servease.ui.dashboard.AdminDashboard;
import com.servease.ui.dashboard.ProviderDashboard;
import com.servease.ui.dashboard.UserDashboard;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {

        setTitle("Login - Servease");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // 🔹 Title
        JLabel title = new JLabel("Servease Login");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 Email
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        // 🔹 Password
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        // 🔹 Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(33, 150, 243));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 Register link
        JButton registerBtn = new JButton("Register");
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);
        registerBtn.setForeground(Color.BLUE);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔥 ADD COMPONENTS
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(emailField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(loginBtn);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(registerBtn);

        add(mainPanel);

        // 🔥 LOGIN ACTION
        loginBtn.addActionListener(e -> {

            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            UserController controller = new UserController();

            if(email.isEmpty()||password.isEmpty()){
                JOptionPane.showMessageDialog(null,"Fill All Fields!!");
                return;
            }

            User user = controller.loginUser(email, password);

            if (user != null) {
                dispose(); // close login
            }
            if (user.getRole().equals("USER")) {
                new UserDashboard(user);
            }
            else if (user.getRole().equals("PROVIDER")) {
                new ProviderDashboard(user);
            }
            else if (user.getRole().equals("ADMIN")) {
                new AdminDashboard(user);
            }
            else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials");
            }
        });

        // 🔥 REGISTER REDIRECT
        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });

        setVisible(true);
    }
}