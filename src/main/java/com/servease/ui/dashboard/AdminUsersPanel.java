package com.servease.ui.dashboard;

import com.servease.controller.AdminUserController;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class AdminUsersPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private AdminUserController controller = new AdminUserController();

    public AdminUsersPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // ===== HEADER =====
        JLabel title = new JLabel("User Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] cols = {"ID","Name","Email","Role","Status","Action"};

        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));

        // ===== STATUS COLOR =====
        table.getColumn("Status").setCellRenderer(new StatusRenderer());

        // ===== ACTION BUTTON =====
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230,230,230)));

        add(scroll, BorderLayout.CENTER);

        loadData();
    }

    // ===== LOAD USERS =====
    private void loadData() {
        model.setRowCount(0);

        List<Object[]> list = controller.getUsers();

        for (Object[] u : list) {
            model.addRow(u);
        }
    }

    // ===== STATUS RENDER =====
    class StatusRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            JLabel lbl = new JLabel(value.toString(), SwingConstants.CENTER);
            lbl.setOpaque(true);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));

            String status = value.toString().toUpperCase();

            switch (status) {
                case "ACTIVE":
                    lbl.setBackground(new Color(200,230,201));
                    lbl.setForeground(new Color(56,142,60));
                    break;

                case "SUSPENDED":
                    lbl.setBackground(new Color(255,205,210));
                    lbl.setForeground(new Color(198,40,40));
                    break;

                case "PENDING":
                    lbl.setBackground(new Color(255,224,178));
                    lbl.setForeground(new Color(230,81,0));
                    break;

                default:
                    lbl.setBackground(Color.LIGHT_GRAY);
            }

            return lbl;
        }
    }

    // ===== BUTTON RENDER =====
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            String status = table.getValueAt(row, 4).toString();

            if (status.equalsIgnoreCase("ACTIVE")) {
                setText("Suspend");
                setBackground(new Color(244,67,54));
            } else {
                setText("Activate");
                setBackground(new Color(76,175,80));
            }

            setForeground(Color.WHITE);
            return this;
        }
    }

    // ===== BUTTON EDITOR =====
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private int userId;
        private String status;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            button = new JButton();
            button.setFocusPainted(false);

            button.addActionListener(e -> {

                if (status.equalsIgnoreCase("ACTIVE")) {

                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Suspend this user?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        controller.suspendUser(userId);
                    }

                } else {

                    controller.activateUser(userId);
                }

                loadData(); // refresh
            });
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value,
                boolean isSelected, int row, int column) {

            userId = (int) table.getValueAt(row, 0);
            status = table.getValueAt(row, 4).toString();

            if (status.equalsIgnoreCase("ACTIVE")) {
                button.setText("Suspend");
                button.setBackground(new Color(244,67,54));
            } else {
                button.setText("Activate");
                button.setBackground(new Color(76,175,80));
            }

            button.setForeground(Color.WHITE);

            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }
    }
}