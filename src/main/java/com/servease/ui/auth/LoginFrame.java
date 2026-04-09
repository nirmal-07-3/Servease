package com.servease.ui.auth;

import com.servease.controller.UserController;
import com.servease.model.User;
import com.servease.ui.dashboard.AdminDashboard;
import com.servease.ui.dashboard.ProviderDashboard;
import com.servease.ui.dashboard.UserDashboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton;

    public LoginFrame() {

        setTitle("Login");
        setSize(300, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 30, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(120, 30, 130, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 80, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 80, 130, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 130, 80, 30);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                UserController controller = new UserController();
                User user = controller.loginUser(email, password);

                if(user!=null){
                    JOptionPane.showMessageDialog(null,"Login Successful!");

                    dispose();

                    if(user.getRole().equals("USER")){
                        new UserDashboard(user);
                    } else if (user.getRole().equals("PROVIDER")) {
                        new ProviderDashboard(user);
                    }
                    else {
                        new AdminDashboard(user);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Invalid Credentials!");
                }

            }


        });
            setVisible(true);
    }
}