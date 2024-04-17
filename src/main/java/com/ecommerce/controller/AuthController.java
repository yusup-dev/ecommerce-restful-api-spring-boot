package com.ecommerce.controller;

import com.ecommerce.dto.AuthRequest;
import com.ecommerce.dto.AuthResponse;
import com.ecommerce.dto.RegisterRequestDTO;
import com.ecommerce.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        String token = authService.login(authRequest);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(token);

        return ResponseEntity.ok(authResponse);
    }

    // Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        String response = authService.signup(registerRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
