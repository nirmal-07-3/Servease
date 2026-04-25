package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.controller.ReviewController;
import com.servease.model.Bookings;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class UserBookingsFrame extends JPanel {

    private User user;
    private JTable table;
    private DefaultTableModel model;

    private BookingController bookingController;
    private ReviewController reviewController;

    public UserBookingsFrame(User user) {

        this.user = user;

        bookingController = new BookingController();
        reviewController = new ReviewController();

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

    // ===== TABLE =====
    private JPanel createTableSection() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245,247,250));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        String[] cols = {"Booking ID","Service","Provider","Date","Amount","Status","Action"};

        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));

        table.getColumn("Status").setCellRenderer(new StatusRenderer());
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230,230,230)));

        panel.add(scroll, BorderLayout.CENTER);

        loadBookings();

        return panel;
    }

    // ===== LOAD =====
    private void loadBookings() {

        model.setRowCount(0);

        List<Bookings> list = bookingController.getBookingsByUser(user.getId());

        for (Bookings b : list) {

            model.addRow(new Object[]{
                    b.getId(),
                    b.getServiceName(),
                    b.getProviderName(),
                    b.getBooking_date(),
                    "₹ " + b.getPrice(),   // 🔥 FIXED PRICE
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
            case "cancelled": return "-";
            default: return "-";
        }
    }

    // ===== STATUS =====
    class StatusRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            JLabel label = new JLabel(value.toString(), SwingConstants.CENTER);
            label.setOpaque(true);

            String status = value.toString().toLowerCase();

            switch (status) {
                case "completed":
                    label.setBackground(new Color(200,230,201));
                    break;
                case "pending":
                    label.setBackground(new Color(255,224,178));
                    break;
                case "in progress":
                    label.setBackground(new Color(187,222,251));
                    break;
                case "cancelled":
                    label.setBackground(new Color(255,205,210));
                    break;
            }

            return label;
        }
    }

    // ===== BUTTON =====
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() { setOpaque(true); }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            setText(value.toString());
            setForeground(Color.WHITE);

            if (value.equals("Cancel")) setBackground(Color.RED);
            else if (value.equals("Review")) setBackground(new Color(33,150,243));
            else setBackground(Color.GRAY);

            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.addActionListener(e -> handleAction());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            this.row = row;
            label = value.toString();
            button.setText(label);
            return button;
        }

        public Object getCellEditorValue() { return label; }

        private void handleAction() {

            int bookingId = (int) model.getValueAt(row, 0);

            if (label.equals("Review")) {
                openReviewDialog(bookingId);
            }

            else if (label.equals("Cancel")) {

                int confirm = JOptionPane.showConfirmDialog(null,
                        "Cancel booking?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    bookingController.updateBookingStatus(bookingId, "Cancelled");
                    loadBookings();
                }
            }

            fireEditingStopped();
        }
    }

    // ===== SINGLE REVIEW UI =====
    private void openReviewDialog(int bookingId) {

        JDialog dialog = new JDialog();
        dialog.setTitle("Review");
        dialog.setSize(350, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JTextField ratingField = new JTextField();
        JTextArea commentArea = new JTextArea(4,20);

        JButton submit = new JButton("Submit");

        submit.addActionListener(e -> {

            int rating = Integer.parseInt(ratingField.getText());
            String comment = commentArea.getText();


            List<Bookings> list = bookingController.getBookingsByUser(user.getId());

            for (Bookings b : list) {
                if (b.getId() == bookingId) {

                    reviewController.addReview(
                            bookingId,
                            b.getService_id(),
                            user.getId(),
                            0,
                            rating,
                            comment
                    );
                    break;
                }
            }

            dialog.dispose();
        });

        panel.add(new JLabel("Rating (1-5):"));
        panel.add(ratingField);
        panel.add(new JLabel("Comment:"));
        panel.add(new JScrollPane(commentArea));
        panel.add(submit);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}