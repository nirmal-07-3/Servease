package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.ui.dashboard.ProviderServicesFrame;

import javax.swing.*;

public class UpdateServiceFrame extends JFrame {

    private Service service;
    private ProviderServicesFrame parent;

    private JTextField nameField,descriptionField, priceField;

    public UpdateServiceFrame(Service service, ProviderServicesFrame parent) {

        this.service = service;
        this.parent = parent;

        setTitle("Update Service");
        setSize(400, 250);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        add(nameLabel);

        JLabel descriptionLabel=new JLabel("Description");
        descriptionLabel.setBounds(30,50,100,25);
        add(descriptionLabel);

        nameField = new JTextField(service.getName());
        nameField.setBounds(130, 30, 200, 25);
        add(nameField);

        descriptionField = new JTextField(service.getDescription());
        descriptionField.setBounds(130, 50, 200, 25);
        add(descriptionField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 80, 100, 25);
        add(priceLabel);

        priceField = new JTextField(String.valueOf(service.getPrice()));
        priceField.setBounds(130, 80, 200, 25);
        add(priceField);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(130, 140, 120, 30);
        add(updateBtn);

        updateBtn.addActionListener(e -> {

            service.setName(nameField.getText());
            service.setDescription(descriptionField.getText());
            service.setPrice(Double.parseDouble(priceField.getText()));

            ServiceController controller = new ServiceController();
            boolean result = controller.updateService(service);

            if (result) {
                JOptionPane.showMessageDialog(this, "Updated Successfully");

                parent.loadServices(); // 🔥 refresh

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Update Failed");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}