package com.busayo.ecommercebackend.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewInfoDto {

    private Long id;

    private String username;

    private String product;

    private int rating;

    private String comment;

}
