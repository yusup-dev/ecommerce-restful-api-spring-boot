package com.ecommerce.service.impl;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.entity.CategoryEntity;
import com.ecommerce.exception.APIException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;


    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryEntity categoryEntity) {
        CategoryEntity savedCategoryEntity = categoryRepository.findByName(categoryEntity.getName());

        if(savedCategoryEntity != null){
            throw new APIException("Category with the name '" + categoryEntity.getName() + "' already exists !!!");
        }

        savedCategoryEntity = categoryRepository.save(categoryEntity);
        return modelMapper.map(savedCategoryEntity, CategoryDTO.class);
    }


//    @Override
//    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
//
//        CategoryEntity categoryEntity = mapToEntity(categoryDTO);
//        CategoryEntity newCategoryEntity = categoryRepository.save(categoryEntity);
//
//        CategoryDTO categoryResponse = mapToDTO(newCategoryEntity);
//        return categoryResponse;
//    }
//
//    // convert Entity into DTO
//    private CategoryDTO mapToDTO(CategoryEntity categoryEntity){
//        CategoryDTO categoryDTO = new CategoryDTO();
//        categoryDTO.setId(categoryEntity.getId());
//        categoryDTO.setName(categoryEntity.getName());
//        return categoryDTO;
//    }
//
//    // convert DTO to entity
//    private CategoryEntity mapToEntity(CategoryDTO categoryDTO){
//        CategoryEntity categoryEntity = new CategoryEntity();
//        categoryEntity.setName(categoryDTO.getName());
//        return categoryEntity;
//    }
}
