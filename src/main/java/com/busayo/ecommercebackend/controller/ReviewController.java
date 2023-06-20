package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.review.ResponseDto;
import com.busayo.ecommercebackend.dto.review.ReviewDto;
import com.busayo.ecommercebackend.dto.review.ReviewInfoDto;
import com.busayo.ecommercebackend.dto.review.ReviewInfoResponseDto;
import com.busayo.ecommercebackend.service.ReviewService;
import com.busayo.ecommercebackend.utils.AppConstants;
import com.busayo.ecommercebackend.utils.CurrentUserUtil;
import com.busayo.ecommercebackend.utils.ReviewConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-response/{id}")
    public void addReviewResponse(@RequestBody ResponseDto responseDto,
                                  @PathVariable("id") Long reviewId){
        reviewService.addResponse(responseDto, reviewId);
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

    @GetMapping("/pagination")
    public ReviewInfoResponseDto getReviewsWithPaginationAndSorting(
            @RequestParam(value = "pageNo", defaultValue = ReviewConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = ReviewConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ReviewConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = ReviewConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return reviewService.getReviewsWithPaginationAndSorting(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReviewInfoDto> getReview(@PathVariable("id") Long reviewId){
        ReviewInfoDto review = reviewService.getReview(reviewId);
        return  ResponseEntity.ok(review);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ReviewInfoDto>> getProductReviews(
            @PathVariable("id") Long productId,
            @PathVariable String status){
        return ResponseEntity.ok(reviewService.getProductReviews(productId));

    }

    @DeleteMapping("/delete/{id}")
    public void deleteReview(@PathVariable Long id) {
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        reviewService.deleteReview(id, userId);
    }
}
