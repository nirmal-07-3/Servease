package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class ProviderBookingsFrame extends JPanel {

    private User user;
    private JTable table;
    private DefaultTableModel model;

    public ProviderBookingsFrame(User user) {

        this.user = user;

        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));

        JLabel title = new JLabel("My Bookings");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        model = new DefaultTableModel(
                new String[]{"ID","Service","User","Date","Status","Action"},0
        );

        table = new JTable(model);
        table.setRowHeight(30);

        JScrollPane scroll = new JScrollPane(table);

        table.getColumn("Action").setCellRenderer(new Renderer());
        table.getColumn("Action").setCellEditor(new Editor(new JCheckBox(), table));

        add(title, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        loadBookings();
    }

    private void loadBookings(){

        model.setRowCount(0);

        BookingController controller = new BookingController();
        List<Object[]> list = controller.getBookingsByProvider(user.getId());

        for(Object[] row : list){
            model.addRow(new Object[]{
                    row[0],row[1],row[2],row[3],row[4],""
            });
        }
    }

    // ===== RENDER BUTTONS =====
    class Renderer extends JPanel implements TableCellRenderer {

        JButton a = new JButton("✔");
        JButton r = new JButton("✖");
        JButton c = new JButton("✓");

        public Renderer(){
            setLayout(new FlowLayout());
            add(a); add(r); add(c);
        }

        public Component getTableCellRendererComponent(JTable t,Object v,
                                                       boolean s,boolean f,int r,int c){
            return this;
        }
    }

    // ===== BUTTON ACTION =====
    class Editor extends DefaultCellEditor {

        JPanel panel;
        JTable table;

        public Editor(JCheckBox box,JTable table){
            super(box);
            this.table = table;

            panel = new JPanel(new FlowLayout());

            JButton a = new JButton("✔");
            JButton r = new JButton("✖");
            JButton c = new JButton("✓");

            panel.add(a); panel.add(r); panel.add(c);

            a.addActionListener(e -> update("Accepted"));
            r.addActionListener(e -> update("Rejected"));
            c.addActionListener(e -> update("Completed"));
        }

        private void update(String status){

            int row = table.getEditingRow();
            int id = (int)table.getValueAt(row,0);

            BookingController controller = new BookingController();
            controller.updateBookingStatus(id,status);

            loadBookings();
        }

        public Component getTableCellEditorComponent(JTable t,Object v,
                                                     boolean s,int r,int c){
            return panel;
        }

        public Object getCellEditorValue(){ return ""; }
    }
}