package com.servease.dao;

import com.servease.config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminProviderDAO {

    public List<Object[]> getProviders() {

        List<Object[]> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE role = 'PROVIDER'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("status"),
                        "Action"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateStatus(int id, String status) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE users SET status=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}