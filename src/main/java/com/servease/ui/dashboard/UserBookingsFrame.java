package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.model.Bookings;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserBookingsFrame extends JPanel {

    private User user;
    private JPanel container;
    private BookingController controller;

    public UserBookingsFrame(User user) {
        this.user = user;
        this.controller = new BookingController();

        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        JLabel title = new JLabel("My Bookings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        top.add(title, BorderLayout.WEST);

        container = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        container.setBackground(new Color(245,245,245));

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        loadBookings();
    }

    private void loadBookings() {
        container.removeAll();

        List<Bookings> list = controller.getBookingsByUser(user.getId());

        for(Bookings b : list){
            container.add(createBookingCard(b));
        }

        container.revalidate();
        container.repaint();
    }

    private JPanel createBookingCard(Bookings b){

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(350,170));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        JLabel service = new JLabel(b.getServiceName());
        service.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel provider = new JLabel("By " + b.getProviderName());
        provider.setForeground(Color.GRAY);

        JLabel date = new JLabel("📅 " + b.getBooking_date());

        JLabel status = new JLabel(b.getStatus());
        status.setOpaque(true);
        status.setForeground(Color.WHITE);
        status.setBorder(BorderFactory.createEmptyBorder(5,12,5,12));

        if(b.getStatus().equalsIgnoreCase("Pending")){
            status.setBackground(new Color(255,152,0));
        } else if(b.getStatus().equalsIgnoreCase("Accepted")){
            status.setBackground(new Color(33,150,243));
        } else if(b.getStatus().equalsIgnoreCase("Completed")){
            status.setBackground(new Color(76,175,80));
        } else {
            status.setBackground(new Color(244,67,54));
        }

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(Color.WHITE);

        left.add(service);
        left.add(provider);
        left.add(Box.createVerticalStrut(10));
        left.add(date);

        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(Color.WHITE);
        right.add(status, BorderLayout.NORTH);

        card.add(left, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);

        return card;
    }
}