package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.model.Bookings;
import com.servease.controller.BookingController;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

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
        sidebar.setPreferredSize(new Dimension(220, getHeight()));

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
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        setVisible(true);
    }

    // ===== SWITCH PANEL =====
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
            java.net.URL url = getClass().getClassLoader().getResource(iconPath);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
            System.out.println("Icon missing: " + iconPath);
        }

        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setBackground(new Color(18, 52, 86));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        return btn;
    }

    // ===== DASHBOARD =====
    private JPanel createDashboardPanel() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(245, 247, 250));

        // ===== FETCH DATA =====
        BookingController controller = new BookingController();
        List<Bookings> bookings = controller.getBookingsByUser(user.getId());

        int total = bookings.size();
        int completed = 0;

        Set<String> services = new HashSet<>();

        for (Bookings b : bookings) {
            services.add(b.getServiceName());
            if ("Completed".equalsIgnoreCase(b.getStatus())) {
                completed++;
            }
        }

        int servicesUsed = services.size();
        double rating = 4.5;

        // ===== TOP BAR =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(300, 35));
        search.setBorder(BorderFactory.createTitledBorder("Search services"));

        JLabel userLabel = new JLabel(" " + user.getName());

        try {
            java.net.URL url = getClass().getClassLoader().getResource("icons/user.png");
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                userLabel.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
            System.out.println("User icon missing");
        }

        top.add(search, BorderLayout.WEST);
        top.add(userLabel, BorderLayout.EAST);

        // ===== CARDS =====
        JPanel cards = createCardsGrid(total, servicesUsed, completed, rating);

        // ===== LOWER =====
        JPanel lower = new JPanel(new GridLayout(1, 4, 25, 0));
        lower.setBorder(BorderFactory.createEmptyBorder(30, 40, 10, 40));
        lower.setBackground(new Color(245, 247, 250));

        lower.add(createRecentBookings(bookings));
        lower.add(createNotifications());

        main.add(top, BorderLayout.NORTH);
        main.add(cards, BorderLayout.CENTER);
        main.add(lower, BorderLayout.SOUTH);

        return main;
    }

    // ===== GRID CARDS =====
    private JPanel createCardsGrid(int total, int services, int completed, double rating) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245,247,250));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;

        gbc.gridx = 0;
        panel.add(createCard("icons/booking.png", String.valueOf(total), "Bookings Made"), gbc);

        gbc.gridx = 1;
        panel.add(createCard("icons/service.png", String.valueOf(services), "Services Used"), gbc);

        gbc.gridx = 2;
        panel.add(createCard("icons/completed.png", String.valueOf(completed), "Completed"), gbc);

        gbc.gridx = 3;
        panel.add(createCard("icons/rating.png", String.valueOf(rating), "Avg Rating"), gbc);

        return panel;
    }

    // ===== CARD =====
    private JPanel createCard(String iconPath, String value, String label) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(260, 90)); // wide, not tall

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        // ===== LEFT (ICON) =====
        JLabel icon = new JLabel();
        try {
            java.net.URL url = getClass().getClassLoader().getResource(iconPath);
            if (url != null) {
                ImageIcon i = new ImageIcon(url);
                Image img = i.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
                icon.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
            System.out.println("Icon error: " + iconPath);
        }

        JPanel left = new JPanel();
        left.setBackground(new Color(240,245,255)); // soft bg like figma
        left.setPreferredSize(new Dimension(50,50));
        left.add(icon);

        // ===== RIGHT (TEXT) =====
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(Color.WHITE);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.GRAY);

        right.add(val);
        right.add(lbl);

        // ===== ADD =====
        card.add(left, BorderLayout.WEST);
        card.add(right, BorderLayout.CENTER);

        return card;
    }

    // ===== RECENT BOOKINGS =====
    private JPanel createRecentBookings(List<Bookings> bookings) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Recent Bookings"));

        int count = 0;

        for (Bookings b : bookings) {
            if (count >= 5) break;

            Color color = new Color(255,152,0);

            if ("Completed".equalsIgnoreCase(b.getStatus())) {
                color = new Color(76,175,80);
            }

            panel.add(createRow(b.getServiceName(), b.getStatus(), color));
            count++;
        }

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
        panel.add(new JLabel("✔ New Offer"));

        return panel;
    }

    private JPanel createNotificationPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("All Notifications"));
        return panel;
    }
}