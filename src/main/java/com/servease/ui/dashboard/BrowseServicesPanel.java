package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;
import com.servease.ui.components.ServiceDetailsDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BrowseServicesPanel extends JPanel {

    private User user;
    private JPanel container;
    private ServiceController controller;
    private JTextField searchField;

    public BrowseServicesPanel(User user) {
        this.user = user;
        this.controller = new ServiceController();

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(Color.WHITE);

        JLabel title = new JLabel("Browse Services");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel subtitle = new JLabel("Explore and book services near you");
        subtitle.setForeground(Color.GRAY);

        titleBox.add(title);
        titleBox.add(subtitle);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(220, 35));
        searchField.setBorder(BorderFactory.createTitledBorder("Search Services"));

        JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchWrapper.setBackground(Color.WHITE);
        searchWrapper.add(searchField);

        header.add(titleBox, BorderLayout.WEST);
        header.add(searchWrapper, BorderLayout.EAST);

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                search();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                search();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                search();
            }
        });

        // ===== FILTERS =====
        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filters.setBackground(new Color(245,247,250));
        filters.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String[] categories = {"All","Cleaning","Plumbing","Electrical","Painting"};

        for(String cat : categories){
            JButton btn = new JButton(cat);
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> loadServices(cat));
            filters.add(btn);
        }

        // ===== GRID =====
        container = new JPanel(new GridLayout(0, 3, 20, 20));
        container.setBackground(new Color(245,247,250));
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // ===== CENTER PANEL =====
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245,247,250));
        centerPanel.add(filters, BorderLayout.NORTH);
        centerPanel.add(scroll, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        loadServices("All");
    }

    // ===== LOAD SERVICES =====
    private void loadServices(String category){
        container.removeAll();

        List<Service> list = controller.getAllServices();

        for(Service s : list){
            if(!category.equals("All") &&
                    !s.getName().toLowerCase().contains(category.toLowerCase())){
                continue;
            }

            container.add(createServiceCard(s));
        }

        container.revalidate();
        container.repaint();
    }

    // ===== ICON LOADER =====
    private ImageIcon loadIcon(String path, int w, int h) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    private void search() {
        String keyword = searchField.getText().toLowerCase();

        container.removeAll();

        List<Service> list = controller.getAllServices();

        for (Service s : list) {
            if (s.getName().toLowerCase().contains(keyword)) {
                container.add(createServiceCard(s));
            }
        }

        container.revalidate();
        container.repaint();
    }

    // ===== CARD =====
    private JPanel createServiceCard(Service s){
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(new Color(33,150,243), 2));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(new Color(220,220,220), 1));
            }
        });

        // ===== TOP =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);

        JLabel name = new JLabel(s.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel tag = new JLabel("Service");
        tag.setForeground(Color.GRAY);
        tag.setIcon(loadIcon("icons/provider.png", 14, 14));

        top.add(name, BorderLayout.WEST);
        top.add(tag, BorderLayout.EAST);

        // ===== CENTER =====
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);

        JLabel provider = new JLabel("Provider Available");
        provider.setForeground(Color.GRAY);

        JPanel infoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        infoRow.setBackground(Color.WHITE);

        JLabel rating = new JLabel("4.5");
        rating.setIcon(loadIcon("icons/star.png", 14, 14));

        JLabel time = new JLabel("1-2 hrs");
        time.setIcon(loadIcon("icons/clock.png", 14, 14));

        JLabel location = new JLabel("Nearby");
        location.setIcon(loadIcon("icons/location.png", 14, 14));

        infoRow.add(rating);
        infoRow.add(time);
        infoRow.add(location);

        center.add(provider);
        center.add(Box.createVerticalStrut(5));
        center.add(infoRow);

        // ===== BOTTOM =====
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);

        JLabel price = new JLabel("₹ " + s.getPrice() + " /service");
        price.setFont(new Font("Segoe UI", Font.BOLD, 14));
        price.setForeground(new Color(33,150,243));

        JButton btn = new JButton("View");
        btn.setBackground(new Color(33,150,243));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            new ServiceDetailsDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    s,
                    user
            );
        });

        bottom.add(price, BorderLayout.WEST);
        bottom.add(btn, BorderLayout.EAST);

        // ===== ADD =====
        card.add(top, BorderLayout.NORTH);
        card.add(center, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        return card;
    }
}