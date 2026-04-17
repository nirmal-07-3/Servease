package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;
import com.servease.ui.components.ServiceCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProviderServicesFrame extends JPanel {

    private User user;
    private JPanel container;
    private ServiceController controller;

    public ProviderServicesFrame(User user) {

        this.user = user;
        this.controller = new ServiceController();

        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));

        // ===== TOP BAR =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        JLabel title = new JLabel("My Services");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton addBtn = new JButton("+ Add Service");
        addBtn.setBackground(new Color(33,150,243));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        addBtn.addActionListener(e -> new AddServiceFrame(user, this));

        top.add(title, BorderLayout.WEST);
        top.add(addBtn, BorderLayout.EAST);

        // ===== CONTAINER =====
        container = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        container.setBackground(new Color(245,245,245));

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        loadServices();
    }

    // ===== LOAD SERVICES =====
    public void loadServices(){

        container.removeAll();

        List<Service> list = controller.getServicesByProviderId(user.getId());

        for(Service s : list){

            JPanel card = createServiceCard(s);
            container.add(card);
        }

        container.revalidate();
        container.repaint();
    }

    // ===== CARD DESIGN =====
    private JPanel createServiceCard(Service s){

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(260,200));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        // ===== IMAGE =====
        JLabel image = new JLabel();
        image.setPreferredSize(new Dimension(260,100));

        try{
            ImageIcon icon = new ImageIcon(s.getImagePath());
            Image img = icon.getImage().getScaledInstance(260,100,Image.SCALE_SMOOTH);
            image.setIcon(new ImageIcon(img));
        }catch(Exception e){
            image.setText("No Image");
        }

        // ===== INFO =====
        JLabel name = new JLabel(s.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel desc = new JLabel("<html>" + s.getDescription() + "</html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(Color.GRAY);

        JLabel price = new JLabel("₹ " + s.getPrice());
        price.setForeground(new Color(33,150,243));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);

        info.add(name);
        info.add(desc);
        info.add(price);

        // ===== ACTION BUTTONS =====
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBackground(Color.WHITE);

        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete");

        edit.setFocusPainted(false);
        delete.setFocusPainted(false);

        // EDIT
        edit.addActionListener(e -> {
            new UpdateServiceFrame((JFrame)
                    SwingUtilities.getWindowAncestor(this),s);
        });

        // DELETE
        delete.addActionListener(e -> {

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete this service?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if(confirm == JOptionPane.YES_OPTION){

                boolean res = controller.deleteService(s.getId(), user.getId());

                if(res){
                    JOptionPane.showMessageDialog(this,"Deleted");
                    loadServices();
                } else {
                    JOptionPane.showMessageDialog(this,"Failed");
                }
            }
        });

        actions.add(edit);
        actions.add(delete);

        // ===== ADD TO CARD =====
        card.add(image, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);

        return card;
    }
}