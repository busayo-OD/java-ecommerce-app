package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.review.ReviewDto;
import com.busayo.ecommercebackend.model.Product;
import com.busayo.ecommercebackend.model.Review;
import com.busayo.ecommercebackend.model.User;
import com.busayo.ecommercebackend.repository.ProductRepository;
import com.busayo.ecommercebackend.repository.ReviewRepository;
import com.busayo.ecommercebackend.repository.UserRepository;
import com.busayo.ecommercebackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    public Review addReview(ReviewDto reviewDto, String username){
        User user = userRepository.findByUsername(username).get();
        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setFullName(user.getUsername());
        review.setStatus("Active");

        Product product = productRepository.findById(reviewDto.getProductId()).get();

        Review reviews1 = reviewRepository.save(review);
        product.getReviews().add(reviews1);
        productRepository.save(product);

        return reviews1;
    }
}
