package com.servease.ui.dashboard;

import com.servease.controller.AdminBookingController;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class AdminBookingsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private AdminBookingController controller = new AdminBookingController();

    public AdminBookingsPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Booking Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        add(title, BorderLayout.NORTH);

        String[] cols = {"ID","User","Service","Date","Price","Status","Action"};

        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));

        table.getColumn("Status").setCellRenderer(new StatusRenderer());
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<Object[]> list = controller.getAllBookings();
        for (Object[] row : list) model.addRow(row);
    }

    // STATUS STYLE
    class StatusRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {

            JLabel lbl = new JLabel(value.toString(), SwingConstants.CENTER);
            lbl.setOpaque(true);

            String s = value.toString().toLowerCase();

            if (s.equals("completed")) {
                lbl.setBackground(new Color(200,230,201));
            } else if (s.equals("pending")) {
                lbl.setBackground(new Color(255,224,178));
            } else if (s.equals("cancelled")) {
                lbl.setBackground(new Color(255,205,210));
            } else {
                lbl.setBackground(new Color(187,222,251));
            }

            return lbl;
        }
    }

    // BUTTON RENDER
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Update");
        }

        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int col) {
            return this;
        }
    }

    // BUTTON EDITOR
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private int id;

        public ButtonEditor(JCheckBox box) {
            super(box);

            button = new JButton("Update");

            button.addActionListener(e -> {

                String[] options = {"Pending","In Progress","Completed","Cancelled"};

                String status = (String) JOptionPane.showInputDialog(
                        null,
                        "Update Status",
                        "Select",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (status != null) {
                    controller.updateStatus(id, status);
                    loadData();
                }
            });
        }

        public Component getTableCellEditorComponent(
                JTable table, Object value,
                boolean isSelected, int row, int col) {

            id = (int) table.getValueAt(row, 0);
            return button;
        }

        public Object getCellEditorValue() {
            return "Update";
        }
    }
}