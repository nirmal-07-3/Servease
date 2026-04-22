package com.servease.controller;

import com.servease.dao.BookingDAO;
import java.util.List;

public class AdminBookingController {

    private BookingDAO dao = new BookingDAO();

    public List<Object[]> getAllBookings() {
        return dao.getAllBookingsAdmin();
    }

    public void updateStatus(int id, String status) {
        dao.updateBookingStatus(id, status);
    }
}