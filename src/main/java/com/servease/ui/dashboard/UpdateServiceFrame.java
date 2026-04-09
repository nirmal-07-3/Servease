package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;

import javax.swing.*;

public class UpdateServiceFrame extends JFrame {

    private JTextField nameField, priceField;
    private JTextArea descArea;
    private Service service;

    public UpdateServiceFrame(Service service) {

        this.service = service;

        setTitle("Update Service");
        setSize(400, 300);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        add(nameLabel);

        nameField = new JTextField(service.getName());
        nameField.setBounds(130, 30, 200, 25);
        add(nameField);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setBounds(30, 70, 100, 25);
        add(descLabel);

        descArea = new JTextArea(service.getDescription());
        descArea.setBounds(130, 70, 200, 60);
        add(descArea);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 150, 100, 25);
        add(priceLabel);

        priceField = new JTextField(String.valueOf(service.getPrice()));
        priceField.setBounds(130, 150, 200, 25);
        add(priceField);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(130, 200, 120, 30);
        add(updateBtn);

        // 🔥 UPDATE LOGIC
        updateBtn.addActionListener(e -> {

            String name = nameField.getText();
            String desc = descArea.getText();
            double price = Double.parseDouble(priceField.getText());

            service.setName(name);
            service.setDescription(desc);
            service.setPrice(price);

            ServiceController controller = new ServiceController();

            boolean result = controller.updateService(service);

            if (result) {
                JOptionPane.showMessageDialog(this, "Updated Successfully");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Update Failed");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}