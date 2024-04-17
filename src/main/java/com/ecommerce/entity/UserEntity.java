package com.ecommerce.entity;

import com.ecommerce.entity.base.UserBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends UserBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String username;

    @Email(message = "please input valid email")
    @Column(nullable = false, unique = true, length = 125)
    private String email;

    @Column(length = 25)
    private String phoneNumber;


    @Column(nullable = false, length = 1024)
    private String password;

    @Column(nullable = false, length = 45)
    private String firstName;

    @Column(nullable = false, length = 45)
    private String lastName;


    @Column(length = 45)
    private String imageUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<RoleEntity> roles;


    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<AddressEntity> addressEntities = new ArrayList<>();

}
