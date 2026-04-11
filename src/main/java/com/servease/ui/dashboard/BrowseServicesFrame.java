package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BrowseServicesFrame extends JFrame {

    private Service service;
    private ProviderServicesFrame parent;
    private User user;
    private JTable table;
    private DefaultTableModel model;

    private JTextField nameField,descriptionField, priceField;

    public BrowseServicesFrame(User user) {

        this.user = user;

        setTitle("Browse Services");
        setSize(650, 400);
        setLayout(null);

        model = new DefaultTableModel(
                new String[]{"Service ID", "Name", "Description", "Price"}, 0
        );

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 20, 600, 250);
        add(scroll);

        loadServices();

        setLocationRelativeTo(null);
        setVisible(true);

        JButton bookingBtn = new JButton("Book");
        bookingBtn.setBounds(130, 140, 120, 30);
        add(bookingBtn);

        bookingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null,"Service Booked");
            }


        });
    }


    // 🔥 LOAD ALL SERVICES (NO FILTER)
    private void loadServices() {

        model.setRowCount(0);

        ServiceController controller = new ServiceController();
        List<Service> services = controller.getAllServices(); // 🔥 important
            int count=1;
        for (Service s : services) {
            model.addRow(new Object[]{
                    count++,
                    s.getName(),
                    s.getDescription(),
                    s.getPrice()
            });
        }
    }
}