package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardHome extends JPanel {

    private User user;

    public DashboardHome(User user) {
        this.user = user;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // ===== MAIN CONTAINER =====
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(new Color(245, 245, 245));

        // ===== CARDS SECTION =====
        JPanel cards = new JPanel(new GridLayout(1, 4, 20, 20));
        cards.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        cards.setBackground(new Color(245, 245, 245));

        ServiceController controller = new ServiceController();
        List<Service> services = controller.getServicesByProviderId(user.getId());

        cards.add(createCard(String.valueOf(services.size()), "Services"));
        cards.add(createCard("67", "Bookings"));   // can connect later
        cards.add(createCard("4.7", "Rating"));
        cards.add(createCard("₹4280", "Revenue"));

        // ===== TITLE =====
        JLabel title = new JLabel("Dashboard Overview");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // ===== ADD TO MAIN =====
        main.add(cards);
        main.add(title);

        add(main, BorderLayout.CENTER);
    }

    // ===== CARD UI =====
    private JPanel createCard(String value, String label) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(20,20,20,20)
        ));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel txt = new JLabel(label);
        txt.setForeground(Color.GRAY);

        panel.add(val, BorderLayout.CENTER);
        panel.add(txt, BorderLayout.SOUTH);

        return panel;
    }
}