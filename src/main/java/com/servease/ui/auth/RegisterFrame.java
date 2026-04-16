package com.servease.ui.auth;

import com.servease.controller.UserController;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    public RegisterFrame() {

        setTitle("Register - Servease");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // 🔹 Title
        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 Fields
        JTextField nameField = createField("Name");
        JTextField emailField = createField("Email");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JTextField phoneField = createField("Phone");

        // 🔹 Role Dropdown
        String[] roles = {"USER", "PROVIDER"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        roleBox.setBorder(BorderFactory.createTitledBorder("Role"));

        // 🔹 Register Button
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(33, 150, 243));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 Login Redirect
        JButton loginBtn = new JButton("Already have account? Login");
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setForeground(Color.BLUE);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔥 ADD COMPONENTS
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(nameField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(emailField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(phoneField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(roleBox);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(registerBtn);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(loginBtn);

        add(mainPanel);

        // 🔥 REGISTER ACTION
        registerBtn.addActionListener(e -> {

            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String phone = phoneField.getText();
            String role = (String) roleBox.getSelectedItem();

            UserController controller = new UserController();
            controller.registerUser(name, email, password, phone, role);
            JOptionPane.showMessageDialog(null,"Registration Successful !");
            dispose();
            new LoginFrame();
        });

        // 🔥 LOGIN REDIRECT
        loginBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    // 🔥 REUSABLE FIELD METHOD
    private JTextField createField(String title) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(BorderFactory.createTitledBorder(title));
        return field;
    }
}