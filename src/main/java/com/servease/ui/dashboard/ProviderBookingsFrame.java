package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProviderBookingsFrame extends JPanel {

    private User user;
    private JPanel container;
    private BookingController controller;

    public ProviderBookingsFrame(User user){

        this.user = user;
        this.controller = new BookingController();

        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));

        // ===== TOP BAR =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        JLabel title = new JLabel("My Bookings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        top.add(title, BorderLayout.WEST);

        // ===== CONTAINER =====
        container = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        container.setBackground(new Color(245,245,245));

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        loadBookings();
    }

    // ===== LOAD BOOKINGS =====
    private void loadBookings(){

        container.removeAll();

        List<Object[]> list = controller.getBookingsByProvider(user.getId());

        for(Object[] row : list){

            JPanel card = createBookingCard(row);
            container.add(card);
        }

        container.revalidate();
        container.repaint();
    }

    // ===== CREATE CARD =====
    private JPanel createBookingCard(Object[] row){

        int bookingId = (int) row[0];
        String service = (String) row[1];
        String username = (String) row[2];
        String date = row[3].toString();
        String status = (String) row[4];

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(300,180));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        // ===== INFO =====
        JLabel title = new JLabel(service);
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JLabel userLabel = new JLabel("User: " + username);
        JLabel dateLabel = new JLabel("Date: " + date);

        // ===== STATUS BADGE =====
        JLabel statusLabel = new JLabel(status);
        statusLabel.setOpaque(true);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        if(status.equalsIgnoreCase("Pending")){
            statusLabel.setBackground(new Color(255,152,0));
        } else if(status.equalsIgnoreCase("Accepted")){
            statusLabel.setBackground(new Color(33,150,243));
        } else if(status.equalsIgnoreCase("Completed")){
            statusLabel.setBackground(new Color(76,175,80));
        } else {
            statusLabel.setBackground(new Color(244,67,54));
        }

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);

        info.add(title);
        info.add(userLabel);
        info.add(dateLabel);
        info.add(Box.createVerticalStrut(5));
        info.add(statusLabel);

        // ===== ACTION BUTTONS =====
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBackground(Color.WHITE);

        JButton accept = new JButton("Accept");
        JButton reject = new JButton("Reject");
        JButton complete = new JButton("Complete");

        accept.setFocusPainted(false);
        reject.setFocusPainted(false);
        complete.setFocusPainted(false);

        // DISABLE IF COMPLETED
        if(status.equalsIgnoreCase("Completed")){
            accept.setEnabled(false);
            reject.setEnabled(false);
            complete.setEnabled(false);
        }

        // ACTIONS
        accept.addActionListener(e -> updateStatus(bookingId, "Accepted"));
        reject.addActionListener(e -> updateStatus(bookingId, "Rejected"));
        complete.addActionListener(e -> updateStatus(bookingId, "Completed"));

        actions.add(accept);
        actions.add(reject);
        actions.add(complete);

        // ===== ADD =====
        card.add(info, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);

        return card;
    }

    // ===== UPDATE STATUS =====
    private void updateStatus(int id, String status){

        boolean res = controller.updateBookingStatus(id, status);

        if(res){
            JOptionPane.showMessageDialog(this,"Updated to " + status);
            loadBookings();
        } else {
            JOptionPane.showMessageDialog(this,"Failed");
        }
    }
}