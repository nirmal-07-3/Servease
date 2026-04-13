package com.servease.dao;

import com.servease.config.DBConnection;
import com.servease.model.Bookings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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




        public List<Object[]> getUserBookingsWithService(int userId) {

            List<Object[]> list = new ArrayList<>();

            try {
                Connection conn = DBConnection.getConnection();

                String sql = "SELECT b.id, s.name, b.booking_date, b.status " +
                        "FROM bookings b " +
                        "JOIN services s ON b.service_id = s.id " +
                        "WHERE b.user_id = ?";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, userId);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    list.add(new Object[]{
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDate("booking_date"),
                            rs.getString("status")
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }
    }


