package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.review.ReviewDto;
import com.busayo.ecommercebackend.dto.review.ReviewInfoDto;
import com.busayo.ecommercebackend.dto.review.ReviewInfoResponseDto;
import com.busayo.ecommercebackend.service.ReviewService;
import com.busayo.ecommercebackend.utils.AppConstants;
import com.busayo.ecommercebackend.utils.CurrentUserUtil;
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

    @PostMapping("/add")
    public Boolean addReview(@RequestBody ReviewDto reviewDto){
        Long userId = CurrentUserUtil.getCurrentUser().getId();

        return reviewService.addReview(reviewDto, userId);
    }

    @PutMapping("/edit")
    public ResponseEntity<Boolean> updateReview(@RequestBody ReviewDto reviewDto){
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        return ResponseEntity.ok(reviewService.updateReview(reviewDto, userId));
    }

    @GetMapping
    public ResponseEntity<List<ReviewInfoDto>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/pagination/{status}")
    public ReviewInfoResponseDto getReviewsWithPaginationAndSorting(
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return reviewService.getReviewsWithPaginationAndSorting(status, pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReviewInfoDto> getReview(@PathVariable("id") Long reviewId){
        ReviewInfoDto review = reviewService.getReview(reviewId);
        return  ResponseEntity.ok(review);
    }

    @GetMapping("/product/{id}/{status}")
    public ResponseEntity<List<ReviewInfoDto>> getProductReviews(
            @PathVariable("id") Long productId,
            @PathVariable String status){
        return ResponseEntity.ok(reviewService.getProductReviews(productId, status));

    }

    @DeleteMapping("/delete/{id}")
    public void deleteReview(@PathVariable Long id) {
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        reviewService.deleteReview(id, userId);
    }
}
