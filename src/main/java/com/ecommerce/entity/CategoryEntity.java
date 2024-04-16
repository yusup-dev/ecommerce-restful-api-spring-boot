package com.ecommerce.entity;

import com.ecommerce.entity.base.DatabaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})}
)
public class CategoryEntity extends DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 125)
    private String name;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    private List<ProductEntity> productEntities;
}
