package com.servease.ui.components;

import com.servease.model.Service;

import javax.swing.*;
import java.awt.*;

public class ServiceCard extends JPanel {

    public ServiceCard(Service service, Runnable onClick) {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));
        setPreferredSize(new Dimension(250,180));

        // ===== IMAGE =====
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(250,100));

        try {
            ImageIcon icon = new ImageIcon(service.getImagePath());
            Image img = icon.getImage().getScaledInstance(250,100,Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setText("No Image");
        }

        // ===== DETAILS =====
        JLabel name = new JLabel(service.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel price = new JLabel("₹ " + service.getPrice());
        price.setForeground(new Color(33,150,243));

        JPanel bottom = new JPanel(new GridLayout(2,1));
        bottom.setBackground(Color.WHITE);
        bottom.add(name);
        bottom.add(price);

        add(imageLabel, BorderLayout.NORTH);
        add(bottom, BorderLayout.CENTER);

        // ===== CLICK =====
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onClick.run();
            }
        });
    }
}