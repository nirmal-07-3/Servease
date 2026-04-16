package com.servease.ui.auth;

import com.servease.controller.UserController;
import com.servease.model.User;
import com.servease.ui.dashboard.*;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame(){

        setTitle("Servease Login");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== MAIN BG =====
        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(245,245,245));

        // ===== CARD =====
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(300, 350));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // TITLE
        JLabel title = new JLabel("Servease Login");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // FIELDS
        JTextField email = new JTextField();
        email.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        email.setBorder(BorderFactory.createTitledBorder("Email"));

        JPasswordField pass = new JPasswordField();
        pass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        pass.setBorder(BorderFactory.createTitledBorder("Password"));

        // BUTTONS
        JButton login = new JButton("Login");
        login.setBackground(new Color(33,150,243));
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton register = new JButton("Register");
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);
        register.setForeground(Color.BLUE);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ADD
        card.add(title);
        card.add(Box.createVerticalStrut(20));
        card.add(email);
        card.add(Box.createVerticalStrut(15));
        card.add(pass);
        card.add(Box.createVerticalStrut(20));
        card.add(login);
        card.add(Box.createVerticalStrut(10));
        card.add(register);

        bg.add(card);
        add(bg);

        // ===== ACTION =====
        login.addActionListener(e -> {

            UserController c = new UserController();
            User u = c.loginUser(email.getText(), new String(pass.getPassword()));

            if(u == null){
                JOptionPane.showMessageDialog(this,"Invalid Credentials");
                return;
            }

            dispose();

            switch(u.getRole()){
                case "USER" -> new UserDashboard(u);
                case "PROVIDER" -> new ProviderDashboard(u);
                case "ADMIN" -> new AdminDashboard(u);
            }
        });

        register.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });

        setVisible(true);
    }
}