package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardHome extends JPanel {

    public DashboardHome(User user){

        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));

        JPanel top = new JPanel(new GridLayout(1,4,15,15));
        top.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        ServiceController sc = new ServiceController();
        List<Service> list = sc.getServicesByProviderId(user.getId());

        top.add(card(String.valueOf(list.size()),"Services"));
        top.add(card("67","Bookings"));
        top.add(card("4.7","Rating"));
        top.add(card("₹4280","Revenue"));

        add(top,BorderLayout.NORTH);
    }

    private JPanel card(String value,String text){
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel v = new JLabel(value);
        v.setFont(new Font("Arial",Font.BOLD,22));

        JLabel t = new JLabel(text);

        p.add(v,BorderLayout.CENTER);
        p.add(t,BorderLayout.SOUTH);

        return p;
    }
}