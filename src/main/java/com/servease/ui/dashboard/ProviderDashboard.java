package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProviderDashboard extends JFrame {

    private final User user;
    private JPanel content;

    // Colors
    private final Color BG = new Color(245,245,245);
    private final Color SIDEBAR = new Color(20, 120, 220);
    private final Color SIDEBAR_ACTIVE = new Color(255,255,255);
    private final Color TEXT_DARK = new Color(60,60,60);

    public ProviderDashboard(User user) {
        this.user = user;

        setTitle("Servease - Provider Dashboard");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(220, getHeight()));

        JLabel logo = new JLabel("Servease");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 22));
        logo.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JButton dashboardBtn = sidebarBtn("Dashboard");
        JButton servicesBtn = sidebarBtn("My Services");
        JButton bookingBtn = sidebarBtn("Bookings");
        JButton logoutBtn = sidebarBtn("Logout");

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
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel welcome = new JLabel("Welcome, " + user.getName());
        welcome.setForeground(TEXT_DARK);

        header.add(title, BorderLayout.WEST);
        header.add(welcome, BorderLayout.EAST);

        // ===== CONTENT =====
        content = new JPanel(new BorderLayout());
        content.setBackground(BG);

        // Default
        loadDashboardHome();

        add(sidebar, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);

        // ===== ACTIONS =====
        dashboardBtn.addActionListener(e -> loadDashboardHome());
        servicesBtn.addActionListener(e -> loadPanel(new ProviderServicesFrame(user)));
        bookingBtn.addActionListener(e -> loadPanel(new ProviderBookingsFrame(user)));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    // ================= DASHBOARD =================
    private void loadDashboardHome() {

        content.removeAll();

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(BG);

        // ===== CARDS =====
        JPanel cards = new JPanel(new GridLayout(1,4,20,20));
        cards.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        cards.setBackground(BG);

        ServiceController controller = new ServiceController();
        List<Service> list = controller.getServicesByProviderId(user.getId());

        cards.add(card(String.valueOf(list.size()), "Total Services"));
        cards.add(card("67", "Bookings")); // you can connect later
        cards.add(card("4.7", "Rating"));
        cards.add(card("₹4280", "Revenue"));

        // ===== TOP BAR =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        topBar.setBackground(BG);

        JLabel section = new JLabel("My Services");
        section.setFont(new Font("Arial", Font.BOLD, 18));

        JButton addBtn = new JButton("+ Add New Service");
        addBtn.setBackground(new Color(33,150,243));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setFont(new Font("Arial", Font.BOLD, 13));
        addBtn.setBorder(BorderFactory.createEmptyBorder(8,15,8,15));

        // 🔥 WORKING BUTTON
        addBtn.addActionListener(e -> new AddServiceFrame(user, null));

        topBar.add(section, BorderLayout.WEST);
        topBar.add(addBtn, BorderLayout.EAST);

        // ===== TABLE =====
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Service Name","Price"},0
        );

        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(33,150,243));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setShowVerticalLines(false);

        for(Service s : list){
            model.addRow(new Object[]{
                    s.getName(),
                    s.getPrice()
            });
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));

        main.add(cards);
        main.add(topBar);
        main.add(scroll);

        content.add(main, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    // ================= UI HELPERS =================
    private JPanel card(String value, String title){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(20,20,20,20)
        ));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel txt = new JLabel(title);
        txt.setForeground(Color.GRAY);

        panel.add(val, BorderLayout.CENTER);
        panel.add(txt, BorderLayout.SOUTH);

        return panel;
    }

    private JButton sidebarBtn(String text){
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
        btn.setBackground(SIDEBAR);
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