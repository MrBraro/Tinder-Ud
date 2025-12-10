package com.tinder.ud.media.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa una foto almacenada en el sistema.
 * 
 * Atributos:
 * - id: PK autonumérica.
 * - idUsuario: usuario al que pertenece la foto.
 * - url: enlace o ruta de la imagen.
 * - esPrincipal: marca si es la foto destacada del usuario.
 * 
 * Constructor vacío y completo disponibles.
 * Getters y setters incluidos.
 * 
 * @author Paula Martinez
 * @version 3.0
 * @since 2025-12-10
 */
@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private String url;

    @Column(name = "es_principal")
    private Boolean esPrincipal;

    public Media() {}

    public Media(Long id, Long idUsuario, String url, Boolean esPrincipal) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.url = url;
        this.esPrincipal = esPrincipal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(Boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
}
