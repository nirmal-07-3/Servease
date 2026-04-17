package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AddServiceFrame extends JFrame {

    private User user;
    private ProviderServicesFrame parent;

    private JTextField nameField, priceField;
    private JTextArea descField;
    private JLabel imagePathLabel;

    public AddServiceFrame(User user, ProviderServicesFrame parent) {

        this.user = user;
        this.parent = parent;

        setTitle("Add Service");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        main.setBackground(Color.WHITE);

        // ===== TITLE =====
        JLabel title = new JLabel("Add New Service");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ===== NAME =====
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        nameField.setBorder(BorderFactory.createTitledBorder("Service Name"));

        // ===== PRICE =====
        priceField = new JTextField();
        priceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        priceField.setBorder(BorderFactory.createTitledBorder("Price"));

        // ===== DESCRIPTION =====
        descField = new JTextArea(4, 20);
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descField);
        descScroll.setBorder(BorderFactory.createTitledBorder("Description"));

        // ===== IMAGE UPLOAD =====
        JButton uploadBtn = new JButton("Upload Image");
        imagePathLabel = new JLabel("No file selected");

        uploadBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(this);

            if (res == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                imagePathLabel.setText(file.getAbsolutePath());
            }
        });

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(uploadBtn, BorderLayout.WEST);
        imagePanel.add(imagePathLabel, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JButton addBtn = new JButton("Add Service");
        addBtn.setBackground(new Color(33,150,243));
        addBtn.setForeground(Color.WHITE);

        JButton cancelBtn = new JButton("Cancel");

        JPanel btnPanel = new JPanel(new GridLayout(1,2,10,10));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(cancelBtn);
        btnPanel.add(addBtn);

        // ===== ADD COMPONENTS =====
        main.add(title);
        main.add(Box.createVerticalStrut(15));
        main.add(nameField);
        main.add(Box.createVerticalStrut(10));
        main.add(priceField);
        main.add(Box.createVerticalStrut(10));
        main.add(descScroll);
        main.add(Box.createVerticalStrut(10));
        main.add(imagePanel);
        main.add(Box.createVerticalStrut(20));
        main.add(btnPanel);

        add(main, BorderLayout.CENTER);

        // ===== ACTIONS =====

        cancelBtn.addActionListener(e -> dispose());

        addBtn.addActionListener(e -> {

            String name = nameField.getText();
            String desc = descField.getText();
            String priceText = priceField.getText();
            String imagePath = imagePathLabel.getText();

            if (name.isEmpty() || desc.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields!");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceText);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid price!");
                return;
            }

            // fallback image
            if (imagePath.equals("No file selected")) {
                imagePath = "";
            }

            Service service = new Service(
                    0,
                    user.getId(),
                    name,
                    desc,
                    price,
                    imagePath // IMPORTANT
            );

            ServiceController controller = new ServiceController();
            boolean result = controller.addService(service);

            if (result) {
                JOptionPane.showMessageDialog(this, "Service Added!");

                if (parent != null) {
                    parent.loadServices();
                }

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed!");
            }
        });

        setVisible(true);
    }
}