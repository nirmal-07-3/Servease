package com.servease.controller;

import com.servease.dao.BookingDAO;
import com.servease.model.Bookings;
import com.servease.service.BookingService;

import java.util.List;

public class BookingController {

    private BookingService bookingService=new BookingService();

    public boolean bookService(Bookings bookings){
        return bookingService.createBooking(bookings);
    }

    public List<Object[]> getUserBookingsWithService(int userId) {
        return bookingService.getUserBookingsWithService(userId);
    }

    public List<Object[]> getBookingsByProvider(int userId) {
        return bookingService.getBookingsByProvider(userId);
    }

    public boolean updateBookingStatus(int bookingId,String status){
        return bookingService.updateBookingStatus(bookingId,status);
    }




        private BookingDAO dao = new BookingDAO();

        public List<Bookings> getBookingsByUser(int userId){
            return dao.getBookingsByUser(userId);
        }
    }

