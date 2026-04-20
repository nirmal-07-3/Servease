package com.servease.ui.dashboard;

import com.servease.dao.ReviewDAO;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProviderReviewsPanel extends JPanel {

    private User user;
    private ReviewDAO reviewDAO = new ReviewDAO();

    public ProviderReviewsPanel(User user) {
        this.user = user;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        add(createHeader(), BorderLayout.NORTH);
        add(createBody(), BorderLayout.CENTER);
    }

    // ===== HEADER =====
    private JPanel createHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Reviews");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel sub = new JLabel("Customer reviews on your services.");
        sub.setForeground(Color.GRAY);

        panel.add(title);
        panel.add(sub);

        return panel;
    }

    // ===== BODY =====
    private JPanel createBody() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(245,247,250));
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        List<Object[]> reviews = reviewDAO.getReviewsByProvider(user.getId());

        main.add(createStats(reviews), BorderLayout.NORTH);
        main.add(createReviewList(reviews), BorderLayout.CENTER);

        return main;
    }

    // ===== STATS =====
    private JPanel createStats(List<Object[]> list) {

        int total = list.size();
        double sum = 0;
        int fiveStar = 0;

        for (Object[] r : list) {
            double rating = (double) r[0];
            sum += rating;
            if (rating == 5.0) fiveStar++;
        }

        double avg = total == 0 ? 0 : sum / total;

        JPanel panel = new JPanel(new GridLayout(1,3,20,0));
        panel.setBackground(new Color(245,247,250));

        panel.add(createStatCard(String.valueOf(total), "Total Reviews"));
        panel.add(createStatCard(String.format("%.1f", avg), "Avg Rating"));
        panel.add(createStatCard(String.valueOf(fiveStar), "5★ Reviews"));

        return panel;
    }

    private JPanel createStatCard(String value, String label) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.GRAY);

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBackground(Color.WHITE);

        text.add(val);
        text.add(lbl);

        card.add(text, BorderLayout.CENTER);

        return card;
    }

    // ===== REVIEW LIST =====
    private JScrollPane createReviewList(List<Object[]> list) {

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(245,247,250));

        if (list.isEmpty()) {
            JLabel empty = new JLabel("No reviews yet");
            empty.setForeground(Color.GRAY);
            empty.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            container.add(empty);
        }

        for (Object[] r : list) {
            container.add(createReviewCard(r));
            container.add(Box.createVerticalStrut(15));
        }

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);

        return scroll;
    }

    // ===== REVIEW CARD =====
    private JPanel createReviewCard(Object[] r) {

        double rating = (double) r[0];
        String comment = (String) r[1];
        String serviceName = (String) r[2];
        String userName = (String) r[3];
        String date = r[4].toString();

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        // LEFT INFO
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(Color.WHITE);

        JLabel name = new JLabel(userName);
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel service = new JLabel(serviceName + " • " + date);
        service.setForeground(Color.GRAY);

        JLabel commentLbl = new JLabel("<html>" + comment + "</html>");

        left.add(name);
        left.add(service);
        left.add(Box.createVerticalStrut(5));
        left.add(commentLbl);

        // RIGHT STARS
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setBackground(Color.WHITE);

        JLabel stars = new JLabel(getStars(rating));
        stars.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        stars.setForeground(new Color(255,193,7));

        right.add(stars);

        card.add(left, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);

        return card;
    }

    // ===== STAR GENERATOR =====
    private String getStars(double rating) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < rating) sb.append("★");
            else sb.append("☆");
        }
        return sb.toString();
    }
}