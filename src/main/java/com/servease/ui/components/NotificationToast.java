package com.servease.ui.components;

import javax.swing.*;
import java.awt.*;

public class NotificationToast extends JWindow {

    public NotificationToast(String message) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(40,40,40));
        panel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);

        panel.add(label, BorderLayout.CENTER);

        add(panel);
        pack();

        // bottom-right position
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screen.width - getWidth() - 20, screen.height - getHeight() - 50);

        setVisible(true);

        // auto close after 3 sec
        new Timer(3000, e -> dispose()).start();
    }
}