package com.servease.ui.dashboard;

import com.servease.controller.NotificationController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NotificationPanel extends JPanel {

    private int userId;
    private NotificationController controller;

    private JPanel listPanel;
    private JLabel unreadLabel;

    public NotificationPanel(int userId) {
        this.userId = userId;
        this.controller = new NotificationController();

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        add(createHeader(), BorderLayout.NORTH);
        add(createList(), BorderLayout.CENTER);

        loadNotifications();
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(20,25,20,25));

        // LEFT TEXT
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(Color.WHITE);

        JLabel title = new JLabel("Notifications");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        unreadLabel = new JLabel("Loading...");
        unreadLabel.setForeground(Color.GRAY);

        left.add(title);
        left.add(unreadLabel);

        // RIGHT BUTTON
        JButton markAll = new JButton("Mark all as read");
        markAll.setFocusPainted(false);
        markAll.setBackground(new Color(240,240,240));

        markAll.addActionListener(e -> {
            controller.markAllAsRead(userId);
            loadNotifications();
        });

        header.add(left, BorderLayout.WEST);
        header.add(markAll, BorderLayout.EAST);

        return header;
    }

    // ================= LIST =================
    private JScrollPane createList() {
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(245,247,250));

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        return scroll;
    }

    // ================= LOAD =================
    private void loadNotifications() {
        listPanel.removeAll();

        List<Object[]> list = controller.getNotifications(userId);

        int unreadCount = 0;

        for (Object[] n : list) {
            if (!(boolean) n[2]) unreadCount++;
        }

        unreadLabel.setText(unreadCount + " unread notifications");

        for (Object[] n : list) {
            listPanel.add(createItem(n));
            listPanel.add(Box.createVerticalStrut(10));
        }

        revalidate();
        repaint();
    }

    // ================= ITEM =================
    private JPanel createItem(Object[] n) {

        int id = (int) n[0];
        String msg = n[1].toString();
        boolean read = (boolean) n[2];
        String time = n[3].toString();

        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        // ===== LEFT ICON =====
        JLabel icon = new JLabel("🔔");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));

        JPanel iconWrap = new JPanel();
        iconWrap.setBackground(new Color(235,240,255));
        iconWrap.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        iconWrap.add(icon);

        // ===== TEXT =====
        JLabel title = new JLabel("<html><b>" + getTitle(msg) + "</b></html>");
        JLabel desc = new JLabel("<html><font color='gray'>" + msg + "</font></html>");

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        textPanel.add(title);
        textPanel.add(desc);

        // ===== RIGHT SIDE =====
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(Color.WHITE);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setForeground(Color.GRAY);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        topRight.setBackground(Color.WHITE);

        // 🔵 UNREAD DOT
        if (!read) {
            JLabel dot = new JLabel("●");
            dot.setForeground(new Color(33,150,243));
            topRight.add(dot);
        }

        // BUTTON / STATUS
        JButton actionBtn = new JButton();
        actionBtn.setFocusPainted(false);

        if (!read) {
            actionBtn.setText("Mark as read");
            actionBtn.setBackground(new Color(33,150,243));
            actionBtn.setForeground(Color.WHITE);

            actionBtn.addActionListener(e -> {
                controller.markRead(id);
                loadNotifications();
            });
        } else {
            actionBtn.setText("✓ Read");
            actionBtn.setEnabled(false);
            actionBtn.setBackground(new Color(230,230,230));
        }

        right.add(timeLabel);
        right.add(Box.createVerticalStrut(8));
        right.add(actionBtn);

        // ===== LAYOUT =====
        item.add(iconWrap, BorderLayout.WEST);
        item.add(textPanel, BorderLayout.CENTER);
        item.add(right, BorderLayout.EAST);

        return item;
    }

    // ================= TITLE GENERATOR =================
    private String getTitle(String msg) {
        if (msg.toLowerCase().contains("booking"))
            return "Booking Update";
        if (msg.toLowerCase().contains("review"))
            return "New Review";
        if (msg.toLowerCase().contains("payout"))
            return "Payment Update";
        return "Notification";
    }
}