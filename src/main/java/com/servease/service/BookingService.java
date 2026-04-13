package com.servease.service;

import com.servease.dao.BookingDAO;
import com.servease.model.Bookings;

import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO=new BookingDAO();

    public boolean createBooking(Bookings bookings){
        if(bookings.getUser_id()<=0||bookings.getService_id()<=0){
            return false;
        }
         return bookingDAO.addBooking(bookings);
    }

    public List<Object[]> getUserBookingsWithService(int userId) {
        return bookingDAO.getUserBookingsWithService(userId);
    }
}



