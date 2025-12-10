package com.tinder.ud.auth.service.impl;

import com.tinder.ud.auth.dto.AuthResponse;
import com.tinder.ud.auth.dto.LoginRequest;
import com.tinder.ud.auth.dto.RegisterRequest;
import com.tinder.ud.auth.entity.Credenciales;
import com.tinder.ud.auth.repository.CredencialesRepository;
import com.tinder.ud.auth.service.AuthService;
import com.tinder.ud.auth.util.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Implementación del servicio de autenticación.
 * Gestiona el registro y login de usuarios con validación de credenciales.
 * 
 * @author Juan Sebastián Bravo Rojas
 * @version 1.0
 * @since 2025-12-09
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CredencialesRepository credencialesRepository;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private EmailService emailService;

    /**
     * Registra un nuevo usuario en el sistema.
     * Valida la contraseña, crea las credenciales y envía email de bienvenida.
     * 
     * @param request Datos de registro (email, password)
     * @return AuthResponse con token y datos del usuario
     * @throws RuntimeException si el email ya existe o la contraseña no cumple
     *                          requisitos
     */
    @Override
    public AuthResponse register(RegisterRequest request) {
        if (!passwordValidator.isValid(request.getPassword())) {
            throw new RuntimeException(
                    "Password does not meet requirements (1 upper, 1 lower, 1 number, 1 special, min 8)");
        }
        if (credencialesRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        // 1. Create Credenciales
        Credenciales creds = new Credenciales();
        creds.setEmail(request.getEmail());
        // Simple hash (In real world use BCrypt)
        creds.setPassword(String.valueOf(request.getPassword().hashCode()));

        Credenciales savedCreds = credencialesRepository.save(creds);

        // 2. Call User Service to create profile
        // Placeholder for user creation logic - assuming we send the Profile DTO +
        // creds ID
        // Note: Error handling omitted for brevity
        try {
            // Here we would map RegisterRequest to a UserDTO for usuario-service
            // restTemplate.postForEntity(USUARIO_SERVICE_URL, userDto, Void.class);
            // Updating local usuarioId if returned
        } catch (Exception e) {
            // rollback?
            System.err.println("Failed to create user profile: " + e.getMessage());
        }

        // 3. Send Email
        try {
            emailService.sendWelcomeEmail(request.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

        // 4. Log Notification
        try {
            // restTemplate.postForEntity(NOTIFICATION_SERVICE_URL, logDto, Void.class);
        } catch (Exception e) {
        }

        return new AuthResponse("User registered successfully", "dummy-token", savedCreds.getId(),
                savedCreds.getEmail());
    }

    /**
     * Autentica un usuario existente.
     * Verifica las credenciales y retorna un token de sesión.
     * 
     * @param request Credenciales de login (email, password)
     * @return AuthResponse con token y datos del usuario
     * @throws RuntimeException si el usuario no existe o las credenciales son
     *                          inválidas
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        Optional<Credenciales> credsOpt = credencialesRepository.findByEmail(request.getEmail());
        if (credsOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Credenciales creds = credsOpt.get();
        // Check password (hash comparison in real app)
        if (!creds.getPassword().equals(String.valueOf(request.getPassword().hashCode()))) {
            throw new RuntimeException("Invalid credentials");
        }
        // TEMPORARY FIX: Return creds.getId() because usuarioId is not being linked
        // yet.
        // This assumes User ID and Creds ID are synced (1:1) for now.
        return new AuthResponse("Login successful", "dummy-token", creds.getId(), creds.getEmail());
    }
}
