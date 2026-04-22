package com.servease.ui.dashboard;

import com.servease.config.DBConnection;
import com.servease.controller.AdminDashboardController;
import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDashboard extends JFrame {

    private User user;
    private JPanel content;
    private JButton activeBtn;

    public AdminDashboard(User user) {
        this.user = user;

        setTitle("Servease Admin");
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
        JButton usersBtn = createMenuBtn("Users", "icons/user.png");
        JButton providersBtn = createMenuBtn("Providers", "icons/provider.png");
        JButton servicesBtn = createMenuBtn("Services", "icons/service.png");
        JButton bookingsBtn = createMenuBtn("Bookings", "icons/booking.png");
        JButton settingsBtn = createMenuBtn("Settings", "icons/settings.png");
        JButton logoutBtn = createMenuBtn("Logout", "icons/logout.png");

        sidebar.add(logo);
        sidebar.add(dashboardBtn);
        sidebar.add(usersBtn);
        sidebar.add(providersBtn);
        sidebar.add(servicesBtn);
        sidebar.add(bookingsBtn);
        sidebar.add(settingsBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // ===== CONTENT =====
        content = new JPanel(new BorderLayout());
        content.setBackground(new Color(245, 247, 250));
        loadPanel(createDashboardPanel());

        // ===== NAVIGATION =====
        dashboardBtn.addActionListener(e -> switchPanel(dashboardBtn, createDashboardPanel()));

        usersBtn.addActionListener(e ->
                switchPanel(usersBtn, new AdminUsersPanel())
        );

        providersBtn.addActionListener(e ->
                switchPanel(providersBtn, new AdminProvidersPanel())
        );

        servicesBtn.addActionListener(e ->
                switchPanel(servicesBtn, new AdminServicesPanel())
        );

        bookingsBtn.addActionListener(e ->
                switchPanel(bookingsBtn, new AdminBookingsPanel())
        );

        settingsBtn.addActionListener(e ->
                switchPanel(settingsBtn, new AdminSettingsPanel(user))
        );

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

    // ===== DASHBOARD PANEL =====
    private JPanel createDashboardPanel() {

        JPanel main = new JPanel(new BorderLayout(20,20));
        main.setBackground(new Color(245,247,250));
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // ===== HEADER (TOP BAR WITH PROFILE) =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        // LEFT TITLE
        JLabel welcome = new JLabel("Welcome back, Admin");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // RIGHT PROFILE
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        // ICON
        JLabel icon = new JLabel();
        try {
            java.net.URL url = getClass().getClassLoader().getResource("icons/user.png");
            if (url != null) {
                ImageIcon ic = new ImageIcon(url);
                Image img = ic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                icon.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
            System.out.println("User icon missing");
        }

        // NAME
        JLabel name = new JLabel(user.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // ROLE
        JLabel role = new JLabel("ADMIN");
        role.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        role.setForeground(Color.WHITE);
        role.setOpaque(true);
        role.setBackground(new Color(33,150,243));
        role.setBorder(BorderFactory.createEmptyBorder(2,8,2,8));

        // TEXT BOX
        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setOpaque(false);
        text.add(name);
        text.add(role);

        right.add(icon);
        right.add(text);

        header.add(welcome, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        // ===== CARDS =====
        AdminDashboardController controller = new AdminDashboardController();

        int users = controller.getTotalUsers();
        int providers = controller.getTotalProviders();
        int services = controller.getTotalServices();
        int bookings = controller.getTotalBookings();
        double revenue = controller.getRevenue();

        JPanel cards = new JPanel(new GridLayout(1,5,20,0));
        cards.setBackground(new Color(245,247,250));

        cards.add(createCard("icons/user.png", String.valueOf(users), "Users"));
        cards.add(createCard("icons/provider.png", String.valueOf(providers), "Providers"));
        cards.add(createCard("icons/service.png", String.valueOf(services), "Services"));
        cards.add(createCard("icons/booking.png", String.valueOf(bookings), "Bookings"));
        cards.add(createCard("icons/money.png", "₹ " + revenue, "Revenue"));

        main.add(cards, BorderLayout.CENTER);

        // ===== LOWER =====
        JPanel lower = new JPanel(new GridLayout(1,2,20,0));
        lower.setBackground(new Color(245,247,250));

        lower.add(createRecentBookings());
        lower.add(createRecentProviders());

        main.add(lower, BorderLayout.SOUTH);

        return main;
    }





    // ===== CARD =====
    private JPanel createCard(String iconPath, String value, String label) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(200,100));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        // ICON
        JLabel iconLabel = new JLabel();
        try {
            java.net.URL url = getClass().getClassLoader().getResource(iconPath);
            if (url != null) {
                ImageIcon img = new ImageIcon(url);
                iconLabel.setIcon(new ImageIcon(img.getImage().getScaledInstance(28,28,Image.SCALE_SMOOTH)));
            }
        } catch(Exception e){}

        JPanel iconBox = new JPanel();
        iconBox.setBackground(new Color(240,245,255));
        iconBox.setPreferredSize(new Dimension(50,50));
        iconBox.add(iconLabel);

        // TEXT
        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBackground(Color.WHITE);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.GRAY);

        text.add(val);
        text.add(lbl);

        card.add(iconBox, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);

        return card;
    }

    // ===== RECENT BOOKINGS =====
    private JPanel createRecentBookings() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Recent Bookings"));

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT u.name, s.name, b.status " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id=u.id " +
                    "JOIN services s ON b.service_id=s.id " +
                    "ORDER BY b.id DESC LIMIT 5";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                JPanel row = new JPanel(new BorderLayout());
                row.setBackground(Color.WHITE);
                row.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                JLabel left = new JLabel(
                        rs.getString(1) + " - " + rs.getString(2)
                );

                JLabel status = new JLabel(rs.getString(3));

                if (status.getText().equalsIgnoreCase("Completed"))
                    status.setForeground(new Color(76,175,80));
                else if (status.getText().equalsIgnoreCase("Cancelled"))
                    status.setForeground(Color.RED);
                else
                    status.setForeground(new Color(255,152,0));

                row.add(left, BorderLayout.WEST);
                row.add(status, BorderLayout.EAST);

                panel.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return panel;
    }

    private JPanel createRecentProviders() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Recent Providers"));

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT name, status FROM users WHERE role='PROVIDER' ORDER BY id DESC LIMIT 5";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                JPanel row = new JPanel(new BorderLayout());
                row.setBackground(Color.WHITE);
                row.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                JLabel name = new JLabel(rs.getString(1));

                JLabel status = new JLabel(rs.getString(2));
                status.setForeground(new Color(76,175,80));

                row.add(name, BorderLayout.WEST);
                row.add(status, BorderLayout.EAST);

                panel.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return panel;
    }

    // ===== SYSTEM ALERTS =====
    private JPanel createSystemAlerts() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 248, 220));
        panel.setBorder(BorderFactory.createTitledBorder("System Alerts"));

        panel.add(new JLabel("• 1 suspicious user"));
        panel.add(new JLabel("• 3 pending approvals"));
        panel.add(new JLabel("• Monthly report ready"));

        return panel;
    }
}