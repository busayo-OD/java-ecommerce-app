package com.busayo.ecommercebackend.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private Long id;

    private String fullName;

    private Long productId;

    private int rating;

    private String comment;

    private String status;
}