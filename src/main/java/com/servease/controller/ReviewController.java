package com.servease.controller;

import com.servease.dao.ReviewDAO;

import java.util.List;

public class ReviewController {

    private ReviewDAO dao = new ReviewDAO();

    public boolean addReview(int bookingId,int serviceId,int userId,
                             int providerId,double rating,String comment){
        return dao.addReview(bookingId,serviceId,userId,providerId,rating,comment);
    }

    public List<Object[]> getReviews(int providerId){
        return dao.getReviewsByProvider(providerId);
    }

    public double getAvgRating(int providerId){
        return dao.getAverageRating(providerId);
    }

    public int getTotalReviews(int providerId){
        return dao.getTotalReviews(providerId);
    }

    public int getFiveStar(int providerId){
        return dao.getFiveStarCount(providerId);
    }

    public Object[] getReviewsStats(int providerId){
        return dao.getReviewStats(providerId);
    }
}