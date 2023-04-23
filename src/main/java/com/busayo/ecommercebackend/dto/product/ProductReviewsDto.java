package com.busayo.ecommercebackend.dto.product;

import com.busayo.ecommercebackend.model.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductReviewsDto {

    List<Review> reviews;
}
