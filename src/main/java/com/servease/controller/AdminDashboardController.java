package com.servease.controller;

import com.servease.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDashboardController {

    public int getTotalUsers() {
        return getCount("SELECT COUNT(*) FROM users WHERE role='USER'");
    }

    public int getTotalProviders() {
        return getCount("SELECT COUNT(*) FROM users WHERE role='PROVIDER'");
    }

    public int getTotalServices() {
        return getCount("SELECT COUNT(*) FROM services");
    }

    public int getTotalBookings() {
        return getCount("SELECT COUNT(*) FROM bookings");
    }

    public double getRevenue() {
        double total = 0;
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT SUM(s.price) " +
                    "FROM bookings b " +
                    "JOIN services s ON b.service_id = s.id " +
                    "WHERE b.status='Completed'";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    private int getCount(String query) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}