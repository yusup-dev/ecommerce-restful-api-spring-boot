package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String imageUrl;
}
