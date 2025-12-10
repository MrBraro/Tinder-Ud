package com.tinder.ud.auth.controller;

import com.tinder.ud.auth.dto.AuthResponse;
import com.tinder.ud.auth.dto.LoginRequest;
import com.tinder.ud.auth.dto.RegisterRequest;
import com.tinder.ud.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST encargado de gestionar los procesos de autenticación.
 * Incluye los endpoints para registro y login de usuarios.
 *
 * <p>Expone operaciones que delegan la lógica al {@link AuthService}.</p>
 *
 * @author Juan Sebastian Bravo Rojas
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
     * Endpoint para registrar un nuevo usuario.
     *
     * @param request datos del usuario a registrar
     * @return respuesta con token y datos del usuario
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
     * Endpoint para autenticar a un usuario existente.
     *
     * @param request credenciales de login
     * @return respuesta con token y datos del usuario
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

