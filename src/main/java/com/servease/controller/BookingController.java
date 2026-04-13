package com.servease.controller;

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
}
