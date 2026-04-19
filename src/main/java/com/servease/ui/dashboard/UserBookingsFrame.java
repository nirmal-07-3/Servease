package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.controller.ServiceController;
import com.servease.model.Bookings;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class UserBookingsFrame extends JPanel {

    private User user;
    private JTable table;
    private DefaultTableModel model;
    private BookingController controller;

    public UserBookingsFrame(User user) {
        this.user = user;
        this.controller = new BookingController();

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        add(createHeader(), BorderLayout.NORTH);
        add(createTableSection(), BorderLayout.CENTER);
    }

    // ===== HEADER =====
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(20,25,20,25));

        JLabel title = new JLabel("My Bookings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel sub = new JLabel("Track all your service bookings and their status.");
        sub.setForeground(Color.GRAY);

        header.add(title);
        header.add(sub);

        return header;
    }

    // ===== TABLE SECTION =====
    private JPanel createTableSection() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245,247,250));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        String[] cols = {"Booking ID","Service","Provider","Date & Time","Amount","Status","Action"};

        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));

        // STATUS COLOR
        table.getColumn("Status").setCellRenderer(new StatusRenderer());

        // ACTION BUTTON
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230,230,230)));

        panel.add(scroll, BorderLayout.CENTER);

        loadBookings();

        return panel;
    }

    // ===== LOAD DATA =====
    private void loadBookings() {

        model.setRowCount(0);

        // ===== CONTROLLERS =====
        BookingController bookingController = new BookingController();
        ServiceController serviceController = new ServiceController();

        // ===== FETCH BOOKINGS =====
        List<Bookings> list = bookingController.getBookingsByUser(user.getId());

        for (Bookings b : list) {

            // ===== FETCH SERVICE FOR PRICE =====
            Service service = serviceController.getServiceById(b.getService_id());

            String priceText = "₹ --";

            if (service != null) {
                priceText = "₹ " + service.getPrice();
            }

            // ===== ADD ROW =====
            model.addRow(new Object[]{
                     b.getId(),
                    b.getServiceName(),
                    b.getProviderName(),
                    b.getBooking_date(),
                    priceText,
                    b.getStatus(),
                    getActionText(b.getStatus())
            });
        }
    }

    private String getActionText(String status) {
        switch (status.toLowerCase()) {
            case "completed": return "Review";
            case "pending": return "Cancel";
            case "in progress": return "Track";
            case "cancelled": return "Re-book";
            default: return "-";
        }
    }

    // ===== STATUS RENDERER =====
    class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            JLabel label = new JLabel(value.toString(), SwingConstants.CENTER);
            label.setOpaque(true);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));

            String status = value.toString().toLowerCase();

            switch (status) {
                case "completed":
                    label.setBackground(new Color(200,230,201));
                    label.setForeground(new Color(56,142,60));
                    break;

                case "pending":
                    label.setBackground(new Color(255,224,178));
                    label.setForeground(new Color(230,81,0));
                    break;

                case "in progress":
                    label.setBackground(new Color(187,222,251));
                    label.setForeground(new Color(25,118,210));
                    break;

                case "cancelled":
                    label.setBackground(new Color(255,205,210));
                    label.setForeground(new Color(198,40,40));
                    break;

                default:
                    label.setBackground(Color.LIGHT_GRAY);
            }

            return label;
        }
    }

    // ===== BUTTON RENDERER =====
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            setText(value.toString());
            setBackground(new Color(33,150,243));
            setForeground(Color.WHITE);

            return this;
        }
    }

    // ===== BUTTON EDITOR =====
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            button = new JButton();
            button.setFocusPainted(false);

            button.addActionListener(e -> handleAction(label));
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            label = value.toString();
            button.setText(label);
            return button;
        }

        public Object getCellEditorValue() {
            return label;
        }

        private void handleAction(String action) {
            JOptionPane.showMessageDialog(null, action + " clicked");
        }
    }
}