package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;
import com.servease.ui.components.ServiceDetailsDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BrowseServicesPanel extends JPanel {

    private User user;
    private JPanel container;
    private ServiceController controller;

    public BrowseServicesPanel(User user) {

        this.user = user;
        this.controller = new ServiceController();

        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));

        // ===== TOP BAR =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        JLabel title = new JLabel("Available Services");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(200,30));

        top.add(title, BorderLayout.WEST);
        top.add(search, BorderLayout.EAST);

        // ===== CATEGORY FILTER =====
        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        filters.setBackground(new Color(245,245,245));

        String[] categories = {"All","Cleaning","Plumbing","Electrical","Painting"};

        for(String cat : categories){

            JButton btn = new JButton(cat);
            btn.setFocusPainted(false);

            btn.addActionListener(e -> loadServices(cat));

            filters.add(btn);
        }

        // ===== SERVICE GRID =====
        container = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        container.setBackground(new Color(245,245,245));

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);

        // ===== ADD =====
        add(top, BorderLayout.NORTH);
        add(filters, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        loadServices("All");
    }

    // ===== LOAD SERVICES =====
    private void loadServices(String category){

        container.removeAll();

        List<Service> list = controller.getAllServices();

        for(Service s : list){

            if(!category.equals("All") && !s.getName().toLowerCase().contains(category.toLowerCase())){
                continue;
            }

            JPanel card = createServiceCard(s);
            container.add(card);
        }

        container.revalidate();
        container.repaint();
    }

    // ===== CARD =====
    private JPanel createServiceCard(Service s){

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(260,200));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        // IMAGE
        JLabel img = new JLabel();
        img.setPreferredSize(new Dimension(260,100));

        try{
            ImageIcon icon = new ImageIcon(s.getImagePath());
            Image image = icon.getImage().getScaledInstance(260,100,Image.SCALE_SMOOTH);
            img.setIcon(new ImageIcon(image));
        }catch(Exception e){
            img.setText("No Image");
        }

        // NAME
        JLabel name = new JLabel(s.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // PRICE
        JLabel price = new JLabel("₹ " + s.getPrice());
        price.setForeground(new Color(33,150,243));

        // BUTTON
        JButton btn = new JButton("View");
        btn.setBackground(new Color(33,150,243));
        btn.setForeground(Color.WHITE);

        btn.addActionListener(e -> {
            new ServiceDetailsDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    s,
                    user
            );
        });

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);
        bottom.add(name, BorderLayout.NORTH);
        bottom.add(price, BorderLayout.CENTER);
        bottom.add(btn, BorderLayout.SOUTH);

        card.add(img, BorderLayout.NORTH);
        card.add(bottom, BorderLayout.CENTER);

        return card;
    }
}