package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.category.CategoryDto;
import com.busayo.ecommercebackend.exception.CategoryDuplicateException;
import com.busayo.ecommercebackend.exception.CategoryNotFoundException;
import com.busayo.ecommercebackend.model.Category;
import com.busayo.ecommercebackend.repository.CategoryRepository;
import com.busayo.ecommercebackend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category addCategory(CategoryDto categoryDto) {

        boolean existingName = categoryRepository.existsByName(categoryDto.getName().toUpperCase());

        if (existingName) {
            throw new CategoryDuplicateException();
        }
        Category category = new Category();

        category.setName(categoryDto.getName().toUpperCase());
        category.setStatus("Active");
        categoryRepository.save(category);
        return category;

    }

    @Override
    public Category getCategory(Long categoryId) {
        Category category;
        category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        return category;
    }

    private final List<Category> categories = new ArrayList<>();

//    @Override
//    public List<CategoryDto> getActiveCategories() {
//        categories.clear();
//        List<Category> allCategories = categoryRepository.findAll();
//
//        for (Category category : allCategories) {
//            if (category.getStatus().trim().equals("Active")) {
//                categories.add(category);
//            }}
//
//        return categories.stream().map((category1) -> modelMapper.map(category1, CategoryDto.class))
//                .collect(Collectors.toList());
//
//    }

    @Override
    public List<Category> getAllCategories(){
        categories.clear();
        try {
            categories.addAll(categoryRepository.findAll());
            return categories;
        }catch (NullPointerException e){
            return categories;
        }
    }

    @Override
    public Boolean updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        category.setId(categoryId);
        category.setName(categoryDto.getName().toUpperCase());
        categoryRepository.save(category);
        return true;
    }

    @Override
    public Boolean deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        category.setStatus("Deleted");
        categoryRepository.save(category);
        return true;
    }


}