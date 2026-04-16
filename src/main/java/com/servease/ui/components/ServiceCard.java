package com.servease.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServiceCard extends CardPanel {

    public ServiceCard(String name, String desc, double price) {

        setLayout(new BorderLayout());

        JLabel title = new JLabel(name);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel priceLbl = new JLabel("₹ " + price);
        priceLbl.setForeground(Color.GRAY);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(Color.WHITE);

        left.add(title);
        left.add(Box.createVerticalStrut(5));
        left.add(priceLbl);

        add(left, BorderLayout.CENTER);

        // 🔥 CLICK → OPEN POPUP
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ServiceDetailsDialog(
                        (JFrame) SwingUtilities.getWindowAncestor(ServiceCard.this),
                        name,
                        desc,
                        price
                );
            }
        });

        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}