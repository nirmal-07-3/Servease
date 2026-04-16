package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {

    private User user;

    public UserDashboard(User user){

        this.user = user;

        setTitle("User Dashboard");
        setSize(800,500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new GridLayout(2,1,20,20));
        main.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        JButton browse = new JButton("Browse Services");
        JButton bookings = new JButton("My Bookings");

        main.add(browse);
        main.add(bookings);

        JButton logout = new JButton("Logout");

        add(main, BorderLayout.CENTER);
        add(logout, BorderLayout.SOUTH);

        browse.addActionListener(e -> new BrowseServicesFrame(user));
        bookings.addActionListener(e -> new UserBookingsFrame(user));

        logout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}