package com.servease.ui.components;

import javax.swing.*;
import java.awt.*;

public class StatCard extends JPanel {

    public StatCard(String value, String label) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 100));
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.GRAY);

        add(val);
        add(Box.createVerticalStrut(5));
        add(lbl);
    }
}