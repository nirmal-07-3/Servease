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
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServiceController serviceController=new ServiceController();

                List<Service> services=serviceController.getServicesByProviderId(user.getId());

                if(services.isEmpty()){
                    JOptionPane.showMessageDialog(null,"No Service Found");
                }
                else{
                    StringBuilder sb=new StringBuilder();

                    for(Service s: services){
                        sb.append("ID: ").append(s.getId());
                        sb.append(",Name: ").append(s.getName());
                        sb.append(",Price: ").append(s.getPrice());
                        sb.append(",\n\n");

                    }
                    JOptionPane.showMessageDialog(null,sb.toString());
                }
            }
        }

        );
    }
}