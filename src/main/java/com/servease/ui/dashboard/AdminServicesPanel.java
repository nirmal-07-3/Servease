package com.servease.ui.dashboard;

import com.servease.controller.AdminServiceController;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class AdminServicesPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private AdminServiceController controller = new AdminServiceController();

    public AdminServicesPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Service Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        add(title, BorderLayout.NORTH);

        String[] cols = {"ID","Service","Description","Price","Provider","Action"};

        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));

        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);

        List<Object[]> list = controller.getAllServices();

        for (Object[] row : list) {
            model.addRow(row);
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

            setText("Delete");
            setBackground(new Color(244,67,54));
            setForeground(Color.WHITE);
            return this;
        }
    }

    // ===== BUTTON EDITOR =====
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private int serviceId;

        public ButtonEditor(JCheckBox box) {
            super(box);

            button = new JButton("Delete");

            button.addActionListener(e -> {

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Delete this service?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deleteService(serviceId);
                    loadData();
                }
            });
        }

        public Component getTableCellEditorComponent(
                JTable table, Object value,
                boolean isSelected, int row, int column) {

            serviceId = (int) table.getValueAt(row, 0);
            return button;
        }

        public Object getCellEditorValue() {
            return button.getText();
        }
    }
}