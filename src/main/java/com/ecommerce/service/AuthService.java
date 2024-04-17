package com.ecommerce.service;

import com.ecommerce.dto.AuthRequest;
import com.ecommerce.dto.RegisterRequestDTO;

public interface AuthService {
    String login(AuthRequest authRequest);

    String signup(RegisterRequestDTO registerRequestDTO);
}
