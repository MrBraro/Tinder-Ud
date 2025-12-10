package com.tinder.ud.usuario.dto;

/**
 * Objeto de Transferencia de Datos (DTO) que representa la información
 * de un usuario. Se usa para comunicar datos entre capas sin exponer
 * directamente la entidad JPA.
 *
 * Incluye datos de perfil, información personal y foto.
 *
 * @author Juan Sebastian Bravo Rojas
 * @version 3.0
 * @since 2025-12-10
 */
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellidos;
    private String nickname;
    private String email;
    private Integer edad;
    private String genero;
    private String ciudad;
    private String descripcion;
    private String fotoUrl;

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
