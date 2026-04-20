package com.servease.dao;

import com.servease.config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {


    public boolean addReview(int bookingId, int serviceId, int userId,
                             int providerId, double rating, String comment) {

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO reviews (booking_id,service_id,user_id,provider_id,rating,comment,created_at) VALUES (?,?,?,?,?,?,NOW())";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, bookingId);
            ps.setInt(2, serviceId);
            ps.setInt(3, userId);
            ps.setInt(4, providerId);
            ps.setDouble(5, rating);
            ps.setString(6, comment);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // GET REVIEWS FOR PROVIDER
    public List<Object[]> getReviewsByProvider(int providerId) {

        List<Object[]> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT u.name, s.name, r.rating, r.comment, r.created_at " +
                    "FROM reviews r " +
                    "JOIN users u ON r.user_id = u.id " +
                    "JOIN services s ON r.service_id = s.id " +
                    "WHERE r.provider_id = ? " +
                    "ORDER BY r.created_at DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, providerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Object[]{
                        rs.getString(1), // user
                        rs.getString(2), // service
                        rs.getDouble(3), // rating
                        rs.getString(4), // comment
                        rs.getTimestamp(5)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // AVG RATING
    public double getAverageRating(int providerId) {

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT AVG(rating) FROM reviews WHERE provider_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, providerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getDouble(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // TOTAL REVIEWS
    public int getTotalReviews(int providerId) {

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT COUNT(*) FROM reviews WHERE provider_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, providerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // 5 STAR COUNT
    public int getFiveStarCount(int providerId) {

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT COUNT(*) FROM reviews WHERE provider_id=? AND rating=5";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, providerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Object[] getReviewStats(int providerId) {

        try (Connection con = DBConnection.getConnection()) {

            String sql = """
            SELECT 
                COUNT(*) as total,
                IFNULL(AVG(rating),0) as avg_rating,
                SUM(CASE WHEN rating=5 THEN 1 ELSE 0 END) as five_star
            FROM reviews
            WHERE provider_id = ?
        """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, providerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Object[]{
                        rs.getInt("total"),
                        rs.getDouble("avg_rating"),
                        rs.getInt("five_star")
                };
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Object[]{0,0.0,0};
    }
}