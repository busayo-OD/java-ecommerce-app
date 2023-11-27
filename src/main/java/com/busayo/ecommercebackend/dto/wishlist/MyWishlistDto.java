package com.busayo.ecommercebackend.dto.wishlist;

import com.busayo.ecommercebackend.model.Product;
import com.busayo.ecommercebackend.model.WishlistBasket;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MyWishlistDto {
    private Long id;
    private Product product;
    private Date createdDate;
}
