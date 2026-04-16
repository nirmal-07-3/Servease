package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class ProviderServicesFrame extends JPanel {

    private User user;
    private JTable table;
    private DefaultTableModel model;
    private ServiceController controller;

    public ProviderServicesFrame(User user) {

        this.user = user;
        controller = new ServiceController();

        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));

        // ===== TOP =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        JLabel title = new JLabel("My Services");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton addBtn = new JButton("+ Add Service");
        addBtn.setBackground(new Color(33,150,243));
        addBtn.setForeground(Color.WHITE);

        top.add(title, BorderLayout.WEST);
        top.add(addBtn, BorderLayout.EAST);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID","Name","Description","Price","Action"},0
        );

        table = new JTable(model);
        table.setRowHeight(30);

        JScrollPane scroll = new JScrollPane(table);

        table.getColumn("Action").setCellRenderer(new Renderer());
        table.getColumn("Action").setCellEditor(new Editor(new JCheckBox(), table));

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        loadServices();

        addBtn.addActionListener(e -> new AddServiceFrame(user,this));
    }

    void loadServices(){

        model.setRowCount(0);

        List<Service> list = controller.getServicesByProviderId(user.getId());

        for(Service s : list){
            model.addRow(new Object[]{
                    s.getId(),
                    s.getName(),
                    s.getDescription(),
                    s.getPrice(),
                    ""
            });
        }
    }

    // ===== BUTTON RENDER =====
    class Renderer extends JPanel implements TableCellRenderer {

        JButton edit = new JButton("✏️");
        JButton delete = new JButton("🗑️");

        public Renderer(){
            setLayout(new FlowLayout());
            add(edit);
            add(delete);
        }

        public Component getTableCellRendererComponent(JTable table,Object value,
                                                       boolean isSelected,boolean hasFocus,
                                                       int row,int col){
            return this;
        }
    }

    // ===== BUTTON ACTION =====
    class Editor extends DefaultCellEditor {

        JPanel panel;
        JButton edit,delete;
        JTable table;

        public Editor(JCheckBox box,JTable table){
            super(box);
            this.table = table;

            panel = new JPanel(new FlowLayout());

            edit = new JButton("✏️");
            delete = new JButton("🗑️");

            panel.add(edit);
            panel.add(delete);

            // EDIT
            edit.addActionListener(e -> {

                int row = table.getSelectedRow();

                Service s = new Service(
                        (int)table.getValueAt(row,0),
                        user.getId(),
                        (String)table.getValueAt(row,1),
                        (String)table.getValueAt(row,2),
                        Double.parseDouble(table.getValueAt(row,3).toString())
                );

                new UpdateServiceFrame(s, ProviderServicesFrame.this);
            });

            // DELETE
            delete.addActionListener(e -> {

                int row = table.getSelectedRow();

                int confirm = JOptionPane.showConfirmDialog(null,"Delete?");

                if(confirm==0){

                    int id = (int)table.getValueAt(row,0);

                    boolean res = controller.deleteService(id,user.getId());

                    if(res){
                        JOptionPane.showMessageDialog(null,"Deleted");
                        loadServices();
                    }else{
                        JOptionPane.showMessageDialog(null,"Failed");
                    }
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table,Object value,
                                                     boolean isSelected,int row,int col){
            return panel;
        }

        public Object getCellEditorValue(){ return ""; }
    }
}