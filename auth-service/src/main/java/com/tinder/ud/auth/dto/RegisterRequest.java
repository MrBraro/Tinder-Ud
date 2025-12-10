package com.tinder.ud.auth.dto;

/**
 * DTO utilizado para registrar nuevos usuarios en el sistema.
 * Contiene datos personales y credenciales iniciales.
 * 
 * @author Juan Sebastian Bravo Rojas
 * @version 1.0
 * @since 2025-12-09
 */
public class RegisterRequest {
    private String email;
    private String password;
    private String nickname;
    private String nombre;
    private String apellidos;
    private Integer edad;
    private String genero;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}
