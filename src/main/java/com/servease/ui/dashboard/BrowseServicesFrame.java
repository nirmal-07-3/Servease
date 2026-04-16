package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;
import com.servease.ui.components.ServiceCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BrowseServicesFrame extends JFrame {

    private User user;

    public BrowseServicesFrame(User user) {

        this.user = user;

        setTitle("Browse Services");
        setSize(800, 500);
        setLayout(new BorderLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(245,245,245));

        JScrollPane scroll = new JScrollPane(listPanel);

        ServiceController controller = new ServiceController();
        List<Service> services = controller.getAllServices();

        for(Service s : services){
            listPanel.add(new ServiceCard(
                    s.getName(),
                    s.getDescription(),
                    s.getPrice()
            ));
            listPanel.add(Box.createVerticalStrut(10));
        }

        add(scroll, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}