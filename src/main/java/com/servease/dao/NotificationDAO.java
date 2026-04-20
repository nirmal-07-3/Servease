package com.servease.dao;

import com.servease.config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    // ================= ADD =================
    public void addNotification(Integer userId, Integer providerId, String message) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO notifications(user_id, provider_id, message, is_read, created_at) VALUES (?, ?, ?, 0, NOW())";

            PreparedStatement ps = con.prepareStatement(sql);

            if (userId == null)
                ps.setNull(1, Types.INTEGER);
            else
                ps.setInt(1, userId);

            if (providerId == null)
                ps.setNull(2, Types.INTEGER);
            else
                ps.setInt(2, providerId);

            ps.setString(3, message);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= FETCH (COMMON) =================
    public List<Object[]> getNotifications(int id) {

        List<Object[]> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = """
                SELECT id, message, is_read, created_at
                FROM notifications
                WHERE user_id = ? OR provider_id = ?
                ORDER BY created_at DESC
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("message"),
                        rs.getBoolean("is_read"),
                        rs.getTimestamp("created_at")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= MARK SINGLE =================
    public void markRead(int id) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE notifications SET is_read = 1 WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= MARK ALL =================
    public void markAllAsRead(int id) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = """
                UPDATE notifications
                SET is_read = 1
                WHERE user_id = ? OR provider_id = ?
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= UNREAD COUNT =================
    public int getUnreadCount(int id) {

        try (Connection con = DBConnection.getConnection()) {

            String sql = """
                SELECT COUNT(*)
                FROM notifications
                WHERE (user_id = ? OR provider_id = ?)
                AND is_read = 0
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}