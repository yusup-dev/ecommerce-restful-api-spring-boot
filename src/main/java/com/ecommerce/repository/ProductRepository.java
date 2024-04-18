package com.ecommerce.repository;

import com.ecommerce.entity.CategoryEntity;
import com.ecommerce.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByCategory(CategoryEntity categoryEntity, Pageable pageable);

    Page<ProductEntity> findByNameLike(String keyword, Pageable pageDetails);

}
