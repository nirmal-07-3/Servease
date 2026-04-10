package com.servease.ui.dashboard;


import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;
import com.servease.ui.dashboard.ProviderServicesFrame;

import javax.swing.*;

public class AddServiceFrame extends JFrame {

    private User user;
    private ProviderServicesFrame parent;

    private JTextField nameField, priceField;
    private JTextArea descArea;

    public AddServiceFrame(User user, ProviderServicesFrame parent) {

        this.user = user;
        this.parent = parent;

        setTitle("Add Service");
        setSize(400, 300);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(130, 30, 200, 25);
        add(nameField);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setBounds(30, 70, 100, 25);
        add(descLabel);

        descArea = new JTextArea();
        descArea.setBounds(130, 70, 200, 60);
        add(descArea);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 150, 100, 25);
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(130, 150, 200, 25);
        add(priceField);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(130, 200, 120, 30);
        add(addBtn);

        addBtn.addActionListener(e -> {

            String name = nameField.getText();
            String desc = descArea.getText();
            double price = Double.parseDouble(priceField.getText());

            Service service = new Service(
                    user.getId(), name, desc, price
            );

            ServiceController controller = new ServiceController();
            boolean result = controller.addService(service);

            if (result) {
                JOptionPane.showMessageDialog(this, "Service Added");

                parent.loadServices(); // 🔥 refresh

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}