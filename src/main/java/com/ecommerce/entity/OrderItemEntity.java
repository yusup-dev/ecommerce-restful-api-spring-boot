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
@Table(name = "order_items")
public class OrderItemEntity extends DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private double discount;

    @Column(nullable = false)
    private double orderedProductPrice;

}
