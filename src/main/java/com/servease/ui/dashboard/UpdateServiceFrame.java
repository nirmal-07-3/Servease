package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class UpdateServiceFrame extends JDialog {

    private JTextField nameField, priceField;
    private JTextArea descArea;
    private JLabel imagePreview;

    private Service service;
    private ServiceController controller;

    public UpdateServiceFrame(JFrame parent, Service service) {
        super(parent, "Update Service", true);

        this.service = service;
        this.controller = new ServiceController();

        setSize(420, 520);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.setBackground(Color.WHITE);

        // ===== TITLE =====
        JLabel title = new JLabel("Update Service");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== FIELDS =====
        nameField = new JTextField(service.getName());
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        nameField.setBorder(BorderFactory.createTitledBorder("Service Name"));

        descArea = new JTextArea(service.getDescription());
        descArea.setBorder(BorderFactory.createTitledBorder("Description"));
        descArea.setLineWrap(true);
        descArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        priceField = new JTextField(String.valueOf(service.getPrice()));
        priceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        priceField.setBorder(BorderFactory.createTitledBorder("Price"));

        // ===== IMAGE PREVIEW BOX =====
        imagePreview = new JLabel("Upload Image", SwingConstants.CENTER);
        imagePreview.setPreferredSize(new Dimension(120,120));
        imagePreview.setMaximumSize(new Dimension(120,120));
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePreview.setOpaque(true);
        imagePreview.setBackground(new Color(240,240,240));
        imagePreview.setAlignmentX(Component.CENTER_ALIGNMENT);

        // LOAD EXISTING IMAGE
        if(service.getImagePath() != null && !service.getImagePath().isEmpty()){
            ImageIcon icon = new ImageIcon(service.getImagePath());
            Image img = icon.getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH);
            imagePreview.setText("");
            imagePreview.setIcon(new ImageIcon(img));
        }

        // ===== UPLOAD BUTTON =====
        JButton uploadBtn = new JButton("Choose Image");
        uploadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        uploadBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);

            if(result == JFileChooser.APPROVE_OPTION){
                File file = chooser.getSelectedFile();
                service.setImagePath(file.getAbsolutePath());

                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH);
                imagePreview.setText("");
                imagePreview.setIcon(new ImageIcon(img));
            }
        });

        // ===== BUTTON =====
        JButton updateBtn = new JButton("Update Service");
        updateBtn.setBackground(new Color(33,150,243));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFocusPainted(false);
        updateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        updateBtn.addActionListener(e -> {
            try {
                service.setName(nameField.getText());
                service.setDescription(descArea.getText());
                service.setPrice(Double.parseDouble(priceField.getText()));

                boolean result = controller.updateService(service);

                if(result){
                    JOptionPane.showMessageDialog(this, "Service Updated Successfully");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Update Failed");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
            }
        });

        // ===== ADD COMPONENTS =====
        panel.add(title);
        panel.add(Box.createVerticalStrut(15));

        panel.add(nameField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(priceField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(descArea);
        panel.add(Box.createVerticalStrut(15));

        panel.add(imagePreview);
        panel.add(Box.createVerticalStrut(10));

        panel.add(uploadBtn);
        panel.add(Box.createVerticalStrut(20));

        panel.add(updateBtn);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}