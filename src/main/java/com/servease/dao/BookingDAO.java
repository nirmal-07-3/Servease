package com.servease.dao;

import com.servease.config.DBConnection;
import com.servease.model.Bookings;

import java.sql.Connection;
import java.sql.Date;
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

                String query = "SELECT b.id, s.name, b.booking_date, b.status " +
                        "FROM bookings b " +
                        "JOIN services s ON b.service_id = s.id " +
                        "WHERE b.user_id = ?";

                PreparedStatement ps = conn.prepareStatement(query);
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


    public List<Object[]> getBookingsByProvider(int providerId) {

        List<Object[]> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT b.id, u.name, s.name, b.booking_date, s.price, b.status " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "JOIN services s ON b.service_id = s.id " +
                    "WHERE s.provider_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, providerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Object[] row = new Object[6];

                row[0] = rs.getInt(1);      // booking id
                row[1] = rs.getString(2);   // customer name
                row[2] = rs.getString(3);   // service name
                row[3] = rs.getString(4);   // date
                row[4] = rs.getDouble(5);   // price
                row[5] = rs.getString(6);   // status

                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateBookingStatus(int bookingId,String status){

        try {
            Connection conn=DBConnection.getConnection();
            String query="UPDATE bookings SET status=? where id=?";
            PreparedStatement ps= conn.prepareStatement(query);
            ps.setString(1,status);
            ps.setInt(2,bookingId);

            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Bookings getBookingById(int id) {

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM bookings WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Bookings(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("service_id"),
                        rs.getDate("booking_date"),
                        rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }




    public List<Bookings> getBookingsByUser(int userId) {

        List<Bookings> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT b.id, b.booking_date, b.status, " +
                    "s.name AS service_name, u.name AS provider_name " +
                    "FROM bookings b " +
                    "JOIN services s ON b.service_id = s.id " +
                    "JOIN users u ON s.provider_id = u.id " +
                    "WHERE b.user_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Bookings b = new Bookings();

                b.setId(rs.getInt("id"));
                b.setBooking_date(rs.getDate("booking_date"));
                b.setStatus(rs.getString("status"));

                b.setServiceName(rs.getString("service_name"));
                b.setProviderName(rs.getString("provider_name"));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    }



