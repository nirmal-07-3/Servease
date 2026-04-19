package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProviderServicesPanel extends JPanel {

    private User user;
    private JPanel listPanel;
    private ServiceController controller = new ServiceController();

    public ProviderServicesPanel(User user) {

        this.user = user;

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        add(createHeader(), BorderLayout.NORTH);
        add(createMain(), BorderLayout.CENTER);
    }

    // ===== HEADER =====
    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        JLabel title = new JLabel("My Services");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        header.add(title, BorderLayout.WEST);

        return header;
    }

    // ===== MAIN =====
    private JPanel createMain() {

        JPanel main = new JPanel(new GridLayout(1,2,20,20));
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        main.setBackground(new Color(245,247,250));

        main.add(createServiceList());
        main.add(createAddServiceForm());

        return main;
    }

    // ===== LEFT: SERVICE LIST =====
    private JComponent createServiceList() {

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(BorderFactory.createTitledBorder("Your Services"));

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);

        loadServices();

        return scroll;
    }

    private void loadServices() {

        listPanel.removeAll();

        List<Service> services = controller.getServicesByProviderId(user.getId());

        for (Service s : services) {
            listPanel.add(createServiceCard(s));
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    // ===== SERVICE CARD =====
    private JPanel createServiceCard(Service s) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        JLabel name = new JLabel(s.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel price = new JLabel("₹ " + s.getPrice());
        price.setForeground(new Color(33,150,243));

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setForeground(Color.WHITE);

        deleteBtn.addActionListener(e -> {
            controller.deleteService(s.getId(),user.getId());
            loadServices();
        });

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.add(name, BorderLayout.WEST);
        top.add(price, BorderLayout.EAST);

        card.add(top, BorderLayout.NORTH);
        card.add(deleteBtn, BorderLayout.SOUTH);

        return card;
    }

    // ===== RIGHT: ADD SERVICE FORM =====
    private JPanel createAddServiceForm() {

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder("Add New Service"));

        JTextField nameField = new JTextField();
        JTextArea descField = new JTextArea(3, 20);
        JTextField priceField = new JTextField();

        form.add(label("Service Name"));
        form.add(nameField);

        form.add(label("Description"));
        form.add(descField);

        form.add(label("Price"));
        form.add(priceField);

        JButton addBtn = new JButton("Add Service");
        addBtn.setBackground(new Color(33,150,243));
        addBtn.setForeground(Color.WHITE);

        addBtn.addActionListener(e -> {

            try {
                Service s = new Service();

                s.setName(nameField.getText());
                s.setDescription(descField.getText());
                s.setPrice(Double.parseDouble(priceField.getText()));
                s.setProvider_id(user.getId());

                controller.addService(s);

                JOptionPane.showMessageDialog(this, "Service Added");

                nameField.setText("");
                descField.setText("");
                priceField.setText("");

                loadServices();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
            }
        });

        form.add(Box.createVerticalStrut(10));
        form.add(addBtn);

        return form;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.GRAY);
        return l;
    }
}