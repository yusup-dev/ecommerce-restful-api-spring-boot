package com.ecommerce.service.impl;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.entity.CategoryEntity;
import com.ecommerce.exception.APIException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;


    private ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO createCategory(CategoryEntity categoryEntity) {
        CategoryEntity savedCategoryEntity = categoryRepository.findByName(categoryEntity.getName());

        if(savedCategoryEntity != null){
            throw new APIException("Category with the name '" + categoryEntity.getName() + "' already exists !!!");
        }

        savedCategoryEntity = categoryRepository.save(categoryEntity);
        return modelMapper.map(savedCategoryEntity, CategoryDTO.class);
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {


        Sort sortByAndOrder = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<CategoryEntity> pageCategoryEntities = categoryRepository.findAll(pageDetails);

        List<CategoryEntity> categoryEntities = pageCategoryEntities.getContent();

        if(categoryEntities.isEmpty()){
            throw new APIException("No category is created till now");
        }

        List<CategoryDTO> categoryDTOs = categoryEntities.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class)).collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setContent(categoryDTOs);
        categoryResponse.setPageNumber(pageCategoryEntities.getNumber());
        categoryResponse.setPageSize(pageCategoryEntities.getSize());
        categoryResponse.setTotalElements(pageCategoryEntities.getTotalElements());
        categoryResponse.setTotalPages(pageCategoryEntities.getTotalPages());
        categoryResponse.setLastPage(pageCategoryEntities.isLast());

        return categoryResponse;

    }

    @Override
    public CategoryDTO updateCategory(CategoryEntity categoryEntity, Long id) {
        CategoryEntity savedCategoryEntity = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("CategoryEntity", "id", id));
        categoryEntity.setId(id);

        savedCategoryEntity = categoryRepository.save(categoryEntity);

        return modelMapper.map(savedCategoryEntity, CategoryDTO.class);
    }

    @Override
    public String deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("CategoryEntity", "id", id));

        categoryRepository.delete(categoryEntity);

        return "Category with categoryId: " + id + " deleted successfully !!!";
    }
}
