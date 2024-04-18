package com.ecommerce.entity;

import com.ecommerce.entity.base.DatabaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class AddressEntity extends DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 125)
    private String street;

    @Column(length = 45)
    private String city;

    @Column(length = 45)
    private String province;

    @Column(length = 45)
    private String country;

    @Column(length = 45)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
