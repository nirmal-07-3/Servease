package com.servease.ui.auth;

import com.servease.controller.UserController;
import com.servease.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class RegisterFrame extends JFrame
{
    JTextField nameField, emailField, phoneField;
    JPasswordField passwordField;
    JComboBox<String> roleBox;
    JButton registerButton;

    public RegisterFrame (){

        setTitle("User Registration");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // All Details

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 150, 30);
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 100, 150, 30);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 150, 30);
        add(passwordField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 250, 100, 30);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(150, 250, 150, 30);
        add(phoneField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 200, 100, 30);
        add(roleLabel);

        roleBox = new JComboBox<>(new String[]{"USER", "PROVIDER"});
        roleBox.setBounds(150, 200, 150, 30);
        add(roleBox);



        //Button

        registerButton = new JButton("Register");
        registerButton.setBounds(150, 300, 100, 30);
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String phone = phoneField.getText();
                String role = roleBox.getSelectedItem().toString();


                UserController controller = new UserController();
                boolean result =controller.registerUser(name, email, password, phone,role);

                if(result){
                    JOptionPane.showMessageDialog(null,"Registered Successfully!");

                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    phoneField.setText("");
                }
                else{
                    JOptionPane.showMessageDialog(null,"Registration Failed!");

                }
            }
        });

        setVisible(true);
    }
}


