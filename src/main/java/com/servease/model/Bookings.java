package com.servease.model;

import java.sql.Date;

public class Bookings {
    private int id;
    private int user_id;
    private int service_id;
    private Date booking_date;
    private String status;
    private double price;


    private String serviceName;
    private  String providerName;

    public Bookings(int id, int user_id, int service_id, Date booking_date, String status) {
        this.id = id;
        this.user_id = user_id;
        this.service_id = service_id;
        this.booking_date = booking_date;
        this.status = status;
    }

    public Bookings(int user_id, int service_id, Date booking_date, String status) {
        this.user_id = user_id;
        this.service_id = service_id;
        this.booking_date = booking_date;
        this.status = status;
    }

    public Bookings() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}
