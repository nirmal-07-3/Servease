package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class ProviderDashboard extends JFrame {

    private JPanel content;
    private User user;

    public ProviderDashboard(User user) {
        this.user = user;

        setTitle("Servease - Provider Dashboard");
        setSize(1200,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(20,120,220));
        sidebar.setPreferredSize(new Dimension(220,700));

        JLabel logo = new JLabel("Servease");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial",Font.BOLD,22));
        logo.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JButton dashboardBtn = createBtn("Dashboard");
        JButton servicesBtn = createBtn("My Services");
        JButton bookingBtn = createBtn("Bookings");
        JButton logoutBtn = createBtn("Logout");

        sidebar.add(logo);
        sidebar.add(dashboardBtn);
        sidebar.add(servicesBtn);
        sidebar.add(bookingBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        JLabel title = new JLabel("Provider Dashboard");
        title.setFont(new Font("Arial",Font.BOLD,20));

        JLabel welcome = new JLabel("Welcome, " + user.getName());

        header.add(title,BorderLayout.WEST);
        header.add(welcome,BorderLayout.EAST);

        // ===== CONTENT =====
        content = new JPanel(new BorderLayout());

        add(sidebar,BorderLayout.WEST);
        add(header,BorderLayout.NORTH);
        add(content,BorderLayout.CENTER);

        // ===== DEFAULT VIEW =====
        loadPanel(new DashboardHome(user));

        // ===== ACTIONS =====
        dashboardBtn.addActionListener(e -> loadPanel(new DashboardHome(user)));
        servicesBtn.addActionListener(e -> loadPanel(new ProviderServicesFrame(user)));
        bookingBtn.addActionListener(e -> loadPanel(new ProviderBookingsFrame(user)));

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,"Logout?","Confirm",JOptionPane.YES_NO_OPTION);

            if(confirm==JOptionPane.YES_OPTION){
                dispose();
                new LoginFrame();
            }
        });

        setVisible(true);
    }

    private JButton createBtn(String text){
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
        btn.setBackground(new Color(20,120,220));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        return btn;
    }

    private void loadPanel(JPanel panel){
        content.removeAll();
        content.add(panel,BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }
}