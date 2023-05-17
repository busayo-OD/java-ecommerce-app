package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.category.CategoryDto;
import com.busayo.ecommercebackend.model.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(CategoryDto categoryDto);
    Category getCategory(Long categoryId);
    //    List<CategoryDto> getActiveCategories();
    List<Category> getAllCategories();
    Boolean updateCategory(CategoryDto categoryDto, Long categoryId);
    Boolean deleteCategory(Long id);
}
