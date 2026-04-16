package com.servease.ui.components;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    public CardPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));
    }
}