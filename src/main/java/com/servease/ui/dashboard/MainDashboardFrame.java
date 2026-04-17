package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class MainDashboardFrame extends JFrame {

    private User user;
    private JPanel contentPanel;

    public MainDashboardFrame(User user) {
        this.user = user;

        setTitle("Servease Dashboard");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setBackground(new Color(30, 120, 255));

        JLabel logo = new JLabel("Servease");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 22));
        logo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton dashboardBtn = createSidebarBtn("Dashboard");
        JButton servicesBtn = createSidebarBtn("My Services");
        JButton bookingsBtn = createSidebarBtn("Bookings");
        JButton browseBtn = createSidebarBtn("Browse");
        JButton logoutBtn = createSidebarBtn("Logout");

        sidebar.add(logo);
        sidebar.add(dashboardBtn);
        sidebar.add(servicesBtn);

        // 👉 ROLE BASED
        if (user.getRole().equals("PROVIDER")) {
            sidebar.add(bookingsBtn);
        } else {
            sidebar.add(browseBtn);
        }

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // ===== TOP BAR =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel userLabel = new JLabel("Welcome, " + user.getName());

        topBar.add(title, BorderLayout.WEST);
        topBar.add(userLabel, BorderLayout.EAST);

        // ===== CONTENT =====
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 245, 245));

        loadDashboard();

        add(sidebar, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // ===== ACTIONS =====
        dashboardBtn.addActionListener(e -> loadDashboard());
        servicesBtn.addActionListener(e -> loadServices());

        bookingsBtn.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new ProviderBookingsFrame(user), BorderLayout.CENTER);
            refresh();
        });

        browseBtn.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new BrowseServicesPanel(user), BorderLayout.CENTER);
            refresh();
        });

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });

        setVisible(true);
    }

    // ===== DASHBOARD HOME =====
    private void loadDashboard() {

        contentPanel.removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 245, 245));

        panel.add(createCard("3", "Services"));
        panel.add(createCard("67", "Bookings"));
        panel.add(createCard("4.7", "Rating"));
        panel.add(createCard("₹4280", "Revenue"));

        contentPanel.add(panel, BorderLayout.CENTER);
        refresh();
    }

    // ===== LOAD SERVICES =====
    private void loadServices() {
        contentPanel.removeAll();
        contentPanel.add(new ProviderServicesFrame(user), BorderLayout.CENTER);
        refresh();
    }

    // ===== UI HELPERS =====
    private JButton createSidebarBtn(String text) {

        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setBackground(new Color(30, 120, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color normal = new Color(30, 120, 255);
        Color hover = new Color(20, 100, 220);

        // HOVER EFFECT
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(hover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(normal);
            }
        });

        return btn;
    }

    private JPanel createCard(String value, String label) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel txt = new JLabel(label);
        txt.setForeground(Color.GRAY);

        card.add(val, BorderLayout.CENTER);
        card.add(txt, BorderLayout.SOUTH);

        return card;
    }

    private void refresh() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}