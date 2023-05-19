package com.busayo.ecommercebackend.dto.review;

import com.busayo.ecommercebackend.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReviewInfoDto {

    private Long id;

    private String username;

    private String fullName;

    private Product product;

    private int rating;

    private String comment;

    private Date createdOn;

    private Date updatedOn;

}
