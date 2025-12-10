package com.tinder.ud.auth.service;

import com.tinder.ud.auth.dto.AuthResponse;
import com.tinder.ud.auth.dto.LoginRequest;
import com.tinder.ud.auth.dto.RegisterRequest;

/**
 * Servicio encargado de la lógica de negocio relacionada
 * con autenticación y registro de usuarios.
 * 
 * @author Juan Sebastian BRavo Rojas
 * @version 1.0
 * @since 2025-12-09
 */
public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
