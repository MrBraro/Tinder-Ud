package com.tinder.ud.auth.dto;

/**
 * DTO que encapsula las credenciales enviadas por el usuario
 * al intentar iniciar sesión en el sistema.
 * 
 * @author Juan Sebastian Bravo Rojas
 * @version 3.0
 * @since 2025-12-09
 */
public class LoginRequest {

    private String email;
    private String password;

    /**
     * Obtiene el correo del usuario.
     *
     * @return email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo del usuario.
     *
     * @param email correo a asignar
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña ingresada.
     *
     * @return contraseña en texto plano
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password contraseña a asignar
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

