package com.tinder.ud.auth.service;

import com.tinder.ud.auth.dto.AuthResponse;
import com.tinder.ud.auth.dto.LoginRequest;
import com.tinder.ud.auth.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
