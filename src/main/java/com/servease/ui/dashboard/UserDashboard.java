package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {

    private User user;
    private JPanel content;
    private JButton activeBtn;

    public UserDashboard(User user) {
        this.user = user;

        setTitle("Servease");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(18, 52, 86));
        sidebar.setPreferredSize(new Dimension(230, getHeight()));

        JLabel logo = new JLabel("Servease");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));

        JButton dashboardBtn = createMenuBtn("Dashboard", "icons/dashboard.png");
        JButton browseBtn = createMenuBtn("Browse Services", "icons/browse.png");
        JButton bookingBtn = createMenuBtn("My Bookings", "icons/booking.png");
        JButton notifyBtn = createMenuBtn("Notifications", "icons/notification.png");
        JButton settingsBtn = createMenuBtn("Settings", "icons/settings.png");
        JButton logoutBtn = createMenuBtn("Logout", "icons/logout.png");



        sidebar.add(logo);
        sidebar.add(dashboardBtn);
        sidebar.add(browseBtn);
        sidebar.add(bookingBtn);
        sidebar.add(notifyBtn);
        sidebar.add(settingsBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // ===== CONTENT =====
        content = new JPanel(new BorderLayout());
        content.setBackground(new Color(245, 247, 250));

        loadPanel(createDashboardPanel());

        // ===== ACTIONS =====
        dashboardBtn.addActionListener(e -> switchPanel(dashboardBtn, createDashboardPanel()));
        browseBtn.addActionListener(e -> switchPanel(browseBtn, new BrowseServicesPanel(user)));
        bookingBtn.addActionListener(e -> switchPanel(bookingBtn, new UserBookingsFrame(user)));
        notifyBtn.addActionListener(e -> switchPanel(notifyBtn, createNotificationPanel()));
        settingsBtn.addActionListener(e -> switchPanel(settingsBtn, new SettingsPanel(user)));

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Logout from Servease?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        setVisible(true);
    }

    // ===== SWITCH PANEL + ACTIVE BUTTON =====
    private void switchPanel(JButton btn, JPanel panel) {
        if (activeBtn != null) {
            activeBtn.setBackground(new Color(18, 52, 86));
        }
        btn.setBackground(new Color(33, 150, 243));
        activeBtn = btn;

        loadPanel(panel);
    }

    private void loadPanel(JPanel panel) {
        content.removeAll();
        content.add(panel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    // ===== MENU BUTTON =====
    private JButton createMenuBtn(String text, String iconPath) {
        JButton btn = new JButton(text);

        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(img));
        } catch (Exception ignored) {}

        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setBackground(new Color(18, 52, 86));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn != activeBtn)
                    btn.setBackground(new Color(30, 70, 120));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn != activeBtn)
                    btn.setBackground(new Color(18, 52, 86));
            }
        });

        return btn;
    }

    // ===== DASHBOARD =====
    private JPanel createDashboardPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(245, 247, 250));

        // TOP BAR
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(300, 35));
        search.setBorder(BorderFactory.createTitledBorder("Search services"));

        JLabel userLabel = new JLabel(" " + user.getName());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icons/user.png"));
            Image img = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            userLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("User icon not found");
        }
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        top.add(search, BorderLayout.WEST);
        top.add(userLabel, BorderLayout.EAST);

        // CARDS
        JPanel cardsWrapper = new JPanel();
        cardsWrapper.setLayout(new BorderLayout());
        cardsWrapper.setBackground(new Color(245, 247, 250));
        cardsWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel cards = new JPanel(new GridLayout(1,4,20,0));
        cards.setBackground(new Color(245, 247, 250));

        // Bigger cards
        cards.add(createCard("12", "Bookings Made", "icons/booking.png"));
        cards.add(createCard("8", "Services Used", "icons/service.png"));
        cards.add(createCard("10", "Completed", "icons/completed.png"));
        cards.add(createCard("4.7", "Avg. Rating Given", "icons/rating.png"));

        cardsWrapper.add(cards, BorderLayout.CENTER);

        // LOWER
        JPanel lower = new JPanel(new GridLayout(1, 2, 20, 20));
        lower.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        lower.setBackground(new Color(245, 247, 250));

        lower.add(createRecentBookings());
        lower.add(createNotifications());

        main.add(top, BorderLayout.NORTH);
        main.add(cardsWrapper, BorderLayout.CENTER);
        main.add(lower, BorderLayout.SOUTH);

        return main;
    }

    // ===== CARD =====
    private JPanel createCard(String value, String label, String iconPath) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 🔥 IMPORTANT: CONTROL HEIGHT
        card.setPreferredSize(new Dimension(200, 140)); // width, height
        card.setMaximumSize(new Dimension(250, 150));   // prevents stretching

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // ===== ICON =====
        JLabel iconLabel = new JLabel();
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(img));
        } catch (Exception ignored) {}

        // ===== VALUE =====
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 22));
        val.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== LABEL =====
        JLabel txt = new JLabel(label);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt.setForeground(Color.GRAY);
        txt.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== LAYOUT =====
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(val);
        card.add(Box.createVerticalStrut(4));
        card.add(txt);

        return card;
    }

    // ===== BOOKINGS =====
    private JPanel createRecentBookings() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Recent Bookings"));

        panel.add(createRow("Home Cleaning", "Completed", new Color(76,175,80)));
        panel.add(createRow("Plumbing Repair", "Pending", new Color(255,152,0)));

        return panel;
    }

    private JPanel createRow(String name, String status, Color color) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel n = new JLabel(name);
        JLabel s = new JLabel(status);
        s.setForeground(color);

        row.add(n, BorderLayout.WEST);
        row.add(s, BorderLayout.EAST);

        return row;
    }

    // ===== NOTIFICATIONS =====
    private JPanel createNotifications() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Notifications"));

        panel.add(new JLabel("✔ Booking Confirmed"));
        panel.add(new JLabel("✔ Service Completed"));
        panel.add(new JLabel("✔ New Offer Available"));

        return panel;
    }

    private JPanel createNotificationPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("All Notifications"));
        return panel;
    }
}