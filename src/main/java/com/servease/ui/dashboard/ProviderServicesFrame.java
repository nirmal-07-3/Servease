package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;
import com.servease.ui.dashboard.AddServiceFrame;
import com.servease.ui.dashboard.UpdateServiceFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProviderServicesFrame extends JFrame {

    private User user;
    private JTable table;
    private DefaultTableModel model;

    public ProviderServicesFrame(User user) {

        this.user = user;

        setTitle("My Services");
        setSize(650, 400);
        setLayout(null);

        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Price"}, 0
        );

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 20, 600, 200);
        add(scroll);

        JButton addBtn = new JButton("Add Service");
        addBtn.setBounds(20, 250, 150, 40);
        add(addBtn);

        JButton editBtn = new JButton("Edit");
        editBtn.setBounds(200, 250, 150, 40);
        add(editBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(380, 250, 150, 40);
        add(deleteBtn);

        // 🔥 ADD
        addBtn.addActionListener(e -> {
            new AddServiceFrame(user, this);
        });

        // 🔥 EDIT
        editBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            int id = (int) model.getValueAt(row, 0);
            String name = (String) model.getValueAt(row, 1);
            double price = (double) model.getValueAt(row, 2);

            Service service = new Service(id, user.getId(), name, "", price);

            new UpdateServiceFrame(service, this);
        });

        // 🔥 DELETE
        deleteBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            int id = (int) model.getValueAt(row, 0);

            ServiceController controller = new ServiceController();
            boolean result = controller.deleteService(id,user.getId());

            if (result) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully");
                loadServices();
            } else {
                JOptionPane.showMessageDialog(this, "Delete Failed");
            }
        });

        loadServices();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 🔥 ONLY ONE LOAD METHOD
    public void loadServices() {

        model.setRowCount(0);

        ServiceController controller = new ServiceController();
        List<Service> services = controller.getServicesByProviderId(user.getId());
        int count=1;
        for (Service s : services) {
            model.addRow(new Object[]{
                    count++,
                    s.getName(),
                    s.getPrice()
            });
        }
    }
}