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

    public boolean updateUserWithoutPassword(User user) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE users SET name=?, email=?, phone=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserWithPassword(User user, String password) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE users SET name=?, email=?, phone=?, password=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, password);
            ps.setInt(5, user.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProfileImage(int userId, String imagePath) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE users SET profile_image=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, imagePath);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


