package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.review.ReviewDto;
import com.busayo.ecommercebackend.dto.review.ReviewInfoDto;
import com.busayo.ecommercebackend.dto.review.ReviewInfoResponseDto;
import com.busayo.ecommercebackend.model.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(ReviewDto reviewDto, Long id);
    Boolean updateReview(ReviewDto reviewDto, Long userId);
    List<ReviewInfoDto> getAllReviews();
    ReviewInfoResponseDto getReviewsWithPaginationAndSorting(String status, int pageNo, int pageSize, String sortBy, String sortDir);
    ReviewInfoDto getReview (Long reviewId);
    Boolean deleteReview(Long reviewId, Long id);
}
