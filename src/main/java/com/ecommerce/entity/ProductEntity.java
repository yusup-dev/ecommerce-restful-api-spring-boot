package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 125)
    private String name;

    @Column(length = 352)
    private String description;

    @Column(length = 52)
    private String brand;

    @Column(length = 1024)
    private String imageUrl;

    private Integer quantity;

    private double price;

    private double discount;

    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryEntity;
}