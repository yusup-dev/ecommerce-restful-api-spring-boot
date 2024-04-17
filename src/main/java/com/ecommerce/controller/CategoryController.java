package com.ecommerce.controller;

import com.ecommerce.constants.AppConstants;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.entity.CategoryEntity;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryEntity categoryEntity){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryEntity);

        return new ResponseEntity<CategoryDTO>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ){
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryEntity categoryEntity, @PathVariable Long id){
        CategoryDTO categoryDTO = categoryService.updateCategory(categoryEntity, id);
        return new  ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        String status = categoryService.deleteCategory(id);
        return new ResponseEntity<String>(status, HttpStatus.OK);
    }

}