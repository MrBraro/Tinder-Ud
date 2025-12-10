package com.tinder.ud.usuario.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa un usuario en el sistema.
 * Almacena información del perfil incluyendo datos personales y preferencias.
 * 
 * @author Juan Sebastián Bravo Rojas
 * @version 1.0
 * @since 2025-12-09
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    private Integer edad;

    private String genero;

    private String ciudad;

    @Column(length = 500)
    private String descripcion;

    @Column(columnDefinition = "LONGTEXT")
    private String fotoUrl;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String apellidos, String nickname, String email, Integer edad, String genero,
            String ciudad, String descripcion, String fotoUrl) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nickname = nickname;
        this.email = email;
        this.edad = edad;
        this.genero = genero;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.fotoUrl = fotoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
