package com.busayo.ecommercebackend.dto.product;

import com.busayo.ecommercebackend.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductResponse5Dto {
    private List<Product> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean last;
}
