package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProviderDashboard extends JFrame {


        private User user;
    public ProviderDashboard(User user) {
        this.user=user;
        setTitle("Provider Dashboard");
        setSize(300, 200);
        add(new JLabel("Welcome "+user.getName()+"!", SwingConstants.CENTER));
        setVisible(true);


        JButton btn=new JButton("My Services");
        add(btn);
        btn.addActionListener(e ->  {
            new ProviderServicesFrame(user);


                              });

    }
}