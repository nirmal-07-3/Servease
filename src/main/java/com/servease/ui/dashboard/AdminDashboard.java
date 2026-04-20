package com.servease.ui.admin;

import com.servease.model.User;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private User admin;
    private JPanel content;
    private JButton activeBtn;

    public AdminDashboard(User admin) {
        this.admin = admin;

        setTitle("Servease Admin");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(18, 52, 86));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));

        JLabel logo = new JLabel("Admin Panel");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setBorder(BorderFactory.createEmptyBorder(25,20,25,20));

        JButton dashboardBtn = createBtn("Dashboard");
        JButton usersBtn = createBtn("Users");
        JButton providersBtn = createBtn("Providers");
        JButton servicesBtn = createBtn("Services");
        JButton bookingsBtn = createBtn("Bookings");
        JButton reviewsBtn = createBtn("Reviews");
        JButton logoutBtn = createBtn("Logout");

        sidebar.add(logo);
        sidebar.add(dashboardBtn);
        sidebar.add(usersBtn);
        sidebar.add(providersBtn);
        sidebar.add(servicesBtn);
        sidebar.add(bookingsBtn);
        sidebar.add(reviewsBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // CONTENT
        content = new JPanel(new BorderLayout());
        loadPanel(createDashboard());

        // NAVIGATION
        dashboardBtn.addActionListener(e -> switchPanel(dashboardBtn, createDashboard()));
        usersBtn.addActionListener(e -> switchPanel(usersBtn, new AdminUsersPanel()));
        providersBtn.addActionListener(e -> switchPanel(providersBtn, new AdminProvidersPanel()));
        servicesBtn.addActionListener(e -> switchPanel(servicesBtn, new AdminServicesPanel()));
        bookingsBtn.addActionListener(e -> switchPanel(bookingsBtn, new AdminBookingsPanel()));
        reviewsBtn.addActionListener(e -> switchPanel(reviewsBtn, new AdminReviewsPanel()));

        logoutBtn.addActionListener(e -> {
            dispose();
        });

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createBtn(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setBackground(new Color(18, 52, 86));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        return btn;
    }

    private void switchPanel(JButton btn, JPanel panel){
        if(activeBtn!=null){
            activeBtn.setBackground(new Color(18,52,86));
        }
        btn.setBackground(new Color(33,150,243));
        activeBtn=btn;

        loadPanel(panel);
    }

    private void loadPanel(JPanel panel){
        content.removeAll();
        content.add(panel,BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    // DASHBOARD
    private JPanel createDashboard(){
        JPanel panel = new JPanel(new GridLayout(2,2,20,20));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(createCard("Total Users", "120"));
        panel.add(createCard("Providers", "45"));
        panel.add(createCard("Bookings", "320"));
        panel.add(createCard("Revenue", "₹ 45,000"));

        return panel;
    }

    private JPanel createCard(String title, String value){
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI",Font.BOLD,22));

        JLabel lbl = new JLabel(title);
        lbl.setForeground(Color.GRAY);

        card.add(val,BorderLayout.CENTER);
        card.add(lbl,BorderLayout.SOUTH);

        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        return card;
    }
}