package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.review.ReviewDto;
import com.busayo.ecommercebackend.model.Review;
import com.busayo.ecommercebackend.service.ReviewService;
import com.busayo.ecommercebackend.utils.CurrentUserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody ReviewDto reviewDto){
        String logname = CurrentUserUtil.getCurrentUser().getUsername();

        Review savedReview = reviewService.addReview(reviewDto, logname);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }
}
