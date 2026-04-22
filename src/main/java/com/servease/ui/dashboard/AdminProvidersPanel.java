package com.servease.ui.dashboard;

import com.servease.controller.AdminProviderController;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class AdminProvidersPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private AdminProviderController controller = new AdminProviderController();

    public AdminProvidersPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Provider Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        add(title, BorderLayout.NORTH);

        String[] cols = {"ID","Name","Email","Phone","Status","Action"};

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

        List<Object[]> list = controller.getProviders();

        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    // ===== STATUS STYLE =====
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

                case "PENDING":
                    lbl.setBackground(new Color(255,224,178));
                    lbl.setForeground(new Color(230,81,0));
                    break;

                case "SUSPENDED":
                    lbl.setBackground(new Color(255,205,210));
                    lbl.setForeground(new Color(198,40,40));
                    break;

                case "REJECTED":
                    lbl.setBackground(new Color(220,220,220));
                    lbl.setForeground(Color.DARK_GRAY);
                    break;
            }

            return lbl;
        }
    }

    // ===== BUTTON RENDER =====
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            setText("Manage");
            setBackground(new Color(33,150,243));
            setForeground(Color.WHITE);
            return this;
        }
    }

    // ===== BUTTON ACTION =====
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private int id;

        public ButtonEditor(JCheckBox box) {
            super(box);

            button = new JButton("Manage");

            button.addActionListener(e -> {

                String[] options = {"Approve","Reject","Suspend"};
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Choose action",
                        "Provider Action",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice == 0) controller.approve(id);
                else if (choice == 1) controller.reject(id);
                else if (choice == 2) controller.suspend(id);

                loadData();
            });
        }

        public Component getTableCellEditorComponent(
                JTable table, Object value,
                boolean isSelected, int row, int column) {

            id = (int) table.getValueAt(row, 0);
            return button;
        }

        public Object getCellEditorValue() {
            return button.getText();
        }
    }
}