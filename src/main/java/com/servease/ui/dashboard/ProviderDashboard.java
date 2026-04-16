package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.controller.ServiceController;
import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class ProviderDashboard extends JFrame {

    private User user;

    public ProviderDashboard(User user) {

        this.user = user;

        setTitle("Servease - Provider Dashboard");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =======================
        // 🔹 SIDEBAR
        // =======================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(33, 150, 243));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));

        JLabel logo = new JLabel("Servease");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 22));
        logo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton dashboardBtn = createSidebarButton("Dashboard");
        JButton servicesBtn = createSidebarButton("My Services");
        JButton bookingBtn = createSidebarButton("Bookings");
        JButton logoutBtn = createSidebarButton("Logout");

        sidebar.add(logo);
        sidebar.add(dashboardBtn);
        sidebar.add(servicesBtn);
        sidebar.add(bookingBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // =======================
        // 🔹 HEADER
        // =======================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Provider Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel welcome = new JLabel("Welcome, " + user.getName());
        welcome.setFont(new Font("Arial", Font.PLAIN, 14));

        header.add(title, BorderLayout.WEST);
        header.add(welcome, BorderLayout.EAST);

        // =======================
        // 🔹 CONTENT
        // =======================
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(245, 245, 245));

        // 🔥 REAL DATA CONTROLLERS
        ServiceController serviceController = new ServiceController();
        BookingController bookingController = new BookingController();

        int totalServices = serviceController.getServicesByProviderId(user.getId()).size();
        int totalBookings = bookingController.getBookingsByProvider(user.getId()).size();

        // =======================
        // 🔹 CARDS
        // =======================
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 20, 20));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        cardsPanel.setBackground(new Color(245, 245, 245));

        cardsPanel.add(createCard("Total Services", String.valueOf(totalServices)));
        cardsPanel.add(createCard("Bookings", String.valueOf(totalBookings)));
        cardsPanel.add(createCard("Rating", "4.7"));     // dummy
        cardsPanel.add(createCard("Revenue", "₹4280"));  // dummy

        // =======================
        // 🔹 CENTER BUTTON PANEL
        // =======================
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(245, 245, 245));

        JButton openServicesBtn = new JButton("Manage My Services");
        openServicesBtn.setFont(new Font("Arial", Font.BOLD, 16));
        openServicesBtn.setBackground(new Color(41, 128, 185));
        openServicesBtn.setForeground(Color.WHITE);

        JButton openBookingsBtn = new JButton("View Bookings");
        openBookingsBtn.setFont(new Font("Arial", Font.BOLD, 16));
        openBookingsBtn.setBackground(new Color(46, 204, 113));
        openBookingsBtn.setForeground(Color.WHITE);

        centerPanel.add(openServicesBtn);
        centerPanel.add(openBookingsBtn);

        // ADD TO CONTENT
        content.add(cardsPanel, BorderLayout.NORTH);
        content.add(centerPanel, BorderLayout.CENTER);

        // =======================
        // 🔥 ADD TO FRAME
        // =======================
        add(sidebar, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);

        // =======================
        // 🔥 ACTIONS
        // =======================

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Logout?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });

        servicesBtn.addActionListener(e -> new ProviderServicesFrame(user));
        bookingBtn.addActionListener(e -> new ProviderBookingsFrame(user));

        openServicesBtn.addActionListener(e -> new ProviderServicesFrame(user));
        openBookingsBtn.addActionListener(e -> new ProviderBookingsFrame(user));

        setVisible(true);
    }

    // 🔥 SIDEBAR BUTTON STYLE
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(33, 150, 243));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    // 🔥 CARD COMPONENT
    private JPanel createCard(String title, String value) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 22));

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);

        return card;
    }
}