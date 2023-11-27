package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.wishlist.MyWishlistDto;
import com.busayo.ecommercebackend.dto.wishlist.WishlistDto;
import com.busayo.ecommercebackend.dto.wishlist.WishlistInfoResponseDto;
import com.busayo.ecommercebackend.service.WishlistService;
import com.busayo.ecommercebackend.utils.CurrentUserUtil;
import com.busayo.ecommercebackend.utils.WishlistConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/wish-lists")
public class WishlistController {

    private  final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add")
    public Boolean addProductToWishlist(@RequestBody WishlistDto wishlistDto){
        Long userId = Objects.requireNonNull(CurrentUserUtil.getCurrentUser()).getId();

        return wishlistService.addProductToWishlist(wishlistDto, userId);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-wishlist")
    public ResponseEntity<List<MyWishlistDto>> getMyWishlist(){
        Long userId = Objects.requireNonNull(CurrentUserUtil.getCurrentUser()).getId();
        return ResponseEntity.ok(wishlistService.getMyWishList(userId));
    }

    @GetMapping("/pagination")
    public WishlistInfoResponseDto getAllWishListWithPaginationAndSorting(
            @RequestParam(value = "pageNo", defaultValue = WishlistConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = WishlistConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = WishlistConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = WishlistConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return wishlistService.getAllWishlist(pageNo, pageSize, sortBy, sortDir);
    }

}

