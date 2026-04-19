package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.controller.ServiceController;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProviderDashboardHomePanel extends JPanel {

    private User user;

    private BookingController bookingController = new BookingController();
    private ServiceController serviceController = new ServiceController();

    public ProviderDashboardHomePanel(User user) {

        this.user = user;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        add(createTopBar(), BorderLayout.NORTH);
        add(createCenterContent(), BorderLayout.CENTER);
    }

    // ===== TOP BAR =====
    private JPanel createTopBar() {

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(300, 35));
        search.setBorder(BorderFactory.createTitledBorder("Search services"));

        JLabel userLabel = new JLabel(" " + user.getName());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        top.add(search, BorderLayout.WEST);
        top.add(userLabel, BorderLayout.EAST);

        return top;
    }

    // ===== CENTER =====
    private JPanel createCenterContent() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 247, 250));

        // ===== FETCH DATA =====
        List<Object[]> bookings = bookingController.getBookingsByProvider(user.getId());

        int totalBookings = bookings.size();
        int completed = 0;
        double earnings = 0;

        for (Object[] b : bookings) {

            String status = b[5].toString();

            if (status.equalsIgnoreCase("Completed")) {
                completed++;
                earnings += Double.parseDouble(b[4].toString());
            }
        }

        int totalServices = serviceController.getServicesByProviderId(user.getId()).size();

        // ===== CARDS =====
        JPanel cards = new JPanel(new GridLayout(1, 4, 20, 0));
        cards.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cards.setBackground(new Color(245, 247, 250));

        cards.add(createCard(String.valueOf(totalServices), "Total Services"));
        cards.add(createCard(String.valueOf(totalBookings), "Total Bookings"));
        cards.add(createCard(String.valueOf(completed), "Completed"));
        cards.add(createCard("₹ " + earnings, "Earnings"));

        // ===== LOWER =====
        JPanel lower = new JPanel(new GridLayout(1, 2, 20, 20));
        lower.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        lower.setBackground(new Color(245, 247, 250));

        lower.add(createRecentBookings(bookings));
        lower.add(createNotifications(bookings));

        wrapper.add(cards, BorderLayout.NORTH);
        wrapper.add(lower, BorderLayout.CENTER);

        return wrapper;
    }

    // ===== CARD =====
    private JPanel createCard(String value, String label) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 22));
        val.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel txt = new JLabel(label);
        txt.setForeground(Color.GRAY);
        txt.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(val);
        card.add(Box.createVerticalStrut(5));
        card.add(txt);

        return card;
    }

    // ===== RECENT BOOKINGS =====
    private JPanel createRecentBookings(List<Object[]> bookings) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Recent Bookings"));

        int count = 0;

        for (Object[] b : bookings) {

            if (count == 5) break;

            String service = b[2].toString();
            String customer = b[1].toString();
            String date = b[3].toString();
            String status = b[5].toString();

            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(Color.WHITE);
            row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel left = new JLabel(service + " - " + customer + " (" + date + ")");
            JLabel right = new JLabel(status);

            if (status.equalsIgnoreCase("Completed"))
                right.setForeground(new Color(76, 175, 80));
            else if (status.equalsIgnoreCase("Pending"))
                right.setForeground(new Color(255, 152, 0));
            else
                right.setForeground(Color.RED);

            row.add(left, BorderLayout.WEST);
            row.add(right, BorderLayout.EAST);

            panel.add(row);
            count++;
        }

        return panel;
    }

    // ===== NOTIFICATIONS =====
    private JPanel createNotifications(List<Object[]> bookings) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Notifications"));

        for (Object[] b : bookings) {

            String service = b[2].toString();
            String status = b[5].toString();

            if (status.equalsIgnoreCase("Completed")) {
                panel.add(new JLabel("✔ Completed: " + service));
            } else if (status.equalsIgnoreCase("Pending")) {
                panel.add(new JLabel("🟡 New booking: " + service));
            }
        }

        return panel;
    }
}