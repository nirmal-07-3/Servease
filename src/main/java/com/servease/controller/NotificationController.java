package com.servease.controller;

import com.servease.dao.NotificationDAO;

import java.util.List;

public class NotificationController {

    private NotificationDAO dao = new NotificationDAO();

    // ================= SEND =================

    // send notification to provider
    public void sendToProvider(int providerId, String msg) {
        dao.addNotification(null, providerId, msg);
    }

    // send notification to user
    public void sendToUser(int userId, String msg) {
        dao.addNotification(userId, null, msg);
    }

    // ================= FETCH =================

    // works for BOTH user & provider
    public List<Object[]> getNotifications(int userId) {
        return dao.getNotifications(userId);
    }

    // ================= READ =================

    public void markRead(int id) {
        dao.markRead(id);
    }

    public void markAllAsRead(int userId) {
        dao.markAllAsRead(userId);
    }

    // ================= COUNT =================

    public int getUnreadCount(int userId) {
        return dao.getUnreadCount(userId);
    }
}