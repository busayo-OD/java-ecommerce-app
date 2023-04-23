package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.review.ReviewDto;
import com.busayo.ecommercebackend.exception.ReviewNotFoundException;
import com.busayo.ecommercebackend.exception.UserNotFoundException;
import com.busayo.ecommercebackend.model.Product;
import com.busayo.ecommercebackend.model.Review;
import com.busayo.ecommercebackend.model.User;
import com.busayo.ecommercebackend.repository.ProductRepository;
import com.busayo.ecommercebackend.repository.ReviewRepository;
import com.busayo.ecommercebackend.repository.UserRepository;
import com.busayo.ecommercebackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Review addReview(ReviewDto reviewDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        Review review = new Review();

        review.setUser(user);
        review.setUsername(user.getUsername());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setStatus("Active");

        Product product = productRepository.findById(reviewDto.getProductId()).get();

        Review reviews1 = reviewRepository.save(review);
        product.getReviews().add(reviews1);
        productRepository.save(product);

        return reviews1;
    }

    public Boolean updateReview(ReviewDto reviewDto, Long id, Long reviewId){
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id));
            Long userId = user.getId();

            Review review = reviewRepository.findById(reviewId).get();
            Long reviewUserId = review.getUser().getId();
            if (userId.equals(reviewUserId)) {
                review.setComment(reviewDto.getComment());
                review.setRating(reviewDto.getRating());
                reviewRepository.save(review);
                return true;
            }
            else {
                return false;
            }
        }catch (NullPointerException e){
            return false;
        }
    }

    private List<Review> reviews = new ArrayList<>();

    @Override
    public List<ReviewDto> getAllReviews() {
        reviews.clear();
        List<Review> allReviews = reviewRepository.findAll();
        for (Review review : allReviews) {
            if ((allReviews != null) && (review.getStatus().trim().equals("Active"))) {
                reviews.add(review);
            }
        }
        return reviews.stream().map((review) -> mapToReviewDto(review))
                .collect(Collectors.toList());
    }

    @Override
    public  ReviewDto getReview (Long reviewId) {
        Review review = reviewRepository.findById(reviewId)

                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        return mapToReviewDto(review);
    }

    @Override
    public Boolean deleteReview(Long reviewId, Long id) {
        try{
            User userId = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id));

            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new ReviewNotFoundException(reviewId));
            Long reviewUserId = review.getUser().getId();
            if (userId.equals(reviewUserId)) {
                review.setStatus("Deleted");
                reviewRepository.save(review);
                return true;
            }
            else {
                return false;
            }

        }catch (NullPointerException e){
            return false;
        }
    }

    private ReviewDto mapToReviewDto (Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setUsername(review.getUsername());
        reviewDto.setComment(review.getComment());
        reviewDto.setRating(review.getRating());

        return reviewDto;
    }
}
