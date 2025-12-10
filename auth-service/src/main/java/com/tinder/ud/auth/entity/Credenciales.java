package com.tinder.ud.auth.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa las credenciales de autenticación de un usuario.
 * Almacena email y contraseña hasheada.
 * 
 * @author Juan Sebastián Bravo Rojas
 * @version 1.0
 * @since 2025-12-09
 */
@Entity
@Table(name = "credenciales")
public class Credenciales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Optional: Link to Usuario entity in another service
    private Long usuarioId;

    public Credenciales() {
    }

    public Credenciales(Long id, String email, String password, Long usuarioId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.usuarioId = usuarioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
