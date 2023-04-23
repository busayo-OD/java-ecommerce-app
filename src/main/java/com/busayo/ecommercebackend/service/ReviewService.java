package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.review.ReviewDto;
import com.busayo.ecommercebackend.model.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(ReviewDto reviewDto, Long id);
    Boolean updateReview(ReviewDto reviewDto, Long id, Long reviewId);
    List<ReviewDto> getAllReviews();
    ReviewDto getReview (Long reviewId);
    Boolean deleteReview(Long reviewId, Long id);
}
