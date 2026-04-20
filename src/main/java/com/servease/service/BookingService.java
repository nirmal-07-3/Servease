package com.servease.service;

import com.servease.dao.BookingDAO;
import com.servease.model.Bookings;
import com.servease.controller.NotificationController;
import com.servease.controller.ServiceController;
import com.servease.model.Service;

import javax.swing.*;
import java.util.List;

public class BookingService {

    private BookingDAO bookingDAO = new BookingDAO();
    private NotificationController notificationController = new NotificationController();
    private ServiceController serviceController = new ServiceController();

    // ===== CREATE BOOKING =====
    public boolean createBooking(Bookings bookings) {

        if (bookings.getUser_id() <= 0 || bookings.getService_id() <= 0) {
            return false;
        }

        boolean success = bookingDAO.addBooking(bookings);

        if (success) {

            Service service = serviceController.getServiceById(bookings.getService_id());

            if (service != null) {

                int providerId = service.getProvider_id();

                // 🔔 PROVIDER NOTIFICATION
                notificationController.sendToProvider(
                        providerId,
                        "🆕 New booking for \"" + service.getName() + "\""
                );
            }
        }

        return success;
    }

    // ===== USER BOOKINGS =====
    public List<Object[]> getUserBookingsWithService(int userId) {
        return bookingDAO.getUserBookingsWithService(userId);
    }

    // ===== PROVIDER BOOKINGS =====
    public List<Object[]> getBookingsByProvider(int userId) {
        return bookingDAO.getBookingsByProvider(userId);
    }

    // ===== UPDATE STATUS =====
    public boolean updateBookingStatus(int bookingId, String status) {

        Bookings existing = bookingDAO.getBookingById(bookingId);

        if (existing.getStatus().equalsIgnoreCase("Completed")) {
            JOptionPane.showMessageDialog(null, "Cannot modify completed booking");
            return false;
        }

        boolean updated = bookingDAO.updateBookingStatus(bookingId, status);

        if (updated) {

            Service service = serviceController.getServiceById(existing.getService_id());

            if (service != null) {

                int providerId = service.getProvider_id();
                int userId = existing.getUser_id();

                // 🔔 PROVIDER NOTIFICATION
                notificationController.sendToProvider(
                        providerId,
                        "📌 Booking #" + bookingId + " marked as " + status
                );

                // 🔔 USER NOTIFICATION (🔥 NEW)
                notificationController.sendToUser(
                        userId,
                        "📢 Your booking for \"" + service.getName() + "\" is " + status
                );
            }
        }

        return updated;
    }
}