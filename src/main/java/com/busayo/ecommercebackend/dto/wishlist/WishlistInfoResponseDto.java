package com.busayo.ecommercebackend.dto.wishlist;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WishlistInfoResponseDto {

    private List<WishlistInfoDto> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean last;
}
