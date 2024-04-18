package com.ecommerce.entity;

import com.ecommerce.entity.base.DatabaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity extends DatabaseEntity {
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RatingEntity> ratings;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews;
}
