package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class ProviderBookingsPanel extends JPanel {

    private User user;
    private JTable table;
    private DefaultTableModel model;
    private BookingController controller = new BookingController();
    private String currentFilter = "All";

    public ProviderBookingsPanel(User user) {
        this.user = user;

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        add(createHeader(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadData();
    }

    // ================= HEADER =================
    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JLabel title = new JLabel("Bookings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        filters.setBackground(Color.WHITE);

        filters.add(createFilter("All"));
        filters.add(createFilter("Pending"));
        filters.add(createFilter("Accepted"));
        filters.add(createFilter("Completed"));
        filters.add(createFilter("Rejected"));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(title, BorderLayout.NORTH);
        wrapper.add(filters, BorderLayout.SOUTH);

        header.add(wrapper, BorderLayout.CENTER);

        return header;
    }

    private JButton createFilter(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(230,230,230));
        btn.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));

        btn.addActionListener(e -> {
            currentFilter = text;
            loadData();
        });

        return btn;
    }

    // ================= TABLE =================
    private JScrollPane createTablePanel() {

        String[] cols = {
                "Booking ID","Customer","Service",
                "Date","Amount","Status","Action"
        };

        model = new DefaultTableModel(cols,0){
            public boolean isCellEditable(int r,int c){
                return c==6;
            }
        };

        table = new JTable(model);
        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // 🔥 REMOVE GRID (IMPORTANT)
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));

        // HEADER STYLE
        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(245,247,250));
        th.setFont(new Font("Segoe UI", Font.BOLD, 13));
        th.setBorder(null);

        // STATUS CHIP
        table.getColumn("Status").setCellRenderer(new StatusRenderer());

        // BUTTON
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor());

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));

        return sp;
    }

    // ================= LOAD =================
    private void loadData() {

        model.setRowCount(0);

        List<Object[]> list =
                controller.getBookingsByProvider(user.getId());

        for(Object[] b : list){

            String status = b[5].toString();

            if(!currentFilter.equals("All") &&
                    !status.equalsIgnoreCase(currentFilter)) continue;

            model.addRow(new Object[]{
                    "BK-"+b[0],
                    b[1],
                    b[2],
                    b[3],
                    "₹ "+b[4],
                    status,
                    ""
            });
        }
    }

    // ================= STATUS CHIP =================
    class StatusRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(
                JTable table,Object value,boolean isSelected,
                boolean hasFocus,int row,int column){

            JLabel lbl = new JLabel(value.toString(),SwingConstants.CENTER);
            lbl.setOpaque(true);
            lbl.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

            String s = value.toString();

            if(s.equalsIgnoreCase("Pending")){
                lbl.setBackground(new Color(255,243,205));
            }
            else if(s.equalsIgnoreCase("Accepted")){
                lbl.setBackground(new Color(209,236,241));
            }
            else if(s.equalsIgnoreCase("Completed")){
                lbl.setBackground(new Color(212,237,218));
            }
            else if(s.equalsIgnoreCase("Rejected")){
                lbl.setBackground(new Color(248,215,218));
            }

            return lbl;
        }
    }

    // ================= BUTTON RENDER =================
    class ButtonRenderer extends JPanel implements TableCellRenderer {

        JButton btn = new JButton();

        public ButtonRenderer(){
            setLayout(new FlowLayout());
            setBackground(Color.WHITE);

            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            add(btn);
        }

        public Component getTableCellRendererComponent(
                JTable table,Object value,boolean isSelected,
                boolean hasFocus,int row,int col){

            String status = table.getValueAt(row,5).toString();

            if(status.equals("Pending")){
                btn.setText("Accept");
                btn.setBackground(new Color(40,167,69));
            }
            else if(status.equals("Accepted")){
                btn.setText("Complete");
                btn.setBackground(new Color(0,123,255));
            }
            else{
                btn.setText("Done");
                btn.setBackground(new Color(108,117,125));
            }

            return this;
        }
    }

    // ================= BUTTON EDIT =================
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

        JButton btn = new JButton();
        int row;

        public ButtonEditor(){
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);

            btn.addActionListener(e -> {

                int id = Integer.parseInt(
                        table.getValueAt(row,0)
                                .toString().replace("BK-",""));

                String status = table.getValueAt(row,5).toString();

                if(status.equals("Pending")){
                    controller.updateBookingStatus(id,"Accepted");
                }
                else if(status.equals("Accepted")){
                    controller.updateBookingStatus(id,"Completed");
                }

                loadData();
            });
        }

        public Component getTableCellEditorComponent(
                JTable table,Object value,boolean isSelected,
                int row,int col){

            this.row=row;

            String status = table.getValueAt(row,5).toString();

            if(status.equals("Pending")){
                btn.setText("Accept");
                btn.setBackground(new Color(40,167,69));
            }
            else if(status.equals("Accepted")){
                btn.setText("Complete");
                btn.setBackground(new Color(0,123,255));
            }
            else{
                btn.setText("Done");
                btn.setBackground(new Color(108,117,125));
            }

            return btn;
        }

        public Object getCellEditorValue(){
            return "";
        }
    }
}