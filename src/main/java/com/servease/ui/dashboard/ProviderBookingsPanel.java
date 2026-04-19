package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProviderBookingsPanel extends JPanel {

    private User user;
    private JTable table;
    private DefaultTableModel model;
    private BookingController controller;

    public ProviderBookingsPanel(User user) {
        this.user = user;
        this.controller = new BookingController();

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        add(createTop(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);

        loadBookings();
    }

    // ===== TOP HEADER =====
    private JPanel createTop() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Bookings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        top.add(title, BorderLayout.WEST);

        return top;
    }

    // ===== TABLE =====
    private JScrollPane createTable() {
        String[] cols = {
                "Booking ID", "Customer", "Service",
                "Date", "Status", "Action"
        };

        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return c == 5; // Only action column clickable
            }
        };

        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        // BUTTON RENDER
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        return new JScrollPane(table);
    }

    // ===== LOAD DATA =====
    private void loadBookings() {
        model.setRowCount(0);

        List<Object[]> list = controller.getBookingsByProvider(user.getId());

        for (Object[] b : list) {
            model.addRow(new Object[]{
                    "BK-" + b[0],     // booking id
                    b[1],            // customer name
                    b[2],            // service name
                    b[3],            // date
                    b[4],            // status
                    getActionText(b[4].toString())
            });
        }
    }

    // ===== ACTION TEXT =====
    private String getActionText(String status) {
        switch (status.toLowerCase()) {
            case "pending":
                return "Accept";
            case "in progress":
                return "Complete";
            case "completed":
                return "Done";
            case "cancelled":
                return "Cancelled";
            default:
                return "-";
        }
    }

    // ===== BUTTON RENDERER =====
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int col) {
            setText(value == null ? "" : value.toString());
            return this;
        }
    }

    // ===== BUTTON EDITOR =====
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;
        private int bookingId;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            label = value.toString();
            button.setText(label);

            String idStr = table.getValueAt(row, 0).toString().replace("BK-", "");
            bookingId = Integer.parseInt(idStr);

            return button;
        }

        public Object getCellEditorValue() {

            if (label.equals("Accept")) {
                controller.updateBookingStatus(bookingId, "In Progress");
            } else if (label.equals("Complete")) {
                controller.updateBookingStatus(bookingId, "Completed");
            }

            loadBookings(); // refresh

            return label;
        }
    }
}