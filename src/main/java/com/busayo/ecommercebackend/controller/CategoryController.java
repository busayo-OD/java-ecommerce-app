package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.category.CategoryDto;
import com.busayo.ecommercebackend.model.Category;
import com.busayo.ecommercebackend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody CategoryDto categoryDto){
        Category savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") Long categoryId){
        Category category = categoryService.getCategory(categoryId);
        return  ResponseEntity.ok(category);
    }
//
//    @GetMapping("/active")
//    public ResponseEntity<List<CategoryDto>> getActiveCategories(){
//        return ResponseEntity.ok(categoryService.getActiveCategories());
//    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Boolean> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable("id") Long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

}
