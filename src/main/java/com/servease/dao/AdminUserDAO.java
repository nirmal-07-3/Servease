package com.servease.dao;

import com.servease.config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminUserDAO {

    public List<Object[]> getAllUsers() {
        List<Object[]> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();


            String sql = "SELECT * FROM users WHERE role = 'USER'";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println("USER FOUND: " + rs.getString("name")); // debug

                list.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("status"),

                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateStatus(int userId, String status) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE users SET status=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}