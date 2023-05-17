package com.busayo.ecommercebackend.dto.wishlist;

import com.busayo.ecommercebackend.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MyWishlistDto {
    private Long id;

    private Product product;

    private String wishlistType;

    private Date createdDate;
}
