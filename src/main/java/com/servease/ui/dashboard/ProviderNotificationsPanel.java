package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProviderNotificationsPanel extends JPanel {

    private User user;
    private BookingController bookingController;
    private ServiceController serviceController;

    public ProviderNotificationsPanel(User user) {

        this.user = user;
        this.bookingController = new BookingController();
        this.serviceController = new ServiceController();

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }

    // ===== HEADER =====
    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        JLabel title = new JLabel("Notifications");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        header.add(title, BorderLayout.WEST);

        return header;
    }

    // ===== CONTENT =====
    private JScrollPane createContent() {

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(245,247,250));

        List<Object[]> bookings = bookingController.getBookingsByProvider(user.getId());
        List<Service> services = serviceController.getServicesByProviderId(user.getId());

        // ===== BOOKING NOTIFICATIONS =====
        for (Object[] b : bookings) {

            String service = b[2].toString();
            String status = b[5].toString();

            if (status.equalsIgnoreCase("Pending")) {
                container.add(createCard("🟡 New Booking", service + " booked"));
            }

            if (status.equalsIgnoreCase("Completed")) {
                container.add(createCard("✔ Completed", service + " completed"));
            }
        }

        // ===== LOW RATING ALERT =====
        for (Service s : services) {

            if (s.getRating() < 3) {
                container.add(createCard("⚠ Low Rating", s.getName() + " needs improvement"));
            }
        }

        if (container.getComponentCount() == 0) {
            container.add(new JLabel("No Notifications"));
        }

        return new JScrollPane(container);
    }

    // ===== CARD =====
    private JPanel createCard(String title, String msg) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel m = new JLabel(msg);
        m.setForeground(Color.GRAY);

        card.add(t, BorderLayout.NORTH);
        card.add(m, BorderLayout.CENTER);

        return card;
    }
}