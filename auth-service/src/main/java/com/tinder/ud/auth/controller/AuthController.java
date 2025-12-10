package com.tinder.ud.auth.controller;

import com.tinder.ud.auth.dto.AuthResponse;
import com.tinder.ud.auth.dto.LoginRequest;
import com.tinder.ud.auth.dto.RegisterRequest;
import com.tinder.ud.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de autenticación de usuarios.
 * Proporciona endpoints para registro y login.
 * 
 * @author Juan Sebastián Bravo Rojas
 * @version 1.0
 * @since 2025-12-09
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param request Datos del usuario a registrar (email, password)
     * @return ResponseEntity con AuthResponse conteniendo token y datos del usuario
     * @throws RuntimeException si el email ya existe o la validación falla
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authService.register(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(e.getMessage(), null, null, null));
        }
    }

    /**
     * Autentica un usuario existente.
     * 
     * @param request Credenciales del usuario (email, password)
     * @return ResponseEntity con AuthResponse conteniendo token y datos del usuario
     * @throws RuntimeException si las credenciales son inválidas
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(new AuthResponse(e.getMessage(), null, null, null));
        }
    }
}
