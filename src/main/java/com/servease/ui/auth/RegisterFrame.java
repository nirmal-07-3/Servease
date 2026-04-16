package com.servease.ui.auth;

import com.servease.controller.UserController;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    public RegisterFrame(){

        setTitle("Servease Register");
        setSize(420, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== BACKGROUND =====
        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(245,245,245));

        // ===== CARD =====
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(320, 480));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // ===== TITLE =====
        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== FIELDS =====
        JTextField name = createField("Full Name");
        JTextField email = createField("Email");

        JPasswordField pass = new JPasswordField();
        pass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        pass.setBorder(BorderFactory.createTitledBorder("Password"));

        JTextField phone = createField("Phone");

        JComboBox<String> role = new JComboBox<>(new String[]{"USER","PROVIDER"});
        role.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        role.setBorder(BorderFactory.createTitledBorder("Select Role"));

        // ===== BUTTON =====
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(33,150,243));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== LOGIN LINK =====
        JButton loginBtn = new JButton("Already have account? Login");
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setForeground(new Color(33,150,243));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== ADD COMPONENTS =====
        card.add(title);
        card.add(Box.createVerticalStrut(20));
        card.add(name);
        card.add(Box.createVerticalStrut(12));
        card.add(email);
        card.add(Box.createVerticalStrut(12));
        card.add(pass);
        card.add(Box.createVerticalStrut(12));
        card.add(phone);
        card.add(Box.createVerticalStrut(12));
        card.add(role);
        card.add(Box.createVerticalStrut(20));
        card.add(registerBtn);
        card.add(Box.createVerticalStrut(10));
        card.add(loginBtn);

        bg.add(card);
        add(bg);

        // ===== ACTION =====
        registerBtn.addActionListener(e -> {

            String n = name.getText();
            String em = email.getText();
            String p = new String(pass.getPassword());
            String ph = phone.getText();
            String r = (String) role.getSelectedItem();

            if(n.isEmpty() || em.isEmpty() || p.isEmpty()){
                JOptionPane.showMessageDialog(this,"Fill all fields");
                return;
            }

            if(!em.contains("@")){
                JOptionPane.showMessageDialog(this,"Invalid Email");
                return;
            }

            if(p.length() < 4){
                JOptionPane.showMessageDialog(this,"Password too short");
                return;
            }

            new UserController().registerUser(n, em, p, ph, r);

            JOptionPane.showMessageDialog(this,"Registration Successful");

            dispose();
            new LoginFrame();
        });

        loginBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    // ===== REUSABLE FIELD =====
    private JTextField createField(String title){
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createTitledBorder(title));
        return field;
    }
}