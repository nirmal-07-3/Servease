package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProviderServicesFrame extends JFrame {

    private User user;
    private JTable table;
    private DefaultTableModel model;

    public ProviderServicesFrame(User user) {

        this.user = user;

        setTitle("My Services");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 🔹 Table Columns
        String[] columns = {"ID", "Name", "Price","Edit", "Delete"};

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 540, 250);
        add(scrollPane);

        loadServices();

        //Edit Button
        JButton editBtn=new JButton("Edit");
        editBtn.setBounds(50,300,150,40);
        add(editBtn);

        editBtn.addActionListener(e -> {
            int row=table.getSelectedRow();
            if(row==-1){
                JOptionPane.showMessageDialog(null,"Select a row First");
                return;
            }
            int id=(int)model.getValueAt(row,0);
            String name = (String) model.getValueAt(row, 1);
            double price = (double) model.getValueAt(row, 2);

            // Temporary (no description in table)
            Service service = new Service(id, user.getId(), name, "", price);

            new UpdateServiceFrame(service);

            loadServices(); // refresh after edit

        });


        // 🔥 DELETE BUTTON ACTION
        JButton deleteBtn = new JButton("Delete Service");
        deleteBtn.setBounds(200, 300, 180, 40);
        add(deleteBtn);

        deleteBtn.addActionListener(e -> deleteSelectedService());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 🔥 LOAD SERVICES
    private void loadServices() {

        ServiceController controller = new ServiceController();
        List<Service> services = controller.getServicesByProviderId(user.getId());

        model.setRowCount(0); // clear old

        for (Service s : services) {
            model.addRow(new Object[]{
                    s.getId(),
                    s.getName(),
                    s.getPrice(),
                    "Edit",
                    "Delete"
            });
        }
    }

    // 🔥 DELETE LOGIC
    private void deleteSelectedService() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first");
            return;
        }

        int serviceId = (int) model.getValueAt(row, 0);
        System.out.println("Deleted id:"+serviceId);

        ServiceController controller = new ServiceController();
        boolean result = controller.deleteService(serviceId);

        if (result) {
            JOptionPane.showMessageDialog(this, "Deleted Successfully");
            loadServices(); // refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Delete Failed");
        }
    }

    //Edit logic



}