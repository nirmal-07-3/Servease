package com.servease.ui.dashboard;

import com.servease.dao.ServiceDAO;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProviderReviewsPanel extends JPanel {

    private User user;
    private ServiceDAO serviceDAO;

    public ProviderReviewsPanel(User user) {
        this.user = user;
        this.serviceDAO = new ServiceDAO();

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        add(createTop(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }

    // ===== TOP =====
    private JPanel createTop() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Reviews");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        top.add(title, BorderLayout.WEST);

        return top;
    }

    // ===== MAIN CONTENT =====
    private JScrollPane createContent() {

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(245, 247, 250));

        // ===== STATS CARDS =====
        container.add(createStatsCards());

        // ===== REVIEWS LIST =====
        List<Service> services = serviceDAO.getServicesByProviderId(user.getId());

        for (Service s : services) {
            container.add(createReviewCard(s));
        }

        return new JScrollPane(container);
    }

    // ===== STATS =====
    private JPanel createStatsCards() {
        JPanel cards = new JPanel(new GridLayout(1, 3, 20, 0));
        cards.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cards.setBackground(new Color(245, 247, 250));

        List<Service> services = serviceDAO.getServicesByProviderId(user.getId());

        int totalReviews = services.size();
        double avg = services.stream().mapToDouble(Service::getRating).average().orElse(0);
        long fiveStar = services.stream().filter(s -> s.getRating() >= 4.5).count();

        cards.add(createCard(String.valueOf(totalReviews), "Total Reviews"));
        cards.add(createCard(String.format("%.1f", avg), "Avg Rating"));
        cards.add(createCard(String.valueOf(fiveStar), "5★ Reviews"));

        return cards;
    }

    private JPanel createCard(String value, String label) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 20));
        v.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel l = new JLabel(label);
        l.setForeground(Color.GRAY);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(v);
        card.add(Box.createVerticalStrut(5));
        card.add(l);

        return card;
    }

    // ===== REVIEW CARD =====
    private JPanel createReviewCard(Service s) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // LEFT
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(Color.WHITE);

        JLabel name = new JLabel(s.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel desc = new JLabel(s.getDescription());
        desc.setForeground(Color.GRAY);

        left.add(name);
        left.add(desc);

        // RIGHT (Stars)
        JLabel rating = new JLabel(getStars(s.getRating()));
        rating.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

        card.add(left, BorderLayout.WEST);
        card.add(rating, BorderLayout.EAST);

        return card;
    }

    // ===== STAR GENERATOR =====
    private String getStars(double rating) {
        int full = (int) rating;
        StringBuilder stars = new StringBuilder();

        for (int i = 0; i < full; i++) stars.append("★");
        for (int i = full; i < 5; i++) stars.append("☆");

        return stars.toString();
    }
}