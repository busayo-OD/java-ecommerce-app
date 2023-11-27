package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.wishlist.MyWishlistDto;
import com.busayo.ecommercebackend.dto.wishlist.WishlistDto;
import com.busayo.ecommercebackend.dto.wishlist.WishlistInfoResponseDto;

import java.util.List;

public interface WishlistService {
    Boolean addProductToWishlist(WishlistDto wishlistDto, Long id);
    List<MyWishlistDto> getMyWishList(Long id);
    WishlistInfoResponseDto getAllWishlist(int pageNo, int pageSize, String sortBy, String sortDir);
}
