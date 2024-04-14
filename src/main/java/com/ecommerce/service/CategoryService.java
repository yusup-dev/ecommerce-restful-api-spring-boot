package com.ecommerce.service;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.entity.CategoryEntity;

public interface CategoryService {
//    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO createCategory(CategoryEntity categoryEntity);

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO updateCategory(CategoryEntity categoryEntity, Long id);

    String deleteCategory(Long id);
}
