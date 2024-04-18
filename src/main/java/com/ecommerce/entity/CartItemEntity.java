package com.ecommerce.entity;


import com.ecommerce.entity.base.DatabaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItemEntity extends DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private double discount;

    @Column(nullable = false)
    private double productPrice;
}
