package com.servease.ui.dashboard;

import com.servease.controller.ServiceController;
import com.servease.model.Service;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProviderServicesPanel extends JPanel {

    private User user;
    private ServiceController controller = new ServiceController();

    private Service service=new Service();
    private JPanel listPanel;

    public ProviderServicesPanel(User user) {
        this.user = user;

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        add(createHeader(), BorderLayout.NORTH);
        add(createMain(), BorderLayout.CENTER);
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        JLabel title = new JLabel("My Services");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton addBtn = new JButton("+ Add New Service");
        addBtn.setBackground(new Color(33,150,243));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);

        addBtn.addActionListener(e -> openServiceForm(null));

        top.add(title, BorderLayout.WEST);
        top.add(addBtn, BorderLayout.EAST);

        return top;
    }

    // ================= MAIN =================
    private JPanel createMain() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245,247,250));
        panel.add(createListPanel(), BorderLayout.CENTER);
        return panel;
    }

    // ================= SERVICE LIST =================
    private JScrollPane createListPanel() {

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(245,247,250));

        loadServices();

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);

        return scroll;
    }

    private void loadServices() {

        listPanel.removeAll();

        List<Service> services = controller.getServicesByProviderId(user.getId());

        if (services.isEmpty()) {
            JLabel empty = new JLabel("No services added yet.");
            empty.setForeground(Color.GRAY);
            empty.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            listPanel.add(empty);
        }

        for (Service s : services) {
            listPanel.add(createCard(s));
            listPanel.add(Box.createVerticalStrut(12));
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    // ================= CARD UI =================
    private JPanel createCard(Service s) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        // LEFT
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(Color.WHITE);

        JLabel name = new JLabel(s.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel desc = new JLabel("<html><small>" + s.getDescription() + "</small></html>");
        desc.setForeground(Color.GRAY);

        left.add(name);
        left.add(desc);

        // CENTER
        JPanel center = new JPanel(new GridLayout(1,2,20,0));
        center.setBackground(Color.WHITE);

        center.add(new JLabel("₹ " + s.getPrice()));

        double rating = service.getRating();

        JLabel ratingLabel;

        if (rating == 0) {
            ratingLabel = new JLabel("☆ No ratings yet");
            ratingLabel.setForeground(Color.GRAY);
        } else {
            ratingLabel = new JLabel("★ " + String.format("%.1f", rating));
            ratingLabel.setForeground(new Color(255, 193, 7)); // gold color
        }

        // RIGHT ACTIONS
        JPanel right = new JPanel();
        right.setBackground(Color.WHITE);

        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete");

        edit.setBackground(new Color(33,150,243));
        edit.setForeground(Color.WHITE);

        delete.setBackground(new Color(244,67,54));
        delete.setForeground(Color.WHITE);

        // EDIT
        edit.addActionListener(e -> openServiceForm(s));

        // DELETE (with confirmation)
        delete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this service?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = controller.deleteService(s.getId(), user.getId());

                if (success) {
                    JOptionPane.showMessageDialog(this, "Service deleted successfully");
                    loadServices();
                } else {
                    JOptionPane.showMessageDialog(this, "Delete failed");
                }
            }
        });

        right.add(edit);
        right.add(delete);

        card.add(left, BorderLayout.WEST);
        card.add(center, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);

        return card;
    }

    // ================= FORM DIALOG =================
    private void openServiceForm(Service service) {

        JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                service == null ? "Add Service" : "Edit Service",
                true
        );

        dialog.setSize(400, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        form.setBackground(Color.WHITE);

        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextArea descField = new JTextArea(4,20);

        int editingId = -1;

        if (service != null) {
            editingId = service.getId();
            nameField.setText(service.getName());
            priceField.setText(String.valueOf(service.getPrice()));
            descField.setText(service.getDescription());
        }

        JButton save = new JButton(service == null ? "Add Service" : "Save Changes");
        save.setBackground(new Color(33,150,243));
        save.setForeground(Color.WHITE);

        int finalEditingId = editingId;

        save.addActionListener(e -> {

            try {
                String name = nameField.getText().trim();
                String priceText = priceField.getText().trim();
                String desc = descField.getText().trim();

                // VALIDATION
                if (name.isEmpty() || priceText.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields required");
                    return;
                }

                double price = Double.parseDouble(priceText);

                Service s = new Service();
                s.setProvider_id(user.getId());
                s.setName(name);
                s.setPrice(price);
                s.setDescription(desc);

                boolean success;

                if (finalEditingId == -1) {
                    success = controller.addService(s);
                } else {
                    s.setId(finalEditingId);
                    success = controller.updateService(s);
                }

                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Saved successfully");
                    dialog.dispose();
                    loadServices();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Operation failed");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input");
            }
        });

        form.add(new JLabel("Service Name"));
        form.add(nameField);
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("Price"));
        form.add(priceField);
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("Description"));
        form.add(new JScrollPane(descField));
        form.add(Box.createVerticalStrut(20));

        form.add(save);

        dialog.add(form, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}