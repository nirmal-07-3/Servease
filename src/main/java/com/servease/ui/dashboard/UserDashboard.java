package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {

    private User user;
    private JPanel content;

    public UserDashboard(User user){
        this.user = user;

        setTitle("User Dashboard");
        setSize(1100,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(33,150,243));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));

        JLabel logo = new JLabel("Servease");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JButton dashboardBtn = createBtn("Dashboard");
        JButton browseBtn = createBtn("Browse Services");
        JButton bookingBtn = createBtn("My Bookings");
        JButton logoutBtn = createBtn("Logout");

        sidebar.add(logo);
        sidebar.add(dashboardBtn);
        sidebar.add(browseBtn);
        sidebar.add(bookingBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // ===== CONTENT =====
        content = new JPanel(new BorderLayout());
        content.setBackground(new Color(245,245,245));

        content.add(createDashboardPanel());

        // ===== ACTIONS =====
        dashboardBtn.addActionListener(e -> loadPanel(createDashboardPanel()));
        browseBtn.addActionListener(e -> loadPanel(new BrowseServicesPanel(user)));
        bookingBtn.addActionListener(e -> loadPanel(new UserBookingsFrame(user)));

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,"Logout?");
            if(confirm == JOptionPane.YES_OPTION){
                dispose();
                new LoginFrame();
            }
        });

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createDashboardPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245,245,245));

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(20,30,10,30));
        header.setBackground(new Color(245,245,245));

        JLabel title = new JLabel("User Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel welcome = new JLabel("Welcome, " + user.getName());
        welcome.setForeground(Color.GRAY);

        header.add(title, BorderLayout.WEST);
        header.add(welcome, BorderLayout.EAST);

        JPanel cards = new JPanel(new GridLayout(1,4,20,20));
        cards.setBorder(BorderFactory.createEmptyBorder(10,30,30,30));
        cards.setBackground(new Color(245,245,245));

        cards.add(createCard("Bookings", "12", new Color(66,133,244)));
        cards.add(createCard("Services Used", "8", new Color(52,168,83)));
        cards.add(createCard("Completed", "10", new Color(251,188,5)));
        cards.add(createCard("Rating", "4.7", new Color(234,67,53)));

        panel.add(header, BorderLayout.NORTH);
        panel.add(cards, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCard(String title, String value, Color color){
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel t = new JLabel(title);
        t.setForeground(Color.GRAY);

        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 26));
        v.setForeground(color);

        card.add(t);
        card.add(Box.createVerticalStrut(10));
        card.add(v);

        return card;
    }

    private JButton createBtn(String text){
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,45));
        btn.setBackground(new Color(33,150,243));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        return btn;
    }

    private void loadPanel(JPanel panel){
        content.removeAll();
        content.add(panel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }
}