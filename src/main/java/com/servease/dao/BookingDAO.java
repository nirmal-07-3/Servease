package com.servease.dao;

import com.servease.config.DBConnection;
import com.servease.model.Bookings;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookingDAO {



    public boolean addBooking(Bookings bookings){


        try{
            Connection conn= DBConnection.getConnection();


            String query="INSERT INTO bookings(user_id,service_id,booking_date,status)VALUES (?,?,?,?)";

            PreparedStatement ps= conn.prepareStatement(query);

            ps.setInt(1,bookings.getUser_id());
            ps.setInt(2,bookings.getService_id());
            ps.setDate(3,bookings.getBooking_date());
            ps.setString(4,bookings.getStatus());

            return ps.executeUpdate()>0;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
