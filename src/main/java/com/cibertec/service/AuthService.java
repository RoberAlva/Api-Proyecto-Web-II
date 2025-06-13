package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.dto.LoginRequestDTO;

public interface AuthService {
    ResponseEntity<Map<String, Object>> login(LoginRequestDTO loginRequest);
    ResponseEntity<Map<String, Object>> validateToken(String token);
}