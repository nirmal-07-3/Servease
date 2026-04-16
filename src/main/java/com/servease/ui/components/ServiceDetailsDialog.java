package com.servease.ui.components;

import javax.swing.*;
import java.awt.*;

public class ServiceDetailsDialog extends JDialog {

    public ServiceDetailsDialog(JFrame parent, String name, String desc, double price) {

        super(parent, "Service Details", true);

        setSize(400, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        main.setBackground(Color.WHITE);

        JLabel image = new JLabel("Image", SwingConstants.CENTER);
        image.setPreferredSize(new Dimension(300,120));
        image.setOpaque(true);
        image.setBackground(new Color(230,230,230));

        JLabel title = new JLabel(name);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel description = new JLabel("<html>" + desc + "</html>");
        description.setForeground(Color.GRAY);

        JLabel priceLbl = new JLabel("₹ " + price);
        priceLbl.setFont(new Font("Arial", Font.BOLD, 16));

        JButton bookBtn = new JButton("Book Now");
        bookBtn.setBackground(new Color(33,150,243));
        bookBtn.setForeground(Color.WHITE);

        bookBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Service Booked Successfully!");
            dispose();
        });

        main.add(image);
        main.add(Box.createVerticalStrut(15));
        main.add(title);
        main.add(Box.createVerticalStrut(10));
        main.add(description);
        main.add(Box.createVerticalStrut(10));
        main.add(priceLbl);
        main.add(Box.createVerticalStrut(20));
        main.add(bookBtn);

        add(main, BorderLayout.CENTER);

        setVisible(true);
    }
}