package com.servease.controller;

import com.servease.model.Bookings;
import com.servease.service.BookingService;

public class BookingController {

    private BookingService bookingService=new BookingService();

    public boolean bookService(Bookings bookings){
        return bookingService.createBooking(bookings);
    }
}
