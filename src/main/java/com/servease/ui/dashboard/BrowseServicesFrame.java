package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;
import com.servease.ui.components.ServiceCard;
import com.servease.ui.components.ServiceDetailsDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BrowseServicesFrame extends JFrame {

    private User user;

    public BrowseServicesFrame(User user) {

        this.user = user;

        setTitle("Browse Services");
        setSize(900,600);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new FlowLayout(FlowLayout.LEFT,15,15));
        main.setBackground(new Color(245,245,245));

        JScrollPane scroll = new JScrollPane(main);
        scroll.setBorder(null);

        add(scroll);

        loadServices(main);

        setVisible(true);
    }

    private void loadServices(JPanel panel){

        ServiceController controller = new ServiceController();
        List<Service> services = controller.getAllServices();

        for(Service s : services){

            ServiceCard card = new ServiceCard(s, () -> {
                new ServiceDetailsDialog(this, s, user);
            });

            panel.add(card);
        }
    }
}