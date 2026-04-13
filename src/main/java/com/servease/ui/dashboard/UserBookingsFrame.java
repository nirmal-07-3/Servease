package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UserBookingsFrame extends JFrame {

    private User user;
    private JTable table;
    private DefaultTableModel model;

    public UserBookingsFrame(User user) {

        this.user = user;

        setTitle("My Bookings");
        setSize(650, 400);
        setLayout(null);

        // 🔥 Updated Columns
        model = new DefaultTableModel(
                new String[]{"Booking ID", "Service Name", "Date", "Status"}, 0
        );

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 20, 600, 250);
        add(scroll);

        loadBookings();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadBookings() {

        model.setRowCount(0);

        BookingController controller = new BookingController();
        List<Object[]> list = controller.getUserBookingsWithService(user.getId());

        for (Object[] row : list) {
            model.addRow(row);
        }
    }
}