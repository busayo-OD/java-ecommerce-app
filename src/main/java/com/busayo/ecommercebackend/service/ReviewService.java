package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.review.ReviewDto;
import com.busayo.ecommercebackend.model.Review;

public interface ReviewService {
    Review addReview(ReviewDto reviewDto, String username);
}
