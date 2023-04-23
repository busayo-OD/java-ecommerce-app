package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.review.ReviewDto;
import com.busayo.ecommercebackend.model.Review;
import com.busayo.ecommercebackend.service.ReviewService;
import com.busayo.ecommercebackend.utils.CurrentUserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody ReviewDto reviewDto){
        Long userId = CurrentUserUtil.getCurrentUser().getId();

        Review savedReview = reviewService.addReview(reviewDto, userId);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Boolean> updateReview(@RequestBody ReviewDto reviewDto,
                                                @PathVariable("id") Long reviewId){
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        return ResponseEntity.ok(reviewService.updateReview(reviewDto, reviewId, userId));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable("id") Long reviewId){
        ReviewDto review = reviewService.getReview(reviewId);
        return  ResponseEntity.ok(review);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable Long id) {
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        return ResponseEntity.ok(reviewService.deleteReview(id, userId));
    }
}
