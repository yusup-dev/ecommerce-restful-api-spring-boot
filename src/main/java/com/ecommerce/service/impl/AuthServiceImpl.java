package com.ecommerce.service.impl;

import com.ecommerce.config.JwtTokenProvider;
import com.ecommerce.dto.AuthRequest;
import com.ecommerce.dto.RegisterRequestDTO;
import com.ecommerce.entity.RoleEntity;
import com.ecommerce.entity.UserEntity;
import com.ecommerce.exception.APIException;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    private UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public String login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);

    }

    @Override
    public String signup(RegisterRequestDTO registerRequestDTO) {
        // add check for email exists in database
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())){
            throw new APIException(HttpStatus.BAD_REQUEST,"Email is already exists!" );
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerRequestDTO.getUsername());
        userEntity.setEmail(registerRequestDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        userEntity.setPhoneNumber(registerRequestDTO.getPhoneNumber());
        userEntity.setFirstName(registerRequestDTO.getFirstName());
        userEntity.setLastName(registerRequestDTO.getLastName());
        userEntity.setImageUrl(registerRequestDTO.getImageUrl());

        Optional<RoleEntity> roleOptional = roleRepository.findByName("ROLE_USER");
        RoleEntity userRole = roleOptional.orElseThrow(() -> new APIException(HttpStatus.INTERNAL_SERVER_ERROR, "Default role not found"));

        Set<RoleEntity> roleEntities = new HashSet<>();
        roleEntities.add(userRole);
        userEntity.setRoles(roleEntities);

        userRepository.save(userEntity);

        return "User registered successfully";
    }
}
