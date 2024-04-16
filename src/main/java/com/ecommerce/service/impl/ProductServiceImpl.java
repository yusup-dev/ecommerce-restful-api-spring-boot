package com.ecommerce.service.impl;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.CategoryEntity;
import com.ecommerce.entity.ProductEntity;
import com.ecommerce.exception.APIException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private CategoryRepository categoryRepository;

    private ProductRepository productRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO createProduct(Long categoryId, ProductEntity productEntity) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new  ResourceNotFoundException("CategoryEntity", "id", categoryId));

        boolean isProductNotPresent = true;

        List<ProductEntity> productEntities = categoryEntity.getProductEntities();

        for (int i = 0; i < productEntities.size(); i++){
            if(productEntities.get(i).getName().equals(productEntity.getName()) && productEntities.get(i).getName().equals(productEntity.getName()) && productEntities.get(i).getDescription().equals(productEntity.getDescription())){
                isProductNotPresent = false;
                break;
            }
        }

        if(isProductNotPresent){
            productEntity.setCategoryEntity(categoryEntity);

            double specialPrice = productEntity.getPrice() - ((productEntity.getDiscount() * 0.01) * productEntity.getPrice());

            productEntity.setSpecialPrice(specialPrice);

            ProductEntity savedProduct = productRepository.save(productEntity);
            return  modelMapper.map(savedProduct, ProductDTO.class);
        }else {
            throw new APIException("Product already exists !!!");
        }
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<ProductEntity> pageProducts = productRepository.findAll(pageDetails);

        List<ProductEntity> productEntities = pageProducts.getContent();

        List<ProductDTO> productDTOs = productEntities.stream().map(productEntity -> modelMapper.map(productEntity, ProductDTO.class)).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productDTOs);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber,Integer pageSize, String sortBy, String sortOrder) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("CategoryEntity", "id", categoryId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<ProductEntity> pageProductEntities = productRepository.findByCategoryEntity(categoryEntity, pageDetails);

        List<ProductEntity> productEntities = pageProductEntities.getContent();

        if(productEntities.isEmpty()){
            throw new APIException(categoryEntity.getName() + "Category doesn't contain any product !!!");
        }

        List<ProductDTO> productDTOs = productEntities.stream().map(productEntity -> modelMapper.map(productEntity, ProductDTO.class)).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productDTOs);
        productResponse.setPageNumber(pageProductEntities.getNumber());
        productResponse.setPageSize(pageProductEntities.getSize());
        productResponse.setTotalElements(pageProductEntities.getTotalElements());
        productResponse.setTotalPages(pageProductEntities.getTotalPages());
        productResponse.setLastPage(pageProductEntities.isLast());

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductEntity productEntity) {
        ProductEntity productEntityFromDB = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductEntity", "id", id));

        if(productEntityFromDB == null){
            throw  new APIException("Product not found with productId: " + id);
        }

        productEntity.setId(id);
        productEntity.setCategoryEntity(productEntityFromDB.getCategoryEntity());

        double specialPrice = productEntity.getPrice() - ((productEntity.getDiscount() * 0.01) * productEntity.getPrice());
        productEntity.setSpecialPrice(specialPrice);

        ProductEntity savedProduct = productRepository.save(productEntity);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<ProductEntity> pageProductEntity = productRepository.findByNameLike(keyword, pageDetails);

        List<ProductEntity> productEntities = pageProductEntity.getContent();

        if(productEntities.isEmpty()){
            throw new APIException("Product not found with keyword: " + keyword);
        }

        List<ProductDTO> productDTOs = productEntities.stream().map(p -> modelMapper.map(p, ProductDTO.class))
                .collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productDTOs);
        productResponse.setPageNumber(pageProductEntity.getNumber());
        productResponse.setPageSize(pageProductEntity.getSize());
        productResponse.setTotalElements(pageProductEntity.getTotalElements());
        productResponse.setTotalPages(pageProductEntity.getTotalPages());
        productResponse.setLastPage(pageProductEntity.isLast());

        return productResponse;


    }

    @Override
    public String deleteProduct(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductEntity", "id", id));

        productRepository.delete(productEntity);

        return "Product with productId: " + id + " deleted successfully !!!";
    }
}
