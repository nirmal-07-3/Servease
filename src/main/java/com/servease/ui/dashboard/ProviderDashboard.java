package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.controller.BookingController;
import com.servease.controller.ServiceController;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProviderDashboard extends JFrame {

    private User user;
    private JPanel content;
    private JButton activeBtn;

    public ProviderDashboard(User user) {
        this.user = user;

        setTitle("Servease Provider");
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
        JButton servicesBtn = createMenuBtn("My Services", "icons/service.png");
        JButton bookingBtn = createMenuBtn("Bookings", "icons/booking.png");
        JButton reviewBtn = createMenuBtn("Reviews", "icons/rating.png");
        JButton notifyBtn = createMenuBtn("Notifications", "icons/notification.png");
        JButton settingsBtn = createMenuBtn("Settings", "icons/settings.png");
        JButton logoutBtn = createMenuBtn("Logout", "icons/logout.png");

        sidebar.add(logo);
        sidebar.add(dashboardBtn);
        sidebar.add(servicesBtn);
        sidebar.add(bookingBtn);
        sidebar.add(reviewBtn);
        sidebar.add(notifyBtn);
        sidebar.add(settingsBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // ===== CONTENT =====
        content = new JPanel(new BorderLayout());
        content.setBackground(new Color(245, 247, 250));
        loadPanel(createDashboardPanel());

        // ===== NAVIGATION =====
        dashboardBtn.addActionListener(e -> switchPanel(dashboardBtn, createDashboardPanel()));
        servicesBtn.addActionListener(e -> switchPanel(servicesBtn, new ProviderServicesPanel(user)));
        bookingBtn.addActionListener(e -> switchPanel(bookingBtn, new ProviderBookingsPanel(user)));
        reviewBtn.addActionListener(e -> switchPanel(reviewBtn, new ProviderReviewsPanel(user)));
        notifyBtn.addActionListener(e -> switchPanel(notifyBtn, new NotificationPanel(user.getId())));
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

        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(new Color(245, 247, 250));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        BookingController bookingController = new BookingController();
        ServiceController serviceController = new ServiceController();

        List<Object[]> bookings = bookingController.getBookingsByProvider(user.getId());

        int totalBookings = bookings.size();
        int completed = 0;
        double earnings = 0;

        for (Object[] b : bookings) {
            String status = b[5].toString();
            double price = (double) b[4];

            if ("Completed".equalsIgnoreCase(status)) {
                completed++;
                earnings += price;
            }
        }

        int totalServices = serviceController.getServicesByProviderId(user.getId()).size();

        // ===== TOP BAR =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));


        JLabel userLabel = new JLabel(" " + user.getName());
        try {
            java.net.URL url = getClass().getClassLoader().getResource("icons/user.png");
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                userLabel.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
            System.out.println("User icon missing");
        }


        top.add(userLabel, BorderLayout.EAST);

        // ===== CARDS =====
        JPanel cards = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        cards.setBackground(new Color(245,247,250));

        cards.add(createCard("icons/service.png", String.valueOf(totalServices), "Total Services"));
        cards.add(createCard("icons/booking.png", String.valueOf(totalBookings), "Total Bookings"));
        cards.add(createCard("icons/completed.png", String.valueOf(completed), "Completed"));
        cards.add(createCard("icons/money.png", "₹ " + earnings, "Earnings"));

        // ===== LOWER =====
        JPanel lower = new JPanel(new GridLayout(1, 2, 20, 0));
        lower.setBackground(new Color(245,247,250));

        lower.add(createRecentBookings(bookings));
        lower.add(createNotifications());

        main.add(top, BorderLayout.NORTH);
        main.add(cards, BorderLayout.CENTER);
        main.add(lower, BorderLayout.SOUTH);

        return main;
    }

    // ===== CARD =====
    private JPanel createCard(String iconPath, String value, String label) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(220, 90));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        JLabel iconLabel = new JLabel();
        try {
            java.net.URL url = getClass().getClassLoader().getResource(iconPath);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {}

        JPanel iconBox = new JPanel();
        iconBox.setBackground(new Color(240,245,255));
        iconBox.setPreferredSize(new Dimension(50,50));
        iconBox.add(iconLabel);

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBackground(Color.WHITE);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.GRAY);

        text.add(val);
        text.add(lbl);

        card.add(iconBox, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);

        return card;
    }

    // ===== RECENT BOOKINGS (IMPROVED UI) =====
    private JPanel createRecentBookings(List<Object[]> bookings) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Recent Bookings"));

        int count = 0;

        for (Object[] b : bookings) {
            if (count >= 5) break;

            String service = b[2].toString();
            String userName = b[1].toString();
            String status = b[5].toString();

            JPanel row = new JPanel(new BorderLayout());
            row.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            row.setBackground(Color.WHITE);

            JLabel name = new JLabel(service + " - " + userName);

            JLabel statusLbl = new JLabel(status);
            if (status.equalsIgnoreCase("Completed"))
                statusLbl.setForeground(new Color(76,175,80));
            else if (status.equalsIgnoreCase("Pending"))
                statusLbl.setForeground(new Color(255,152,0));
            else
                statusLbl.setForeground(Color.RED);

            row.add(name, BorderLayout.WEST);
            row.add(statusLbl, BorderLayout.EAST);

            panel.add(row);
            count++;
        }

        return panel;
    }

    // ===== NOTIFICATIONS (IMPROVED) =====
    private JPanel createNotifications() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Notifications"));

        panel.add(createNotifyItem("New Booking Received"));
        panel.add(createNotifyItem("Service Completed"));
        panel.add(createNotifyItem("New Review Added"));

        return panel;
    }

    private JPanel createNotifyItem(String text) {

        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel lbl = new JLabel("• " + text);

        item.add(lbl, BorderLayout.WEST);

        return item;
    }
}