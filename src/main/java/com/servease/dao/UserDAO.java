package com.servease.dao;

import com.servease.config.DBConnection;
import com.servease.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean registerUser(User user) {
        boolean isRegistered = false;

        try {
            Connection conn = DBConnection.getConnection();

            String query = "INSERT INTO users (name, email, password,phone, role) VALUES (?, ?, ?, ?,?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                isRegistered = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRegistered;
    }

    public User loginUser(String email, String password) {
        User user =null;

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT *FROM users WHERE email=? AND password=?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user=new User(
                 rs.getInt("id")  ,
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("phone"),
                rs.getString("role")
                );
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;

    }
}


