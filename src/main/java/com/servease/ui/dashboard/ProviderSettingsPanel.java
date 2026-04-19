package com.servease.ui.dashboard;

import com.servease.controller.UserController;
import com.servease.model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ProviderSettingsPanel extends JPanel {

    private User user;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;

    private JLabel imageLabel;
    private String imagePath = "";

    public ProviderSettingsPanel(User user) {

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
        header.setBorder(BorderFactory.createEmptyBorder(20,25,20,25));

        JLabel title = new JLabel("Account Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        header.add(title, BorderLayout.WEST);

        return header;
    }

    // ===== MAIN =====
    private JPanel createMain() {

        JPanel main = new JPanel(new GridLayout(1,2,20,20));
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        main.setBackground(new Color(245,247,250));

        main.add(createForm());
        main.add(createRight());

        return main;
    }

    // ===== LEFT FORM =====
    private JPanel createForm() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        nameField = new JTextField(user.getName());
        emailField = new JTextField(user.getEmail());
        phoneField = new JTextField(user.getPhone());
        passwordField = new JPasswordField();

        panel.add(label("Full Name"));
        panel.add(nameField);

        panel.add(label("Email"));
        panel.add(emailField);

        panel.add(label("Phone"));
        panel.add(phoneField);

        panel.add(label("New Password"));
        panel.add(passwordField);

        JButton save = new JButton("Save Changes");
        save.setBackground(new Color(33,150,243));
        save.setForeground(Color.WHITE);

        save.addActionListener(e -> handleSave());

        panel.add(Box.createVerticalStrut(15));
        panel.add(save);

        return panel;
    }

    // ===== RIGHT =====
    private JPanel createRight() {

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(new Color(245,247,250));

        right.add(profileCard());

        return right;
    }

    // ===== PROFILE CARD =====
    private JPanel profileCard() {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadProfileImage();

        JButton uploadBtn = new JButton("Change Photo");
        uploadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        uploadBtn.addActionListener(e -> chooseImage());

        JLabel name = new JLabel(user.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(imageLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(name);
        card.add(Box.createVerticalStrut(10));
        card.add(uploadBtn);

        return card;
    }

    // ===== IMAGE LOAD =====
    private void loadProfileImage() {

        try {
            if (user.getProfileImage() != null) {
                ImageIcon icon = new ImageIcon(user.getProfileImage());
                Image img = icon.getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            } else {
                imageLabel.setText("No Image");
            }
        } catch (Exception e) {
            imageLabel.setText("No Image");
        }
    }

    // ===== IMAGE SELECT =====
    private void chooseImage() {

        JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            try {
                File dir = new File("uploads");
                if (!dir.exists()) dir.mkdir();

                File dest = new File("uploads/" + file.getName());
                Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

                imagePath = dest.getPath();

                ImageIcon icon = new ImageIcon(imagePath);
                Image img = icon.getImage().getScaledInstance(80,80,Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Image upload failed");
            }
        }
    }

    // ===== SAVE =====
    private void handleSave() {

        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String pass = new String(passwordField.getPassword());

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name & Email required");
            return;
        }

        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);

        if (!imagePath.isEmpty()) {
            user.setProfileImage(imagePath);
        }

        UserController controller = new UserController();
        boolean success = controller.updateUser(user, pass);

        if (success) {
            JOptionPane.showMessageDialog(this, "Profile Updated");
        } else {
            JOptionPane.showMessageDialog(this, "Update Failed");
        }
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.GRAY);
        return l;
    }
}