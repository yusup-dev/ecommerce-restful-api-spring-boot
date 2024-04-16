package com.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private String imageUrl;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
}
