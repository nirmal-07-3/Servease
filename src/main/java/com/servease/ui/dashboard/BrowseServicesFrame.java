package com.servease.ui.dashboard;

import com.servease.controller.BookingController;
import com.servease.controller.ServiceController;
import com.servease.model.Bookings;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
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


        JButton bookingBtn = new JButton("Book");
        bookingBtn.setBounds(250, 300, 120, 30);
        add(bookingBtn);

        bookingBtn.addActionListener(e -> {

            int selectedRow=table.getSelectedRow();

            if(selectedRow==-1){
                JOptionPane.showMessageDialog(null,"Please Select a Service");
                return;
            }
            int serviceId=(int)model.getValueAt(selectedRow,0);
            try {
                Date date=new Date(System.currentTimeMillis());

                Bookings bookings=new Bookings(user.getId(),serviceId,date,"Pending");


                BookingController bookingController=new BookingController();

                boolean result =bookingController.bookService(bookings);

                if(result){
                    JOptionPane.showMessageDialog(null,"Service Booked Successfully !");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Booking Failed !");

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"This Error Occurred");

            }

        }


        );
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
        setVisible(true);
    }

}