package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddServiceFrame extends JFrame {

    JTextField nameField, priceField;
    JTextArea descriptionArea;
    JButton addButton;

    int providerId = 1; // 🔥 TEMP (later dynamic)

    public AddServiceFrame() {

        setTitle("Add Service");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLabel = new JLabel("Service Name:");
        nameLabel.setBounds(50, 50, 120, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(180, 50, 150, 30);
        add(nameField);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setBounds(50, 100, 150, 30);
        add(descLabel);

        descriptionArea = new JTextArea();
        descriptionArea.setBounds(180, 100, 150, 80);
        add(descriptionArea);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 200, 120, 30);
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(180, 200, 150, 30);
        add(priceField);

        addButton = new JButton("Add Service");
        addButton.setBounds(130, 270, 140, 30);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String name = nameField.getText();
                String description = descriptionArea.getText();

                double price = 0;
                try {
                    price = Double.parseDouble(priceField.getText());
                } catch (Exception ex) {
                    System.out.println("Invalid price!");
                    return;
                }

                ServiceController controller = new ServiceController();
                boolean result =controller.addService(providerId, name, description, price);

                if(result){

                    JOptionPane.showMessageDialog(null,"Service Added Successfully!");
                    nameField.setText("");
                    descriptionArea.setText("");
                    priceField.setText("");
                }
                else{
                    JOptionPane.showMessageDialog(null,"Failed to Add Service Please Check your Details!");
                }

            }
        });

        setVisible(true);
    }
}