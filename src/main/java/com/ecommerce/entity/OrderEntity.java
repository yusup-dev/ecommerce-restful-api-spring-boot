package com.ecommerce.entity;

import com.ecommerce.constants.OrderStatus;
import com.ecommerce.constants.Status;
import com.ecommerce.entity.base.DatabaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity extends DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "please input valid email")
    @Column(nullable = false, unique = true, length = 125)
    private String email;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.PROCESSING;
}
