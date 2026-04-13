package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProviderBookingsFrame extends JFrame {

    private User user;
    private JTable table;
    private DefaultTableModel model;

    public ProviderBookingsFrame(User user) {

        this.user = user;

        setTitle("Provider Bookings");
        setSize(700, 400);
        setLayout(null);

        model = new DefaultTableModel(
                new String[]{"Booking ID", "Service", "User Name", "Date", "Status"}, 0
        );

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 20, 650, 250);
        add(scroll);

        // 🔥 ACCEPT BUTTON
        JButton acceptBtn = new JButton("Accept");
        acceptBtn.setBounds(150, 300, 120, 30);
        add(acceptBtn);

        // 🔥 REJECT BUTTON
        JButton rejectBtn = new JButton("Reject");
        rejectBtn.setBounds(300, 300, 120, 30);
        add(rejectBtn);

        JButton completeBtn = new JButton("Completed");
        completeBtn.setBounds(450, 300, 120, 30);
        add(completeBtn);

        loadBookings();

        // 🔥 ACCEPT LOGIC
        acceptBtn.addActionListener(e -> updateStatus("Accepted"));

        // 🔥 REJECT LOGIC
        rejectBtn.addActionListener(e -> updateStatus("Rejected"));

        completeBtn.addActionListener(e -> updateStatus("Completed"));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadBookings() {

        model.setRowCount(0);

        BookingController controller = new BookingController();
        List<Object[]> list = controller.getBookingsByProvider(user.getId());

        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    private void updateStatus(String newStatus) {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a booking first");
            return;
        }

        int bookingId = (int) model.getValueAt(row, 0);
        String currentStatus = (String) model.getValueAt(row, 4);

        // 🔒 BLOCK IF ALREADY COMPLETED
        if (currentStatus.equalsIgnoreCase("Completed")) {
            JOptionPane.showMessageDialog(this, "This booking is already completed. No changes allowed.");
            return;
        }

        // 🔁 OPTIONAL: PREVENT SAME STATUS UPDATE
        if (currentStatus.equalsIgnoreCase(newStatus)) {
            JOptionPane.showMessageDialog(this, "Already " + newStatus);
            return;
        }

        // 🔥 CONFIRMATION
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to mark this as " + newStatus + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // 🔥 CALL CONTROLLER
        BookingController controller = new BookingController();
        boolean result = controller.updateBookingStatus(bookingId, newStatus);

        if (result) {
            JOptionPane.showMessageDialog(this, "Status updated to " + newStatus);
            loadBookings(); // 🔄 refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Update failed!");
        }
    }
}