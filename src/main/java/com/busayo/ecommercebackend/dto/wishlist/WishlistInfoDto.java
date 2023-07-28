package com.busayo.ecommercebackend.dto.wishlist;

import com.busayo.ecommercebackend.model.Product;
import com.busayo.ecommercebackend.model.WishlistBasket;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class WishlistInfoDto {
    private Long id;

    private Product product;

    private String username;

    private String fullName;

    private WishlistBasket wishlistType;

    private int quantity;

    private Date createdDate;

    private  int daysInWishlist;
}
