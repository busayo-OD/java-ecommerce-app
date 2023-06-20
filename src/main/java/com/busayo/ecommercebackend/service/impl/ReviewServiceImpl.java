package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.review.ResponseDto;
import com.busayo.ecommercebackend.dto.review.ReviewDto;
import com.busayo.ecommercebackend.dto.review.ReviewInfoDto;
import com.busayo.ecommercebackend.dto.review.ReviewInfoResponseDto;
import com.busayo.ecommercebackend.exception.ProductNotFoundException;
import com.busayo.ecommercebackend.exception.ReviewNotFoundException;
import com.busayo.ecommercebackend.exception.ReviewResponseNotFoundException;
import com.busayo.ecommercebackend.exception.UserNotFoundException;
import com.busayo.ecommercebackend.model.*;
import com.busayo.ecommercebackend.repository.*;
import com.busayo.ecommercebackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ReviewResponseRepository reviewResponseRepository;

    @Override
    public Boolean addReview(ReviewDto reviewDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        Review review = new Review();

        review.setUser(user);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        Long productId = reviewDto.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        review.setProduct(product);
        review.setStatus("Active");

        reviewRepository.save(review);

        Notification notification = new Notification();

        notification.setImage(user.getAvatar());
        notification.setStatus("Active");
        notification.setTitle(user.getFirstName() + " " + user.getLastName());
        notification.setText(user.getUsername() + " submitted a review for " + product.getName());

        notificationRepository.save(notification);
        return true;

    }

    public Boolean updateReview(ReviewDto reviewDto, Long id){
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id));
            Long userId = user.getId();

            Long reviewId = reviewDto.getId();
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new ReviewNotFoundException(reviewId));
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
    public List<ReviewInfoDto> getAllReviews() {
        reviews.clear();
        List<Review> allReviews = reviewRepository.findAll();
        for (Review review : allReviews) {
            if ((allReviews != null) && (review.getStatus().trim().equals("Active"))) {
                reviews.add(review);
            }
        }
        return reviews.stream().map((review) -> mapToReviewInfoDto(review))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewInfoResponseDto getReviewsWithPaginationAndSorting(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Review> reviews = reviewRepository.findAll(pageable);

        List<Review> reviewList = reviews.getContent();

        List<ReviewInfoDto> content = reviewList.stream().map(review -> mapToReviewInfoDto(review)).collect(Collectors.toList());

        ReviewInfoResponseDto reviewInfoResponseDto = new ReviewInfoResponseDto();
        reviewInfoResponseDto.setContent(content);
        reviewInfoResponseDto.setPageNo(reviews.getNumber());
        reviewInfoResponseDto.setPageSize(reviews.getSize());
        reviewInfoResponseDto.setTotalElements(reviews.getTotalElements());
        reviewInfoResponseDto.setTotalPages(reviews.getTotalPages());
        reviewInfoResponseDto.setLast(reviews.isLast());

        return reviewInfoResponseDto;
    }

    @Override
    public  ReviewInfoDto getReview (Long reviewId) {
        Review review = reviewRepository.findById(reviewId)

                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        return mapToReviewInfoDto(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long id) {
        User userId = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        Long reviewUserId = review.getUser().getId();
        if (!userId.equals(reviewUserId)) {

        } else {
            reviewRepository.delete(review);
        }
    }

    @Override
    public List<ReviewInfoDto> getProductReviews(Long productId) {
        List<Review> productReviews = reviewRepository.findByProductId(productId);
        return productReviews.stream().map((review) -> mapToReviewInfoDto(review))
                .collect(Collectors.toList());
    }

    @Override
    public void addResponse(ResponseDto responseDto, Long reviewId) {
        ReviewResponse response = new ReviewResponse();
        response.setId(responseDto.getId());
        response.setResponse(responseDto.getResponse());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        review.setReviewResponse(response);
        reviewResponseRepository.save(response);
    }

    @Override
    public void deleteResponse(Long responseId) {
        ReviewResponse response = reviewResponseRepository.findById(responseId)
                .orElseThrow(() -> new ReviewResponseNotFoundException(responseId));
        reviewResponseRepository.delete(response);
    }


    private ReviewInfoDto mapToReviewInfoDto (Review review) {
        ReviewInfoDto reviewInfoDto = new ReviewInfoDto();
        reviewInfoDto.setId(review.getId());
        reviewInfoDto.setUsername(review.getUser().getUsername());
        reviewInfoDto.setFullName(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        reviewInfoDto.setProduct(review.getProduct());
        reviewInfoDto.setComment(review.getComment());
        reviewInfoDto.setRating(review.getRating());
        reviewInfoDto.setResponse(review.getReviewResponse());
        reviewInfoDto.setCreatedOn(review.getCreatedOn());
        reviewInfoDto.setUpdatedOn(review.getUpdatedOn());

        return reviewInfoDto;
    }

    private ResponseDto mapToReviewResponseDto(ReviewResponse reviewResponse) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setId(reviewResponse.getId());
        responseDto.setResponse(reviewResponse.getResponse());
        return responseDto;
    }

}

