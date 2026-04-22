package com.servease.ui.dashboard;

import com.servease.model.User;
import com.servease.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AdminSettingsPanel extends JPanel {

    private User user;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;

    private JLabel avatarLabel;

    public AdminSettingsPanel(User user) {
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
        header.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(Color.WHITE);

        JLabel title = new JLabel("Admin Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel sub = new JLabel("Manage admin profile and preferences");
        sub.setForeground(Color.GRAY);

        left.add(title);
        left.add(sub);

        header.add(left, BorderLayout.WEST);

        return header;
    }

    // ===== MAIN =====
    private JPanel createMain() {
        JPanel main = new JPanel(new GridLayout(1, 2, 20, 20));
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        main.setBackground(new Color(245,247,250));

        main.add(createForm());
        main.add(createRight());

        return main;
    }

    // ===== FORM =====
    private JPanel createForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        nameField = new JTextField(user.getName());
        emailField = new JTextField(user.getEmail());
        phoneField = new JTextField(user.getPhone());
        passwordField = new JPasswordField();

        panel.add(createField("Full Name", nameField, "icons/user.png"));
        panel.add(createField("Email", emailField, "icons/email.png"));
        panel.add(createField("Phone", phoneField, "icons/phone.png"));
        panel.add(createField("New Password", passwordField, "icons/lock.png"));

        JButton save = new JButton("Save Changes");
        save.setBackground(new Color(33,150,243));
        save.setForeground(Color.WHITE);
        save.setFocusPainted(false);
        save.setCursor(new Cursor(Cursor.HAND_CURSOR));

        save.addActionListener(e -> handleSave());

        panel.add(Box.createVerticalStrut(15));
        panel.add(save);

        return panel;
    }

    // ===== FIELD =====
    private JPanel createField(String label, JComponent input, String iconPath) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        JLabel l = new JLabel(label);
        l.setForeground(Color.GRAY);

        JPanel fieldBox = new JPanel(new BorderLayout());
        fieldBox.setBackground(new Color(250,250,250));
        fieldBox.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        JLabel iconLabel = new JLabel();
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0,8,0,8));

        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(16,16,Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Icon missing: " + iconPath);
        }

        input.setBorder(BorderFactory.createEmptyBorder(8,5,8,5));

        fieldBox.add(iconLabel, BorderLayout.WEST);
        fieldBox.add(input, BorderLayout.CENTER);

        wrapper.add(l, BorderLayout.NORTH);
        wrapper.add(fieldBox, BorderLayout.CENTER);

        return wrapper;
    }

    // ===== SAVE =====
    private void handleSave() {

        String nameVal = nameField.getText().trim();
        String emailVal = emailField.getText().trim();
        String phoneVal = phoneField.getText().trim();
        String passVal = new String(passwordField.getPassword());

        if (nameVal.isEmpty() || emailVal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name & Email required");
            return;
        }

        user.setName(nameVal);
        user.setEmail(emailVal);
        user.setPhone(phoneVal);

        UserController controller = new UserController();
        boolean success = controller.updateUser(user, passVal);

        JOptionPane.showMessageDialog(this,
                success ? "Profile Updated Successfully" : "Update Failed");
    }

    // ===== RIGHT SIDE =====
    private JPanel createRight() {
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(new Color(245,247,250));

        right.add(profileCard());
        right.add(Box.createVerticalStrut(20));
        right.add(preferences());

        return right;
    }

    // ===== PROFILE CARD =====
    private JPanel profileCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        avatarLabel = new JLabel();
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadImage();

        JLabel name = new JLabel(user.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel role = new JLabel("Admin Account");
        role.setForeground(Color.GRAY);
        role.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btn = new JButton("Change Photo");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> uploadImage());

        card.add(avatarLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(name);
        card.add(role);
        card.add(Box.createVerticalStrut(10));
        card.add(btn);

        return card;
    }

    private void loadImage() {
        try {
            if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                ImageIcon icon = new ImageIcon(user.getProfileImage());
                Image img = icon.getImage().getScaledInstance(60,60,Image.SCALE_SMOOTH);
                avatarLabel.setIcon(new ImageIcon(img));
            } else {
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icons/user.png"));
                Image img = icon.getImage().getScaledInstance(60,60,Image.SCALE_SMOOTH);
                avatarLabel.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
            System.out.println("Image load error");
        }
    }

    private void uploadImage() {
        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();

                String newPath = "uploads/" + System.currentTimeMillis() + "_" + file.getName();

                Files.copy(file.toPath(), new File(newPath).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                UserController controller = new UserController();
                boolean success = controller.updateProfileImage(user.getId(), newPath);

                if (success) {
                    user.setProfileImage(newPath);
                    loadImage();
                    JOptionPane.showMessageDialog(this, "Image Updated");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Upload Failed");
            }
        }
    }

    // ===== PREFERENCES =====
    private JPanel preferences() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        JLabel title = new JLabel("Preferences");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));

        card.add(title);
        card.add(Box.createVerticalStrut(10));

        card.add(new JCheckBox("Email Notifications", true));
        card.add(new JCheckBox("SMS Alerts"));
        card.add(new JCheckBox("System Alerts", true));
        card.add(new JCheckBox("Security Updates"));

        return card;
    }
}