package com.servease.ui.components;

import com.servease.controller.BookingController;
import com.servease.model.Bookings;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class ServiceDetailsDialog extends JDialog {

    public ServiceDetailsDialog(JFrame parent, Service service, User user) {

        super(parent, "Service Details", true);
        setSize(400,500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        main.setBackground(Color.WHITE);

        // IMAGE
        JLabel img = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(service.getImagePath());
            Image image = icon.getImage().getScaledInstance(350,150,Image.SCALE_SMOOTH);
            img.setIcon(new ImageIcon(image));
        } catch(Exception e){
            img.setText("No Image");
        }

        // TEXT
        JLabel name = new JLabel(service.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel desc = new JLabel("<html>" + service.getDescription() + "</html>");

        JLabel price = new JLabel("₹ " + service.getPrice());
        price.setForeground(new Color(33,150,243));
        price.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // BUTTON
        JButton bookBtn = new JButton("Book Now");
        bookBtn.setBackground(new Color(33,150,243));
        bookBtn.setForeground(Color.WHITE);

        bookBtn.addActionListener(e -> {
            try {
                Bookings booking = new Bookings(
                        user.getId(),
                        service.getId(),
                        new Date(System.currentTimeMillis()),
                        "Pending"
                );

                BookingController controller = new BookingController();
                boolean res = controller.bookService(booking);

                if(res){
                    JOptionPane.showMessageDialog(this, "Booked!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed!");
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
        });

        // ADD
        main.add(img);
        main.add(Box.createVerticalStrut(10));
        main.add(name);
        main.add(Box.createVerticalStrut(5));
        main.add(desc);
        main.add(Box.createVerticalStrut(10));
        main.add(price);
        main.add(Box.createVerticalStrut(20));
        main.add(bookBtn);

        add(main, BorderLayout.CENTER);

        setVisible(true);
    }
}