package com.ecommerce.service;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.ProductEntity;

public interface ProductService {
    ProductDTO createProduct(Long categoryId, ProductEntity productEntity);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchByCategory(Long categoryId,Integer pageNumber,Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(Long id, ProductEntity productEntity);

    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    String deleteProduct(Long id);
}
