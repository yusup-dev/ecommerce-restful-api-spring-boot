package com.ecommerce.service;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.entity.CategoryEntity;

public interface CategoryService {
//    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO createCategory(CategoryEntity categoryEntity);
}
