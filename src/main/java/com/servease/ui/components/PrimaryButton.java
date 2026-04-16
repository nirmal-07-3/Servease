package com.servease.ui.components;

import javax.swing.*;
import java.awt.*;

public class PrimaryButton extends JButton {

    public PrimaryButton(String text) {
        super(text);

        setBackground(new Color(33,150,243));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setFont(new Font("Arial", Font.BOLD, 13));
        setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}