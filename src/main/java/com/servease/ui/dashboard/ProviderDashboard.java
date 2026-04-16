package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProviderDashboard extends JFrame {

    private User user;

    public ProviderDashboard(User user) {

        this.user = user;

        setTitle("Provider Dashboard");
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
        // 🔹 CONTENT PANEL
        // =======================
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.setBackground(new Color(245, 245, 245));

        // 🔥 STATS CARDS
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 20, 20));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        cardsPanel.setBackground(new Color(245, 245, 245));

        cardsPanel.add(createCard("Total Services", "3"));
        cardsPanel.add(createCard("Bookings", "67"));
        cardsPanel.add(createCard("Rating", "4.7"));
        cardsPanel.add(createCard("Revenue", "₹4280"));

        // 🔥 TABLE PANEL
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        tablePanel.setBackground(new Color(245, 245, 245));

        JLabel tableTitle = new JLabel("My Services");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 16));

        // 🔥 TABLE DATA (TEMP — later dynamic)
        String[] columns = {"Service Name", "Category", "Price", "Status"};
        Object[][] data = {
                {"Electrician", "Home", "₹599", "Active"},
                {"Plumbing", "Home", "₹699", "Active"},
                {"Barber", "Personal", "₹399", "Inactive"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        // 🔥 TABLE STYLE
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scroll, BorderLayout.CENTER);

        // ADD TO CONTENT
        content.add(cardsPanel, BorderLayout.NORTH);
        content.add(tablePanel, BorderLayout.CENTER);

        // =======================
        // 🔥 ADD ALL TO FRAME
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

        servicesBtn.addActionListener(e -> {
            new ProviderServicesFrame(user);
        });

        bookingBtn.addActionListener(e -> {
            new ProviderBookingsFrame(user);
        });

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