package com.tinder.ud.auth.dto;

/**
 * DTO que representa la respuesta de autenticación.
 * Contiene mensajes, token generado y datos del usuario autenticado.
 *
 * @author Juan Sebastian Bravo Rojas
 * @version 3.0
 * @since 2025-12-09
 */
public class AuthResponse {
    private String message;
    private String token; // Optional, placeholder for now
    private Long userId;
    private String email;

     /**
     * Constructor vacío requerido por frameworks.
     */
    public AuthResponse() {
    }

     /**
     * Construye una respuesta de autenticación completa.
     *
     * @param message mensaje descriptivo
     * @param token token JWT generado
     * @param userId identificador del usuario
     * @param email correo del usuario
     */
    public AuthResponse(String message, String token, Long userId, String email) {
        this.message = message;
        this.token = token;
        this.userId = userId;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
