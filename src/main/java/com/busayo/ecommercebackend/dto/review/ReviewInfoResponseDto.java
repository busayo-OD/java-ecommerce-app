package com.busayo.ecommercebackend.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ReviewInfoResponseDto {

    private List<ReviewInfoDto> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean last;
}
